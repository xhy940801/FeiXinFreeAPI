package com.xiao.Socket.WebClient;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HttpSocket implements WebCilentSocket
{
	protected int port = 80;
	protected int timeOut = 10000;
	protected InetAddress addr;
	protected Socket webSocket = null;
	protected BufferedWriter bufWriter;
	protected AsciiReader bufReader;
	
	public HttpSocket()
	{
		
	}

	@Override
	public String sendAndReadLine(String req) throws WebCilentException
	{
		try
		{
			String status;
			if(this.webSocket == null)
			{
				this.connect();
				this.bufWriter.write(req);
				this.bufWriter.flush();
				status = this.bufReader.readLine();
			}
			else
			{
				try
				{
					this.bufWriter.write(req);
					this.bufWriter.flush();
					status = this.bufReader.readLine();
				}
				catch (IOException e)
				{
					status = null;
				}
				if(status == null)
				{
					this.reconnect();
					this.bufWriter.write(req);
					this.bufWriter.flush();
					status = this.bufReader.readLine();
					if(status == null)
						throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: protected void sendRequestAndAnalysisStatus(String req, HttpResponse HR, int port)"
								,ConstValue.CLASSNAME.HTTPSOCKET|ConstValue.FUNCTIONNAME.SENDANDREADLINE|
								ConstValue.ERRORTYPE.CONNECTIONERROR|ConstValue.OTHERINFORMATION.NONE);
				}
			}
			return status;
		} catch(IOException e)
		{
			e.printStackTrace();
			throw new WebCilentException("Connection error!\r\nFunction: protected void sendRequestAndAnalysisStatus(String req, HttpResponse HR, int port)"
					,ConstValue.CLASSNAME.HTTPSOCKET|ConstValue.FUNCTIONNAME.SENDANDREADLINE|
					ConstValue.ERRORTYPE.CONNECTIONERROR|ConstValue.OTHERINFORMATION.NONE);
		}
	}

	@Override
	public String readLine() throws IOException
	{
		return this.bufReader.readLine();
	}

	@Override
	public int read() throws IOException
	{
		return this.bufReader.read();
	}

	@Override
	public byte[] read(int n) throws WebCilentException
	{
		byte[] buf = new byte[n];
		for(int i=0;i<n;)
		{
			try
			{
				i += this.bufReader.read(buf, i, n - i);
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new WebCilentException("The http response from web has a io error!\r\nFunction: public byte[] read(int n) throws WebCilentException"
						,ConstValue.CLASSNAME.HTTPSOCKET|ConstValue.FUNCTIONNAME.READ|
						ConstValue.ERRORTYPE.IOERROR|ConstValue.OTHERINFORMATION.NONE);
			}
			if(i == -1)
			{
				throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: public byte[] read(int n) throws WebCilentException"
						,ConstValue.CLASSNAME.HTTPSOCKET|ConstValue.FUNCTIONNAME.READ|
						ConstValue.ERRORTYPE.UNEXPECTEND|ConstValue.OTHERINFORMATION.NONE);
			}
		}
		return buf;
	}

	@Override
	public void reconnect() throws IOException
	{
		this.webSocket.close();
		this.connect();
	}

	@Override
	public void close() throws IOException
	{
		this.webSocket.close();
		this.webSocket = null;
	}

	@Override
	public int getPort()
	{
		return port;
	}
	
	@Override
	public void setPort(int port)
	{
		this.port = port;
	}
	
	protected void connect() throws IOException
	{
		this.webSocket = new Socket(this.addr,this.port);
		this.webSocket.setSoTimeout(this.timeOut);
		this.bufWriter = new BufferedWriter(new OutputStreamWriter(this.webSocket.getOutputStream()));
		this.bufReader = new AsciiReader(new BufferedInputStream(this.webSocket.getInputStream()));
	}
	
	@Override
	public void connect(InetAddress addr) throws IOException
	{
		this.addr = addr;
		this.connect();
	}

	@Override
	public void connect(InetAddress addr, int port) throws IOException
	{
		this.addr = addr;
		this.port = port;
		this.connect();
	}

	@Override
	public int getTimeOut()
	{
		return timeOut;
	}

	@Override
	public void setTimeOut(int timeOut)
	{
		this.timeOut = timeOut;
	}

}
