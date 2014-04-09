package com.xiao.Feixin.FreeAPI.Controller;

import com.xiao.Feixin.FreeAPI.Model.CodeMessage;
import com.xiao.Feixin.FreeAPI.Operater.ReturnCode;

public class ReplyCode
{
	static private final CodeMessage[] codeMsgs = {
		new CodeMessage(1000001, "Parameter error"),
		new CodeMessage(1000000, "Unsupport commend")
	};
	
	private final int code;
	private final String message;
	
	public ReplyCode(final int code)
	{
		this.code = code;
		String message = ReturnCode.getMsg(code);
		if(message == null)
		{
			for(CodeMessage cMsg : codeMsgs)
			{
				if(code == cMsg.getCode())
					message = cMsg.getMessage();
			}
		}
		if(message == null)
			this.message = "Unknow code";
		else
			this.message = message;
	}

	public int getCode()
	{
		return code;
	}

	public String getMsg()
	{
		return message;
	}
	
	public boolean isSuccess()
	{
		return ReturnCode.isSuccess(code);
	}
	
	public boolean isLogin()
	{
		return ReturnCode.isLogin(code);
	}
}
