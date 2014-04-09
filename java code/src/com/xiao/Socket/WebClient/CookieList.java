package com.xiao.Socket.WebClient;

import java.util.ArrayList;
/*
 * A cookie list base on ArrayList
 * so it's not thread safe
 */
public class CookieList
{
	private ArrayList<CookieInfo> arrayList;
	public CookieList()
	{
		this.arrayList = new ArrayList<CookieInfo>(3);
	}
	/**
	 * Add a cookie
	 * @param cookie a CookieInfo object you want to add.
	 */
	public void addCookie(CookieInfo cookie)
	{
		this.arrayList.add(cookie);
	}
	/**
	 * get all the valid cookie.
	 * @param url what url you want to browser(if it's null ,we will not check)
	 * @param checkDate if or not check the expires
	 * @param secure (are you browser in ssl(Https)? If you don't want to check secure, you need set secure to true)
	 * 
	 * @see com.xiao.Socket.WebClient.CookieInfo
	 */
	public CookieInfo[] getValidCookieArray(String url,boolean checkDate,boolean secure)
	{
		ArrayList<CookieInfo> validCookies = new ArrayList<CookieInfo>(this.arrayList.size());
		for(int i=0;i<this.arrayList.size();++i)
		{
			if(this.arrayList.get(i).isValid(url, checkDate, secure))
				validCookies.add(this.arrayList.get(i));
		}
		return (CookieInfo[])validCookies.toArray();
	}
	
	public CookieInfo[] getCookies()
	{
		CookieInfo[] cookies = new CookieInfo[arrayList.size()];
		this.arrayList.toArray(cookies);
		return cookies;
	}
}
