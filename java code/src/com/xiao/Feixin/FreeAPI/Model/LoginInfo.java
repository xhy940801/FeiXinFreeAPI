package com.xiao.Feixin.FreeAPI.Model;

import com.xiao.Socket.WebClient.CookieInfo;

public class LoginInfo
{
	private final String username;
	private final String password;
	private final String verificationCode;
	private final CookieInfo cookie;
	
	public LoginInfo(String username, String password, String verificationCode, CookieInfo cookie)
	{
		this.username = username;
		this.password = password;
		this.verificationCode = verificationCode;
		this.cookie = cookie;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getVerificationCode()
	{
		return verificationCode;
	}

	public CookieInfo getCookie()
	{
		return cookie;
	}
	
	public boolean isAvailable()
	{
		if(username == null || password == null || verificationCode == null || cookie == null)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		String str = "Username: ";
		if(username != null)
			str += username;
		
		str += "\r\nPassword: ";
		if(password != null)
			str += password;
		
		str += "\r\nVerification code: ";
		if(verificationCode != null)
			str += verificationCode;
		
		str += "\r\nCookie: ";
		if(cookie != null)
			str += cookie.toString();
		return str;
	}
}
