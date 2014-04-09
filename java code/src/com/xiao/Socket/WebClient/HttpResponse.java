package com.xiao.Socket.WebClient;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
/**
 * 
 * @author xiao
 *
 * The class has all the information of http response from web server
 */
public class HttpResponse
{
	private int status = -1;
	private String version = null;
	private java.util.Date date = null, expires = null;
	private String dataType = null,charset = null;
	private CookieList cookieList;
	private String otherInfomation = null;
	private String contentEncoding = null;
	private byte[] body = null;
	
	public HttpResponse()
	{
		this.cookieList = new CookieList();
	}
	
	public final int getStatus()
	{
		return status;
	}
	
	public final void setStatus(int status)
	{
		this.status = status;
	}
	
	public final String getVersion()
	{
		return version;
	}
	
	public final void setVersion(String version)
	{
		this.version = version.replaceAll("^\\s*|\\s*$", "");
	}
	
	public final java.util.Date getDate()
	{
		return date;
	}
	
	public final void setDate(java.util.Date date)
	{
		this.date = date;
	}
	
	public final java.util.Date getExpires()
	{
		return expires;
	}
	
	public final void setExpires(java.util.Date expires)
	{
		this.expires = expires;
	}
	
	public final String getDataType()
	{
		return dataType;
	}
	
	public final void setDataType(String dataType)
	{
		this.dataType = dataType.replaceAll("^\\s*|\\s*$", "");
	}
	
	public final String getCharset()
	{
		return charset;
	}
	
	public final void setCharset(String charset)
	{
		this.charset = charset.replaceAll("^\\s*|\\s*$", "");
	}
	
	public final CookieList getCookieList()
	{
		return cookieList;
	}
	
	public final String getOtherInfomation()
	{
		return otherInfomation;
	}
	
	public final void setOtherInfomation(String otherInfomation)
	{
		this.otherInfomation = otherInfomation;
	}
	
	public final byte[] getBody()
	{
		return body;
	}
	
	public final void setBody(byte[] body)
	{
		this.body = body;
	}

	public final String getContentEncoding()
	{
		return contentEncoding;
	}

	public final void setContentEncoding(String contentEncoding)
	{
		this.contentEncoding = contentEncoding.replaceAll("^\\s*|\\s*$", "");
	}
	/**
	 * This function will turn the body base byte to String if HttpResponse.charset is not null we use it, else we use the jvm default charset.
	 * @return A string 
	 * @throws UnsupportedEncodingException
	 */
	public String turnByteToString() throws UnsupportedEncodingException
	{
		if(this.charset == null)
			return new String(this.body);
		else
			return new String(this.body, this.charset);
	}
	/**
	 * This function will turn the body base byte to String.
	 * @param charset we use the charset to turn byte to string.
	 * @return A string 
	 * @throws UnsupportedEncodingException
	 */
	public String turnByteToString(String charset) throws UnsupportedEncodingException
	{
		return new String(this.body, charset);
	}
	/**
	 * This function will turn the body base byte to String.
	 * @param charset we use the charset to turn byte to string.
	 * @return A string 
	 * @throws UnsupportedEncodingException
	 */
	public String turnByteToString(Charset charset) throws UnsupportedEncodingException
	{
		return new String(this.body, charset);
	}
}
