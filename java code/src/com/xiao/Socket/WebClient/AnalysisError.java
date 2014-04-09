package com.xiao.Socket.WebClient;

public class AnalysisError extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorType = 0;

	public AnalysisError()
	{
		
	}

	public AnalysisError(String message)
	{
		super(message);
	}

	public AnalysisError(Throwable cause)
	{
		super(cause);
	}

	public AnalysisError(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public AnalysisError(String message, int err)
	{
		super(message);
		this.errorType = err;
	}
	
	public void setErrorType(int err)
	{
		this.errorType = err;
	}
	
	public int getErrorType()
	{
		return this.errorType;
	}
}
