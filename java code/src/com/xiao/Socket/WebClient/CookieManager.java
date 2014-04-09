package com.xiao.Socket.WebClient;

import java.util.HashMap;
import java.util.Map;

public class CookieManager
{
	private Map<String, CookieInfo> cookieMap;
	
	public CookieManager()
	{
		cookieMap = new HashMap<String, CookieInfo>();
	}
	
	public void add(CookieInfo cookie)
	{
		cookieMap.put(cookie.getKey(), cookie);
	}
	
	public void add(CookieInfo[] cookies)
	{
		for(CookieInfo cookie : cookies)
		{
			cookieMap.put(cookie.getKey(), cookie);
		}
	}
	
	public void add(CookieList cookieList)
	{
		CookieInfo[] cookies = cookieList.getCookies();
		this.add(cookies);
	}
	
	public CookieInfo get(String key)
	{
		return this.cookieMap.get(key);
	}
	
	public CookieList getCookieList()
	{
		CookieList cookieList = new CookieList();
		for(String str : this.cookieMap.keySet())
		{
			cookieList.addCookie(this.cookieMap.get(str));
		}
		return cookieList;
	}
	
	public void rempveAll()
	{
		this.cookieMap.clear();
	}
}
