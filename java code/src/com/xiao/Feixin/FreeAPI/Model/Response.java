package com.xiao.Feixin.FreeAPI.Model;

public class Response
{
	private final ResponseCode resCode;
	private final Object otherInfo;
	
	public enum ResponseCode
	{
		NULLCODE, SUCCESS, TIMEOUT, FAIL, REQUESTERROR, NOTLOGIN
	}
	
	public Response(ResponseCode resCode, Object otherInfo)
	{
		this.resCode = resCode;
		this.otherInfo = otherInfo;
	}

	public Object getOtherInfo()
	{
		return otherInfo;
	}

	public ResponseCode getResCode()
	{
		return resCode;
	}
	
	@Override
	public String toString()
	{
		String str = resCode.toString() + "\r\n";
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
