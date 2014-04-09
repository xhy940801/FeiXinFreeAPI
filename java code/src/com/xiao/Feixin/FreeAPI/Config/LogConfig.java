package com.xiao.Feixin.FreeAPI.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogConfig
{
	static private String errorLogPath;
	static private String requestLogPath;
	static private String responseLogPath;
	
	static public void init()
	{
		File configFile = new File(Config.getLogConfigPath());
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
		finally
		{
			initDefaultValue();
		}
	}
	
	static private void initDefaultValue()
	{
		if(errorLogPath == null)
			errorLogPath = "log/error_log.log";
		if(requestLogPath == null)
			requestLogPath = "log/request_log.log";
		if(responseLogPath == null)
			responseLogPath = "log/response_log.log";
	}
	
	static private void readConfig() throws IOException
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
			case "error_log_path":
				errorLogPath = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "request_log_path":
				requestLogPath = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "response_log_path":
				responseLogPath = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			default:
				break;
			}
		}
		br.close();
	}
	
	static public String getErrorLogPath()
	{
		return errorLogPath;
	}

	static public String getRequestLogPath()
	{
		return requestLogPath;
	}

	static public String getResponseLogPath()
	{
		return responseLogPath;
	}
}
