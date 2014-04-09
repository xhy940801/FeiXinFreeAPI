package com.xiao.Feixin.FreeAPI.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FeixinURLConfig
{
	static private String feixinHost = null;
	static private String verificationImgURL = null;
	static private String sendFreeMsgURL = null;
	static private String loginURL = null;
	static private String connectURL = null;
	
	static public void init()
	{
		File configFile = new File(Config.getFeixinURLConfigPath());
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
		if(feixinHost == null)
			feixinHost = "webim.feixin.10086.cn";
		if(verificationImgURL == null)
			verificationImgURL = "/WebIM/GetPicCode.aspx?Type=ccpsession&";
		if(sendFreeMsgURL == null)
			sendFreeMsgURL = "/content/WebIM/SendSMS.aspx";
		if(loginURL == null)
			loginURL = "/WebIM/Login.aspx";
		if(connectURL == null)
			connectURL = "/WebIM/GetConnect.aspx";
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
			case "feixin_host":
				feixinHost = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "verification_img_url":
				verificationImgURL = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "send_free_msg_url":
				sendFreeMsgURL = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "login_url":
				loginURL = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			case "connect_url":
				connectURL = confKV[1].replaceAll("^\\s*|\\s*$", "");
				break;
			default:
				break;
			}
		}
		br.close();
	}
	
	static public String getVerificationImgURL()
	{
		return verificationImgURL;
	}

	static public String getSendfreeMsgURL()
	{
		return sendFreeMsgURL;
	}

	static public String getLoginURL()
	{
		return loginURL;
	}

	static public String getConnectURL()
	{
		return connectURL;
	}

	static public String getFeixinHost()
	{
		return feixinHost;
	}
}
