package com.xiao.Feixin.FreeAPI.Model;

public class Request
{
	private final RequestType requestType;
	private final Object otherInfo;
	
	public enum RequestType
	{
		GETVERIFICATIONIMAGE, SENDFREEMSG, LOGIN, DISCONNECT
	}
	
	public Request(RequestType requestType, Object otherInfo)
	{
		this.requestType = requestType;
		this.otherInfo = otherInfo;
	}

	public RequestType getRequestType()
	{
		return requestType;
	}

	public Object getOtherInfo()
	{
		return otherInfo;
	}
	
	@Override
	public String toString()
	{
		String str = requestType.toString() + "\r\n";
		if(otherInfo != null)
		{
			str += "object(OtherInfo) information:\r\n";
			str += otherInfo.getClass().toString() + "\r\n";
			str += "Object(OtherInfo) toString:\r\n";
			str += otherInfo.toString();
		}
		else
		{
			str += "OtherInfo is null";
		}
		return str;
	}
}
