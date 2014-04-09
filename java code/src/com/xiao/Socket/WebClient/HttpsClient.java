package com.xiao.Socket.WebClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpsClient extends HttpClient
{

	public HttpsClient()
	{
		
	}

	public HttpsClient(String host) throws UnknownHostException,
			WebCilentException
	{
		super(host);
	}

	public HttpsClient(String host, String ipAddr) throws WebCilentException
	{
		super(host, ipAddr);
	}

	public HttpsClient(String host, InetAddress ipAddr)
			throws WebCilentException
	{
		super(host, ipAddr);
	}

	public HttpsClient(String host, int port) throws UnknownHostException,
			WebCilentException
	{
		super(host, port);
	}

	public HttpsClient(String host, String ipAddr, int port)
			throws WebCilentException
	{
		super(host, ipAddr, port);
	}

	public HttpsClient(String host, InetAddress ipAddr, int port)
			throws WebCilentException
	{
		super(host, ipAddr, port);
	}
	
	protected WebCilentSocket getSocket()
	{
		return new HttpsSocket();
	}

}
