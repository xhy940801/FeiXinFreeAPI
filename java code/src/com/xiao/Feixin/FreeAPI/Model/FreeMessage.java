package com.xiao.Feixin.FreeAPI.Model;

public class FreeMessage
{
	private final long receiver;
	private final String message;
	

	public FreeMessage(final long receiver, final String message)
	{
		this.receiver = receiver;
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public long getReceiver()
	{
		return receiver;
	}

}
