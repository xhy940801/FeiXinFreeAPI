package com.xiao.Feixin.FreeAPI.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config
{
	static private String ServerConfigPath = null;
	static private String FeixinURLConfigPath = null;
	static private String LogConfigPath = null;
	static private String accessToken = null;
	
	static private String configPath = "conf/config.conf";
	
	static public void init()
	{
		File configFile = new File(configPath);
		try
		{
			if(configFile.exists())
				readConfig();
			else
				configFile.createNewFile();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			initDefaultValue();
		}
		
		FeixinURLConfig.init();
		LogConfig.init();
		ServerConfig.init();
	}
	
	static private void initDefaultValue()
	{
		if(ServerConfigPath == null)
			ServerConfigPath = "conf/server_config.conf";
		if(FeixinURLConfigPath == null)
			FeixinURLConfigPath = "conf/feixin_url_config.conf";
		if(LogConfigPath == null)
			LogConfigPath = "conf/log_config.conf";
		if(accessToken == null)
			accessToken = "";
	}
	
	static private void readConfig() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configPath)));
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
			case "server_config_path":
				ServerConfigPath = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "feixin_url_config_path":
				FeixinURLConfigPath = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "log_config_path":
				LogConfigPath = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "access_token":
				accessToken = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			default:
				break;
			}
		}
		br.close();
	}

	static public String getServerConfigPath()
	{
		return ServerConfigPath;
	}

	static public String getFeixinURLConfigPath()
	{
		return FeixinURLConfigPath;
	}

	static public String getLogConfigPath()
	{
		return LogConfigPath;
	}

	public static String getAccessToken()
	{
		return accessToken;
	}
}
