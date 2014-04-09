package com.xiao.Socket.WebClient;

public class WebCilentException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorType = 0;
	public WebCilentException()
	{
		
	}

	public WebCilentException(String message)
	{
		super(message);
	}

	public WebCilentException(Throwable cause)
	{
		super(cause);
	}

	public WebCilentException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public WebCilentException(String message, int err)
	{
		super(message);
		this.errorType = err;
	}

	public int getErrorType()
	{
		return errorType;
	}

	public void setErrorType(int errorType)
	{
		this.errorType = errorType;
	}
}
