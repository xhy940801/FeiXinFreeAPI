package com.xiao.Feixin.FreeAPI.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

import com.xiao.Feixin.FreeAPI.Config.Config;
import com.xiao.Feixin.FreeAPI.Config.ServerConfig;
import com.xiao.Feixin.FreeAPI.Log.Log;
import com.xiao.Socket.WebClient.CookieInfo;

public class StreamReqDecoder implements RequestDecoder
{
	BufferedReader bReader;

	public StreamReqDecoder(InputStream inputStream)
	{
		bReader = new BufferedReader(new InputStreamReader(inputStream, ServerConfig.getCharset()));
	}

	@Override
	public Request getRequest() throws SocketTimeoutException,
			IllegalDataStructException, TokenErrorException
	{
		String req = null;
		String token = null;
		try
		{
			token = bReader.readLine();
			req = bReader.readLine();
		}
		catch (IOException e)
		{
			Log.getErrorLog().log(e);
		}
		if(token != null)
			token = token.replaceAll("^\\s*|\\s*$", "");
		if(req == null)
			throw new SocketTimeoutException();
		if(!Config.getAccessToken().equals(token))
			throw new TokenErrorException();
		req = req.replaceAll("^\\s*|\\s*$", "").toLowerCase();
		Request request;
		switch(req)
		{
		case "get_v_img":
			request = new Request(Request.RequestType.GETVERIFICATIONIMAGE, null);
			break;
		case "login":
			request = this.getLoginRequest();
			break;
		case "send_msg":
			request = this.sendMsgRequest();
			break;
		case "disconnect":
			request = new Request(Request.RequestType.DISCONNECT, null);
			break;
		default:
			throw new IllegalDataStructException();
		}
		Log.getRequestLog().log(request);
		return request;
	}
	
	private Request sendMsgRequest() throws IllegalDataStructException
	{
		try
		{
			String receiver = bReader.readLine().replaceAll("^\\s*|\\s*$", "");
			String msgL = bReader.readLine().replaceAll("^\\s*|\\s*$", "");
			int msgLength = Integer.parseInt(msgL);
			StringBuffer msgBuf = new StringBuffer();
			char[] charBuf = new char[msgLength];
			int curL = 0;
			while(true)
			{
				int c = bReader.read(charBuf, curL, msgLength - curL);
				
				if(c < 0)
				{
					msgBuf.append(charBuf);
					break;
				}
				curL += c;
				if(curL >= msgLength)
				{
					if(curL > 340)
						msgBuf.append(charBuf, 0, 340);
					else
						msgBuf.append(charBuf, 0, curL);
					break;
				}
			}
			
			long rec = Long.parseLong(receiver);
			FreeMessage fMsg = new FreeMessage(rec, msgBuf.toString());
			Request req = new Request(Request.RequestType.SENDFREEMSG, fMsg);
			return req;
		}
		catch (IOException | NumberFormatException e)
		{
			Log.getErrorLog().log(e);
			throw new IllegalDataStructException();
		}
	}
	
	private Request getLoginRequest() throws IllegalDataStructException, SocketTimeoutException
	{
		try
		{
			String username = bReader.readLine().replaceAll("^\\s*|\\s*$", "");
			String password = bReader.readLine().replaceAll("^\\s*|\\s*$", "");
			String verificationCode = bReader.readLine().replaceAll("^\\s*|\\s*$", "");
			String cookie_value = bReader.readLine().replaceAll("^\\s*|\\s*$", "");
			if(username == null || password == null || verificationCode == null || cookie_value == null)
				throw new IllegalDataStructException();
			CookieInfo cookie = new CookieInfo();
			cookie.setKey("ccpsession");
			cookie.setValue(cookie_value);
			LoginInfo lInfo = new LoginInfo(username, password, verificationCode, cookie);
			return new Request(Request.RequestType.LOGIN, lInfo);
		}
		catch (IOException e)
		{
			Log.getErrorLog().log(e);
			throw new IllegalDataStructException();
		}
	}

}
