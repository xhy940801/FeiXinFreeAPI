package com.xiao.Feixin.FreeAPI.Connection;

import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.xiao.Feixin.FreeAPI.Controller.Commend;
import com.xiao.Feixin.FreeAPI.Controller.Listener;
import com.xiao.Feixin.FreeAPI.Controller.Reply;
import com.xiao.Feixin.FreeAPI.Controller.ThreadFeixinController;
import com.xiao.Feixin.FreeAPI.Log.Log;
import com.xiao.Feixin.FreeAPI.Model.IllegalDataStructException;
import com.xiao.Feixin.FreeAPI.Model.Request;
import com.xiao.Feixin.FreeAPI.Model.RequestDecoder;
import com.xiao.Feixin.FreeAPI.Model.Response;
import com.xiao.Feixin.FreeAPI.Model.ResponseEncoder;
import com.xiao.Feixin.FreeAPI.Model.TokenErrorException;

public class FeixinServer implements Runnable, Listener
{
	private ThreadFeixinController fController;
	private ResponseEncoder resEncoder;
	private RequestDecoder reqDecoder;
	private Socket socket;
	private Thread tFController;
	private BlockingQueue<Reply> replyMsgCodeQueue = new LinkedBlockingQueue<Reply>();
	
	private boolean run = true;
	
	public FeixinServer(ThreadFeixinController fController, ResponseEncoder resEncoder, RequestDecoder reqDecoder, Socket socket, Thread tFController)
	{
		this.fController = fController;
		this.resEncoder = resEncoder;
		this.reqDecoder = reqDecoder;
		this.socket = socket;
		this.tFController = tFController;
	}

	@Override
	public void run()
	{
		fController.signIn(this);
		try
		{
			while(run)
			{
				Commend commend = this.sendCommend();
				if(!run)
					break;
				this.analyzeReply(commend);
			}
		}
		catch (SocketException e)
		{
			Log.getErrorLog().log(e);
		}
		catch (IllegalDataStructException e)
		{
			Log.getErrorLog().log(e);
			Response res = new Response(Response.ResponseCode.REQUESTERROR, "request error!");
			resEncoder.sendResponse(res);
		}
		fController.signOut(this);
	}
	
	private Reply getReply(Commend commend)
	{
		while(run)
		{
			Reply reply;
			try
			{
				reply = replyMsgCodeQueue.poll(20, TimeUnit.SECONDS);
				return reply;
			}
			catch (InterruptedException e)
			{
				Log.getErrorLog().log(e);
			}
		}
		return null;
	}
	
	private void analyzeReply(Commend commend) throws SocketException
	{
		Reply reply = this.getReply(commend);
		if(reply == null)
		{
			Response res = new Response(Response.ResponseCode.TIMEOUT, null);
			resEncoder.sendResponse(res);
		}
		else if(reply.isSuccess())
		{
			Response res = new Response(Response.ResponseCode.SUCCESS, reply.getOtherInfo());
			resEncoder.sendResponse(res);
		}
		else if(!reply.isLogin())
		{
			Response res = new Response(Response.ResponseCode.NOTLOGIN, reply);
			resEncoder.sendResponse(res);
		}
		else
		{
			Response res = new Response(Response.ResponseCode.FAIL, reply);
			resEncoder.sendResponse(res);
		}
	}
	
	public Commend sendCommend() throws IllegalDataStructException
	{
		Request req;
		Commend commend = null;
		try
		{
			socket.setSoTimeout(5000);
			req = reqDecoder.getRequest();
			switch(req.getRequestType())
			{
			case GETVERIFICATIONIMAGE:
				commend = new Commend(Commend.CommendCode.GETVERIFICATIONIMAGE, this, null);
				fController.addCommend(commend);
				tFController.interrupt();
				break;
			case SENDFREEMSG:
				commend = new Commend(Commend.CommendCode.SENDFREEMSG, this, req.getOtherInfo());
				fController.addCommend(commend);
				tFController.interrupt();
				break;
			case LOGIN:
				commend = new Commend(Commend.CommendCode.LOGIN, this, req.getOtherInfo());
				fController.addCommend(commend);
				tFController.interrupt();
				break;
			case DISCONNECT:
				run = false;
				break;
			}
		}
		catch (SocketTimeoutException e)
		{
			run = false;
		}
		catch (SocketException e)
		{
			Log.getErrorLog().log(e);
		}
		catch (TokenErrorException e)
		{
			Log.getErrorLog().log(e, "Socket information:\r\n" + this.socket.toString());
			run = false;
		}
		return commend;
	}
	
	public void stop()
	{
		run = false;
	}
	
	public boolean isRun()
	{
		return run;
	}

	@Override
	public void sendReply(Reply reply)
	{
		if(reply.getCommend().getSender().equals(this))
		{
			try
			{
				replyMsgCodeQueue.put(reply);
			}
			catch (InterruptedException e)
			{
				Log.getErrorLog().log(e);
			}
		}
	}
}
