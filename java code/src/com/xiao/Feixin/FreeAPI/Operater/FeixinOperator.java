package com.xiao.Feixin.FreeAPI.Operater;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.json.JSONException;

import com.xiao.Feixin.FreeAPI.Config.FeixinURLConfig;
import com.xiao.Feixin.FreeAPI.Log.Log;
import com.xiao.Feixin.FreeAPI.Model.MessageModel;
import com.xiao.Socket.WebClient.AnalysisError;
import com.xiao.Socket.WebClient.CookieInfo;
import com.xiao.Socket.WebClient.CookieManager;
import com.xiao.Socket.WebClient.HttpResponse;
import com.xiao.Socket.WebClient.HttpsClient;
import com.xiao.Socket.WebClient.KeyValuePair;
import com.xiao.Socket.WebClient.WebCilentException;
import com.xiao.Socket.WebClient.WebClient;

public class FeixinOperator
{	
	private WebClient webClient;
	private CookieManager cookieManager;
	private Random random;
	
	private Version version;
	private Queue<Message> msgQueue;
	
	private class Version
	{
		private int version;
		
		Version()
		{
			init();
		}
		
		public void init()
		{
			version = 0;
		}

		public int getVersion()
		{
			++version;
			if(version < 0)
				version = 1;
			return version;
		}
	}
	
	public FeixinOperator()
	{
		
	}
	
	public void init() throws UnknownHostException, WebCilentException
	{
		webClient = new HttpsClient(FeixinURLConfig.getFeixinHost());
		cookieManager = new CookieManager();
		random = new Random();
		version = new Version();
		msgQueue = new LinkedList<Message>();
	}
	
	public ReturnCode login(String username, String password, String verificationCode, CookieInfo cookie)
	{
		HttpResponse response;
		KeyValuePair[] kvs = new KeyValuePair[5];
		kvs[0] = new KeyValuePair("Username",username);
		kvs[1] = new KeyValuePair("Pwd", password);
		kvs[2] = new KeyValuePair("OnlineStatus", "400");
		kvs[3] = new KeyValuePair("AccountType", "1");
		kvs[4] = new KeyValuePair("Ccp", verificationCode);
		try
		{
			cookieManager.add(cookie);
			CookieInfo[] cs = cookieManager.getCookieList().getCookies();
			response = webClient.doPost(FeixinURLConfig.getLoginURL(), kvs, cs);
			cookieManager.add(response.getCookieList());
			
			ReceiveJsonDecoder jsonDecoder = new ReceiveJsonDecoder(response.turnByteToString());
			
			return new ReturnCode(jsonDecoder.getStatus());
		}
		catch (AnalysisError | WebCilentException e)
		{
			return new ReturnCode(10001);
		}
		catch (JSONException | NumberFormatException | UnsupportedEncodingException e)
		{
			return new ReturnCode(10002);
		}
	}
	
	public VerificationImage getVerificationImage()
	{
		try
		{
			HttpResponse response = webClient.doGet(FeixinURLConfig.getVerificationImgURL() + random.nextFloat());
			cookieManager.add(response.getCookieList());
			
			if(response.getStatus() == 200)
				return new VerificationImage(response.getBody(), new ReturnCode(200), cookieManager.get("ccpsession"));
			else
				return new VerificationImage(null, new ReturnCode(10001), null);
		}
		catch (AnalysisError | WebCilentException e)
		{
			Log.getErrorLog().log(e);
			return new VerificationImage(null, new ReturnCode(10001), null);
		}
	}
	
	public ReturnCode sendFreeMsg(String msg, String receiver)
	{
		CookieInfo c = cookieManager.get("webim_sessionid");
		if(c == null)
			return new ReturnCode(310);
		KeyValuePair[] kvs2 = new KeyValuePair[3];
		kvs2[0] = new KeyValuePair("Message", msg);
		kvs2[1] = new KeyValuePair("Receivers", receiver);
		kvs2[2] = new KeyValuePair("ssid", c.getValue());
		
		HttpResponse response;
		try
		{
			response = webClient.doPost(FeixinURLConfig.getSendfreeMsgURL() + "?Version=" + version.getVersion(), kvs2, cookieManager.getCookieList().getCookies());
			
			ReceiveJsonDecoder jsonDecoder = new ReceiveJsonDecoder(response.turnByteToString());
			return new ReturnCode(jsonDecoder.getStatus());
		}
		catch (AnalysisError | WebCilentException e)
		{
			return new ReturnCode(10001);
		}
		catch (JSONException | UnsupportedEncodingException e)
		{
			return new ReturnCode(10002);
		}
	}
	
	public ReturnCode getConnect()
	{
		CookieInfo c = cookieManager.get("webim_sessionid");
		if(c == null)
			return new ReturnCode(10000);
		KeyValuePair[] kvs2 = new KeyValuePair[2];
		kvs2[0] = new KeyValuePair("ssid", c.getValue());
		kvs2[1] = new KeyValuePair("reported", "");
		HttpResponse response;
		try
		{
			response = webClient.doPost(FeixinURLConfig.getConnectURL() + "?Version=" + version.getVersion(), kvs2, cookieManager.getCookieList().getCookies());
			
			ReceiveJsonDecoder jsonDecoder = new ReceiveJsonDecoder(response.turnByteToString());
			int status = jsonDecoder.getStatus();
			switch(status)
			{
			case 200:
				{
					List<Message> msgList = jsonDecoder.getMessages();
					if(msgList != null)
						msgQueue.addAll(msgList);
					return new ReturnCode(status);
				}
			default:
				return new ReturnCode(status);
			}
		}
		catch (AnalysisError | WebCilentException e)
		{
			return new ReturnCode(10001);
		}
		catch (JSONException | UnsupportedEncodingException e)
		{
			return new ReturnCode(10002);
		}
		
	}
	
	public Message[] getMessage(int number)
	{
		if(number < 0 || number >= msgQueue.size())
		{
			if(msgQueue.size() > 0)
				return msgQueue.toArray(new Message[0]);
			else
				return new Message[0];
		}
		else
		{
			Message[] msgs = new Message[number];
			for(int i=0;i<msgs.length;++i)
				msgs[i] = msgQueue.poll();
			return msgs;
		}
	}
	
	public void cleanCookie()
	{
		cookieManager.rempveAll();
		this.version.init();
	}
	
	public int getMessageNumber()
	{
		return msgQueue.size();
	}
	
	public void release()
	{
		MessageModel.saveMessage(msgQueue);
		msgQueue.clear();
	}

}
