package com.xiao.Feixin.FreeAPI.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnecter
{
	private static final String driver = "com.mysql.jdbc.Driver";
	private static boolean init = false;
	
	private static final String dataBaseHost = "jdbc:mysql://127.0.0.1:3306/";
	private static final String dataBaseName = "feixin_message";
	private static final String user = "root";
	private static final String password = "kfdiv71569";
	
	private static Connection conn;
	
	public static Connection getConnection()
	{
		try
		{
			if(conn != null && !conn.isClosed())
				return conn;
			else
			{
				conn = DriverManager.getConnection(dataBaseHost + dataBaseName, user, password);
				return conn;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void init()
	{
		if(!init)
		{
			try
			{
				Class.forName(driver);
				init = true;
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static boolean isInit()
	{
		return init;
	}
}
