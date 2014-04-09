package com.xiao.Socket.WebClient;

import java.io.IOException;
import java.net.InetAddress;

public interface WebCilentSocket
{
	public void connect(InetAddress addr) throws IOException;
	public void connect(InetAddress addr, int port) throws IOException;
	public String sendAndReadLine(String req) throws WebCilentException;
	public String readLine() throws IOException;
	public int read() throws IOException;
	public byte[] read(int n) throws WebCilentException;
	public void reconnect() throws IOException;
	public void close() throws IOException;
	public int getPort();
	public void setPort(int port);
	public int getTimeOut();
	public void setTimeOut(int TimeOut);
}
