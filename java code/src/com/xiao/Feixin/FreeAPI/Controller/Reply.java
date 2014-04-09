package com.xiao.Feixin.FreeAPI.Controller;

import com.xiao.Feixin.FreeAPI.Operater.ReturnCode;

public class Reply
{
	private final Commend commend;
	
	private final ReplyCode code;
	
	private Object otherInfo;

	public Reply(Commend commend, ReturnCode code)
	{
		this.commend = commend;
		this.code = new ReplyCode(code.getCode());
	}
	
	public Reply(Commend commend, ReplyCode code)
	{
		this.commend = commend;
		this.code = code;
	}

	public Commend getCommend()
	{
		return commend;
	}

	public boolean isSuccess()
	{
		return code.isSuccess();
	}
	
	public boolean isLogin()
	{
		return code.isLogin();
	}
	
	public int getCode()
	{
		return code.getCode();
	}
	
	public String getMsg()
	{
		return code.getMsg();
	}

	public Object getOtherInfo()
	{
		return otherInfo;
	}

	public void setOtherInfo(Object otherInfo)
	{
		this.otherInfo = otherInfo;
	}
	
	@Override
	public String toString()
	{
		if(this.isSuccess())
			return "Success!";
		else
			return "error: " + this.getMsg();
	}
}
