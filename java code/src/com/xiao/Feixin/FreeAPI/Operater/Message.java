package com.xiao.Feixin.FreeAPI.Operater;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Message
{
	private long senderUid;
	private long receiverUid;
	private String msg;
	private Date date;
	
	public Message(long senderUid, long receiverUid, String msg, Date date)
	{
		this.senderUid = senderUid;
		this.receiverUid = receiverUid;
		this.msg = msg;
		this.date = date;
	}
	
	public Message(JSONObject jsonObj)
	{
		try
		{
			Object suid = jsonObj.get("fromUid");
			senderUid = Long.parseLong(suid.toString());
		}
		catch (JSONException | NumberFormatException e)
		{
			senderUid = -1L;
		}
		
		try
		{
			Object ruid = jsonObj.get("toUid");
			receiverUid = Long.parseLong(ruid.toString());
		}
		catch (JSONException e)
		{
			receiverUid = -1L;
		}
		
		try
		{
			Object suid = jsonObj.get("msg");
			msg = suid.toString();
		}
		catch (JSONException e)
		{
			msg = "";
		}
		
		date = new Date();
	}
	
	public long getSenderUid()
	{
		return senderUid;
	}
	
	public long getReceiverUid()
	{
		return receiverUid;
	}

	public String getMsg()
	{
		return msg;
	}

	public Date getDate()
	{
		return date;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Message)
		{
			Message msg = (Message) o;
			return msg.date.equals(this.date) && msg.msg.equals(this.msg)
					&& msg.receiverUid == this.receiverUid && msg.senderUid == this.senderUid;
		}
		else
			return false;
	}
}
