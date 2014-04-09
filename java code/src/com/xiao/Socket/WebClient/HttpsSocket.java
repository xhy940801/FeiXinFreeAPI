package com.xiao.Socket.WebClient;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class HttpsSocket extends HttpSocket
{
	public HttpsSocket()
	{
		this.port = 443;
	}
	
	protected void connect() throws IOException
	{
		SocketFactory sf = SSLSocketFactory.getDefault();  
		this.webSocket = sf.createSocket(this.addr, this.port);
		this.webSocket.setSoTimeout(this.timeOut);
		this.bufWriter = new BufferedWriter(new OutputStreamWriter(this.webSocket.getOutputStream()));
		this.bufReader = new AsciiReader(new BufferedInputStream(this.webSocket.getInputStream()));
	}
}
