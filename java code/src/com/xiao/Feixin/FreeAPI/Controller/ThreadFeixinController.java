package com.xiao.Feixin.FreeAPI.Controller;

import java.net.UnknownHostException;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.xiao.Feixin.FreeAPI.Config.ServerConfig;
import com.xiao.Feixin.FreeAPI.Log.Log;
import com.xiao.Feixin.FreeAPI.Model.FreeMessage;
import com.xiao.Feixin.FreeAPI.Model.LoginInfo;
import com.xiao.Feixin.FreeAPI.Operater.FeixinOperator;
import com.xiao.Feixin.FreeAPI.Operater.ReturnCode;
import com.xiao.Feixin.FreeAPI.Operater.VerificationImage;
import com.xiao.Socket.WebClient.WebCilentException;

public class ThreadFeixinController implements Runnable
{	
	private FeixinOperator fOperator;
	private boolean run;
	private Queue<Commend> commandMsgCodeQueue;
	private int cMsgCQueueLength;
	
	private boolean login;
	
	private Vector<Listener> listenerList;
	
	public ThreadFeixinController(FeixinOperator fOperator)
	{
		this.fOperator = fOperator;
		init();
	}
	
	public void init()
	{
		run = true;
		commandMsgCodeQueue = new ConcurrentLinkedQueue<Commend>();
		cMsgCQueueLength = 0;
		login = false;
		listenerList = new Vector<Listener>();
		try
		{
			fOperator.init();
		}
		catch (UnknownHostException e)
		{
			Log.getErrorLog().log(e);
		}
		catch (WebCilentException e)
		{
			Log.getErrorLog().log(e);
		}
	}

	@Override
	public void run()
	{
		
		while(run)
		{
			try
			{
				if(login)
				{
					ReturnCode rc = fOperator.getConnect();
					for(int i=0;i<ServerConfig.getReconnectTimes() && !rc.isSuccess();++i)
						rc = fOperator.getConnect();
					if(!rc.isSuccess())
					{
						fOperator.cleanCookie();
						login = false;
					}
				}
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e)
				{
					
				}
				
				Commend commend;
				while(true)
				{
					commend = commandMsgCodeQueue.poll();
					if(commend == null)
						break;
					switch (commend.getCommendCode())
					{
					case LOGIN:
						this.login(commend);
						break;
					case SENDFREEMSG:
						this.sendFreeMsg(commend);
						break;
					case GETMESSAGE:
						this.getMessage(commend);
						break;
					case GETVERIFICATIONIMAGE:
						this.getVerificationImage(commend);
						break;
					case LOGOUT:
						break;
					default:
						break;
					}
				}
			}
			catch (Exception e)
			{
				Log.getErrorLog().log(e);
				Log.getErrorLog().log("re init");
				this.init();
			}
		}
		
	}
	
	public void addCommend(Commend commend)
	{
		commandMsgCodeQueue.add(commend);
		++cMsgCQueueLength;
	}
	
	public void emptyCommendQueue()
	{
		commandMsgCodeQueue = new ConcurrentLinkedQueue<Commend>();
		cMsgCQueueLength = commandMsgCodeQueue.size();
	}
	
	public int getCommendQueueLength()
	{
		return cMsgCQueueLength;
	}

	public boolean isRun()
	{
		return run;
	}

	public void setRun(boolean run)
	{
		this.run = run;
	}

	private void getVerificationImage(Commend commend)
	{
		VerificationImage vImg = fOperator.getVerificationImage();
		Reply re;
		if(vImg.isSuccess())
		{
			re = new Reply(commend, vImg.getReturnCode());
			re.setOtherInfo(vImg);
		}
		else
		{
			re = new Reply(commend, vImg.getReturnCode());
			Log.getErrorLog().log(vImg);
		}
		pushReply(re);
	}
	
	private void login(Commend commend)
	{
		if(login)
		{
			pushReply(new Reply(commend, new ReplyCode(200)));
			return;
		}
		if(commend.getOtherInfo() instanceof LoginInfo)
		{
			LoginInfo loginInfo = (LoginInfo) commend.getOtherInfo();
			if(!loginInfo.isAvailable())
			{
				ReplyCode replyCode = new ReplyCode(1000001);
				pushReply(new Reply(commend, replyCode));
				return;
			}
			ReturnCode rc = fOperator.login(loginInfo.getUsername(), loginInfo.getPassword(), loginInfo.getVerificationCode(), loginInfo.getCookie());
			pushReply(new Reply(commend, rc));
			if(rc.isSuccess())
				login = true;
		}
		else
		{
			ReplyCode replyCode = new ReplyCode(1000001);
			pushReply(new Reply(commend, replyCode));
			return;
		}
	}
	
	public void logout(Commend commend)
	{
		fOperator.cleanCookie();
		
		login = false;
		pushReply(new Reply(commend, new ReplyCode(200)));
	}
	
	private void sendFreeMsg(Commend commend)
	{
		if(commend.getOtherInfo() instanceof FreeMessage)
		{
			FreeMessage fmsg = (FreeMessage) commend.getOtherInfo();
			ReturnCode rc = fOperator.sendFreeMsg(fmsg.getMessage(), String.valueOf(fmsg.getReceiver()));
			if(!rc.isLogin())
				login = false;
			pushReply(new Reply(commend, rc));
		}
		else
		{
			ReplyCode replyCode = new ReplyCode(1000001);
			pushReply(new Reply(commend, replyCode));
			return;
		}
	}
	
	private void getMessage(Commend commend)
	{
		Reply re = new Reply(commend, new ReplyCode(10000));
		pushReply(re);
	}
	
	public boolean isLogin()
	{
		return login;
	}
	
	private void pushReply(Reply reply)
	{
		for(Listener l : listenerList)
		{
			l.sendReply(reply);
		}
	}
	
	public void signIn(Listener l)
	{
		listenerList.add(l);
	}
	
	public void signOut(Listener l)
	{
		listenerList.remove(l);
	}
}
