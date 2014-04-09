package com.xiao.Feixin.FreeAPI.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ServerConfig
{
	static private int port = -1;
	static private int reconnectTimes = -1;
	static private Charset charset = null;
	
	static public void init()
	{
		File configFile = new File(Config.getServerConfigPath());
		try
		{
			if(configFile.exists())
				readConfig();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		finally
		{
			initDefaultValue();
		}
	}
	
	static private void initDefaultValue()
	{
		if(port == -1)
			port = 12322;
		if(reconnectTimes == -1)
			reconnectTimes = 2;
		if(charset == null)
			charset = Charset.forName("utf8");
	}
	
	static private void readConfig() throws IOException, NumberFormatException, IllegalArgumentException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Config.getLogConfigPath())));
		while(true)
		{
			String conf = br.readLine();
			if(conf == null)
				break;
			conf = conf.replaceAll("^\\s*|\\s*$", "").replaceFirst("#.*", "").toLowerCase();
			String[] confKV = conf.split("=", 2);
			if(confKV.length < 2)
				continue;
			switch(confKV[0].replaceAll("^\\s*|\\s*$", "").toLowerCase())
			{
			case "port":
				port = Integer.parseInt(confKV[1].replaceAll("^\\s*|\\s*$", ""));
				break;
			case "reconnect_times":
				reconnectTimes = Integer.parseInt(confKV[1].replaceAll("^\\s*|\\s*$", ""));
				break;
			case "charset":
				charset = Charset.forName(confKV[1].replaceAll("^\\s*|\\s*$", ""));
				break;
			default:
				break;
			}
		}
		br.close();
	}

	static public int getPort()
	{
		return port;
	}

	static public int getReconnectTimes()
	{
		return reconnectTimes;
	}

	static public Charset getCharset()
	{
		return charset;
	}
}
