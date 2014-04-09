package com.xiao.Feixin.FreeAPI.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;

import com.xiao.Feixin.FreeAPI.Operater.Message;

public class MessageModel
{
	static private String table = "message";
	
	static public boolean saveMessage(Message msg)
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return false;
		boolean suc = saveMsg(msg, conn);
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return suc;
	}
	
	static public boolean saveMessage(Message[] msgs)
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return false;
		boolean suc = true;
		for(Message msg : msgs)
		{
			if(!saveMsg(msg, conn))
				suc = false;
		}
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return suc;
	}
	
	static public boolean saveMessage(Collection<Message> msgs)
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return false;
		boolean suc = true;
		for(Message msg : msgs)
		{
			if(!saveMsg(msg, conn))
				suc = false;
		}
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return suc;
	}
	
	static public int getMsgCount()
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return -1;
		String sql = "SELECT COUNT( * ) AS number FROM  `" + table + "` WHERE 1";
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			int n = rs.getInt("number");
			conn.close();
			return n;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	static public Message[] poll(int n)
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return new Message[0];
		String sql = "SELECT * FROM  `message` WHERE 1 ORDER BY  `date` ASC LIMIT 0 , " + n;
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			rs.last();
			int count = rs.getRow();
			Message[] msgs = new Message[count];
			rs.first();
			for(int i=0;i < count;++i)
			{
				long senderUid = rs.getLong("sender_uid");
				if(senderUid == 0)
					senderUid = -1;
				
				long receiverUid = rs.getLong("receiver_uid");
				if(receiverUid == 0)
					senderUid = -1;
				
				String message = rs.getString("msg");
				
				java.sql.Timestamp dateTime = rs.getTimestamp("date");
				java.util.Date d = new java.util.Date(dateTime.getTime());
				msgs[i] = new Message(senderUid, receiverUid, message, d);
				rs.next();
			}
			conn.close();
			return msgs;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return new Message[0];
		}
	}
	
	static public boolean isExist(Message msg)
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return false;
		long sender = msg.getSenderUid();
		long receiver = msg.getReceiverUid();
		String message = msg.getMsg();
		Timestamp dateTime = new Timestamp(msg.getDate().getTime());
		String sql = "SELECT COUNT( * ) AS number FROM  `" + table + "` "
				+ "WHERE `sender_uid` = " + sender + " "
				+ "AND `receiver_uid` = " + receiver + " "
				+ "AND `msg` = " + message + " "
				+ "AND `date" + dateTime;
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			int n = rs.getInt("number");
			conn.close();
			return n > 0;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	static public void clear()
	{
		Connection conn = DataBaseConnecter.getConnection();
		if(conn == null)
			return;
		String sql = "DELETE FROM `" + table + "` WHERE 1";
		try
		{
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	static private boolean saveMsg(Message msg, Connection conn)
	{
		long sender = msg.getSenderUid();
		long receiver = msg.getReceiverUid();
		String message = msg.getMsg();
		Timestamp dateTime = new Timestamp(msg.getDate().getTime());
		String sql = "INSERT INTO `" + table + "`"
				+ "(`sender_uid`, `receiver_uid`, `msg`, `date`) "
				+ "VALUES (" + sender + "," + receiver + ",\'" + message + "\'," + dateTime + ")";
		try
		{
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
