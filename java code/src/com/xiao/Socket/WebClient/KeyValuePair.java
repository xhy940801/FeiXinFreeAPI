package com.xiao.Socket.WebClient;
/**
 * This class is a simple struct
 * has two member key and value (all is String object)
 * @author xiao
 */
public class KeyValuePair
{
	public String key,value;
	
	public KeyValuePair()
	{
		
	}
	
	public KeyValuePair(String key)
	{
		this.key = key;
		this.value = "";
	}
	
	public KeyValuePair(String key,String value)
	{
		this.key = key;
		this.value = value;
	}
	
	public void set(String key,String value)
	{
		this.key = key;
		this.value = value;
	}
}
