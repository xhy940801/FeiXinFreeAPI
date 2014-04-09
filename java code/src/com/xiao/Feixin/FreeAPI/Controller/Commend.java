package com.xiao.Feixin.FreeAPI.Controller;

public class Commend
{
	private final CommendCode code;
	private final Object sender;
	private Object otherInfo;
	
	private int repeat = 1;
	
	public enum CommendCode
	{
		LOGIN, SENDFREEMSG, GETMESSAGE, GETVERIFICATIONIMAGE, LOGOUT
	}
	
	public Commend(CommendCode code, Object sender, Object otherInfo)
	{
		this.code = code;
		this.otherInfo = otherInfo;
		this.sender = sender;
	}
	
	public int getRepeatNumber()
	{
		return repeat;
	}
	
	public void addRepeatNumber()
	{
		++repeat;
	}

	public Object getOtherInfo()
	{
		return otherInfo;
	}
	
	public CommendCode getCommendCode()
	{
		return code;
	}

	public Object getSender()
	{
		return sender;
	}

}
