package com.xiao.Feixin.FreeAPI.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.xiao.Feixin.FreeAPI.Config.Config;
import com.xiao.Feixin.FreeAPI.Config.ServerConfig;
import com.xiao.Feixin.FreeAPI.Controller.ThreadFeixinController;
import com.xiao.Feixin.FreeAPI.Log.Log;
import com.xiao.Feixin.FreeAPI.Model.RequestDecoder;
import com.xiao.Feixin.FreeAPI.Model.ResponseEncoder;
import com.xiao.Feixin.FreeAPI.Model.StreamReqDecoder;
import com.xiao.Feixin.FreeAPI.Model.StreamResEncoder;
import com.xiao.Feixin.FreeAPI.Operater.FeixinOperator;
import com.xiao.Socket.WebClient.WebCilentException;

public class InitServer implements Runnable
{
	private ThreadFeixinController fController;
	Thread tFController;
	private boolean run = true;
	
	public InitServer()
	{
		
	}
	
	public void init() throws UnknownHostException, WebCilentException
	{
		Config.init();
		Log.init();
		FeixinOperator fOperator = new FeixinOperator();
		fController = new ThreadFeixinController(fOperator);
		tFController = new Thread(fController);
		tFController.start();
	}

	@Override
	public void run()
	{
		ServerSocket server;
		try
		{
			server = new ServerSocket(ServerConfig.getPort());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			run = false;
			return;
		}
		while(run)
		{
			try
			{
				Socket socket = server.accept();
				ResponseEncoder resEncoder = new StreamResEncoder(socket.getOutputStream());
				RequestDecoder reqDecoder = new StreamReqDecoder(socket.getInputStream());
				FeixinServer fs = new FeixinServer(fController, resEncoder, reqDecoder, socket, tFController);
				Thread t = new Thread(fs);
				t.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		if(server != null)
		{
			try
			{
				server.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void stop()
	{
		run = false;
	}
	
	public boolean isRun()
	{
		return run;
	}
}
