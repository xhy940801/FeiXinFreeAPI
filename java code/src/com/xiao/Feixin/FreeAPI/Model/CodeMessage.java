package com.xiao.Feixin.FreeAPI.Model;

public class CodeMessage
{
	private final int code;
	private final String message;
	
	public CodeMessage(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public int getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}
}