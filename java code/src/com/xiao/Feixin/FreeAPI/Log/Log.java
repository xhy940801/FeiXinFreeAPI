package com.xiao.Feixin.FreeAPI.Log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.xiao.Feixin.FreeAPI.Config.LogConfig;
import com.xiao.Feixin.FreeAPI.Config.ServerConfig;

public class Log
{
	private String logPath;
	private Lock lock;
	
	private Log(String logPath)
	{
		this.logPath = logPath;
		lock = new ReentrantLock();
	}
	
	public void log(Exception e)
	{
		try
		{
			lock.lock();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logPath, true), ServerConfig.getCharset()));
			PrintWriter pw = new PrintWriter(bw);
			pw.println(new Date());
			pw.println("------------------------");
			e.printStackTrace(pw);
			pw.println("------------------------");
			pw.println();
			pw.flush();
			pw.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void log(Exception e, Object o)
	{
		try
		{
			lock.lock();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logPath, true), ServerConfig.getCharset()));
			PrintWriter pw = new PrintWriter(bw);
			pw.println(new Date());
			pw.println("------------------------");
			e.printStackTrace(pw);
			pw.println();
			pw.println("OtherInfo:");
			pw.println(o);
			pw.println("------------------------");
			pw.println();
			pw.flush();
			pw.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void log(Object o)
	{
		try
		{
			lock.lock();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logPath, true), ServerConfig.getCharset()));
			PrintWriter pw = new PrintWriter(bw);
			pw.println(new Date());
			pw.println("------------------------");
			pw.println(o);
			pw.println("------------------------");
			pw.println();
			pw.flush();
			pw.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	static private Log errorLog;
	static private Log responseLog;
	static private Log requestLog;
	
	static public Log getErrorLog()
	{
		return errorLog;
	}
	
	static public Log getResponseLog()
	{
		return responseLog;
	}
	
	static public Log getRequestLog()
	{
		return requestLog;
	}
	
	static public void init()
	{
		errorLog = new Log(LogConfig.getErrorLogPath());
		responseLog = new Log(LogConfig.getResponseLogPath());
		requestLog = new Log(LogConfig.getRequestLogPath());
	}
}
