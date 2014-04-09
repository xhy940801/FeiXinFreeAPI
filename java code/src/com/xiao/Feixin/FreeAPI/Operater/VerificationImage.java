package com.xiao.Feixin.FreeAPI.Operater;

import com.xiao.Socket.WebClient.CookieInfo;

public class VerificationImage
{
	private final byte[] img;
	private final ReturnCode returnCode;
	private final CookieInfo cookie;
	
	VerificationImage(byte[] img, ReturnCode returnCode, CookieInfo cookie)
	{
		this.img = img;
		this.returnCode = returnCode;
		this.cookie = cookie;
	}

	public byte[] getImg()
	{
		return img;
	}

	public ReturnCode getReturnCode()
	{
		return returnCode;
	}
	
	public boolean isSuccess()
	{
		return returnCode.isSuccess();
	}

	public CookieInfo getCookie()
	{
		return cookie;
	}
	
	@Override
	public String toString()
	{
		String str = returnCode.toString();
		if(cookie != null)
			str += "\r\nCookie information:\r\n" + cookie.toString();
		return str;
	}
}
