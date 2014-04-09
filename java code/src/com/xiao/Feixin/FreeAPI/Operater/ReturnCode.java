package com.xiao.Feixin.FreeAPI.Operater;

import com.xiao.Feixin.FreeAPI.Model.CodeMessage;

public class ReturnCode
{
	static final int[] successCode = {200, 304};
	
	static private final CodeMessage[] codeMsgs = {
		new CodeMessage(200, "Success"),
		new CodeMessage(304, "Success"),
		new CodeMessage(312, "Verification code is not correct"),
		new CodeMessage(321, "Username or password is not correct"),
		new CodeMessage(310, "Have not login"),
		new CodeMessage(10001, "Network error"),
		new CodeMessage(10002, "Decode message error"),
		new CodeMessage(10000, "Unknow error")
		};
	
	private final int code;

	public ReturnCode(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
	
	public boolean isSuccess()
	{
		for(int sc : successCode)
		{
			if(code == sc)
				return true;
		}
		return false;
	}
	
	public boolean isLogin()
	{
		return code != 310;
	}
	
	static public boolean isLogin(int code)
	{
		return code != 310;
	}
	
	public String getMsg()
	{
		for(CodeMessage cMsg : codeMsgs)
		{
			if(code == cMsg.getCode())
				return cMsg.getMessage();
		}
		return "Unknow code";
	}
	
	public static String getMsg(int code)
	{
		for(CodeMessage cMsg : codeMsgs)
		{
			if(code == cMsg.getCode())
				return cMsg.getMessage();
		}
		return null;
	}
	
	static public boolean isSuccess(int code)
	{
		for(int sc : successCode)
		{
			if(code == sc)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return this.getMsg();
	}

}


