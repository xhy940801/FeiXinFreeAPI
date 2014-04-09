package com.xiao.Socket.WebClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class WebAnalysis
{
	private WebCilentSocket webSocket;
	private int bufLength = 10240;
	private String host;
	private String encodeCharset = "utf-8";
	public WebAnalysis()
	{
		
	}
	
	public void setWebSocket(WebCilentSocket webSocket)
	{
		this.webSocket = webSocket;
	}
	/**
	 * This function will return a HttpResponse object, which include all the information the class catch from web.
	 * 
	 * @param path A path from host to you want(if you want to browse www.baidu.com, you must set the host to "www.baidu.com" in contructer and the path must be "/".if you want to browse www.baidu.com/s you must set the path to "/s")
	 * @param keyValuePairs The request which you want to send
	 * @param cookies You can get it from HttpResponse.getCookieList().getValidCookieArray();
	 * @param accept The http head "Accept".default is "*(This is null)/*" you can change the SocketForWeb.defaultAccept
	 * @param acceptLanguage default is "zh-cn" you can change the SocketForWeb.defaultAcceptLanguage
	 * @param port the port for http, default is 80 you can change the SocketForWeb.defaultHttpPort
	 * 
	 * @see com.xiao.Socket.WebClient.CookieInfo
	 * @see com.xiao.Socket.WebClient.KeyValuePair
	 */
	public HttpResponse postForHttp(String path,KeyValuePair[] keyValuePairs,CookieInfo[] cookies,String accept,String acceptLanguage) throws AnalysisError, WebCilentException
	{
		String req = this.setHttpHeadForPost(path, keyValuePairs, cookies, accept, acceptLanguage);
		return this.sendAndGetResponse(req);
	}
	/**
	 * This function will return a HttpResponse object, which include all the information the class catch from web.
	 * 
	 * @param path A path from host to you want(if you want to browse www.baidu.com, you must set the host to "www.baidu.com" in contructer and the path must be "/".if you want to browse www.baidu.com/s you must set the path to "/s")
	 * @param keyValuePairs The request which you want to send
	 * @param cookies You can get it from HttpResponse.getCookieList().getValidCookieArray();
	 * @param accept The http head "Accept".default is "*(This is null)/*" you can change the SocketForWeb.defaultAccept
	 * @param acceptLanguage default is "zh-cn" you can change the SocketForWeb.defaultAcceptLanguage
	 * @param port the port for http, default is 80 you can change the SocketForWeb.defaultHttpPort
	 * 
	 * @see com.xiao.Socket.WebClient.CookieInfo
	 * @see com.xiao.Socket.WebClient.KeyValuePair
	 */
	public HttpResponse getForHttp(String path,KeyValuePair[] keyValuePairs,CookieInfo[] cookies,String accept,String acceptLanguage) throws AnalysisError, WebCilentException
	{
		String req = this.setHttpHeadForGet(path, keyValuePairs, cookies, accept, acceptLanguage);
		return this.sendAndGetResponse(req);
	}
	
	protected String setHttpHeadForPost(String path,KeyValuePair[] keyValuePairs,CookieInfo[] cookies,String accept,String acceptLanguage) throws AnalysisError
	{
		StringBuffer strBuf = new StringBuffer(350);
		StringBuffer kvBuf = new StringBuffer(50);
		try
		{
			if(keyValuePairs != null && keyValuePairs.length > 0)
			{
				kvBuf.append(URLEncoder.encode(keyValuePairs[0].key, this.encodeCharset));
				kvBuf.append('=');
				kvBuf.append(URLEncoder.encode(keyValuePairs[0].value, this.encodeCharset));
				for(int i=1;i<keyValuePairs.length;++i)
				{
					kvBuf.append('&');
					kvBuf.append(URLEncoder.encode(keyValuePairs[i].key, this.encodeCharset));
					kvBuf.append('=');
					kvBuf.append(URLEncoder.encode(keyValuePairs[i].value, this.encodeCharset));
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new AnalysisError("Unknow charset (\"" + this.encodeCharset + "\")!\r\nFunction: protected String setHttpHeadForPost(String path,KeyValuePair[] keyValuePairs,CookieInfo[] cookies,String accept,String acceptLanguage)  throws AnalysisError",
					ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.SETHTTPHEADFORPOST|
					ConstValue.ERRORTYPE.UNKNOWNCHARSET|ConstValue.OTHERINFORMATION.NONE);
		}
		strBuf.append("POST "+path+ " HTTP/1.1\r\n");
		strBuf.append("Accept: " + accept + "\r\n");
		strBuf.append("Accept-Language: " + acceptLanguage + "\r\n");
		strBuf.append("Accept-Encoding: gzip\r\n");
		strBuf.append("Content-Type: application/x-www-form-urlencoded\r\n");
		strBuf.append("Connection: Keep-Alive\r\n");
		strBuf.append("Host: " + this.host + "\r\n");
		strBuf.append("Content-Length: " + String.valueOf(kvBuf.length()) + "\r\n");
		if(cookies != null && cookies.length > 0)
		{
			strBuf.append("Cookie:");
			for(int i=0;i<cookies.length;++i)
			{
				strBuf.append(' ');
				strBuf.append(cookies[i].getKey());
				strBuf.append('=');
				strBuf.append(cookies[i].getValue());
				strBuf.append(';');
			}
			strBuf.setCharAt(strBuf.length()-1, '\r');
			strBuf.append('\n');
		}
		strBuf.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.48 Safari/537.36\r\n\r\n");
		strBuf.append(kvBuf.toString());
		return strBuf.toString();
	}
	
	protected String setHttpHeadForGet(String path,KeyValuePair[] keyValuePairs,CookieInfo[] cookies,String accept,String acceptLanguage) throws AnalysisError
	{
		StringBuffer strBuf = new StringBuffer(350);
		strBuf.append("GET " + path);
		try
		{
			if(keyValuePairs != null && keyValuePairs.length > 0)
			{
				strBuf.append('?');
				strBuf.append(URLEncoder.encode(keyValuePairs[0].key, this.encodeCharset));
				strBuf.append('=');
				strBuf.append(URLEncoder.encode(keyValuePairs[0].value, this.encodeCharset));
				for(int i=1;i<keyValuePairs.length;++i)
				{
					strBuf.append('&');
					strBuf.append(URLEncoder.encode(keyValuePairs[i].key, this.encodeCharset));
					strBuf.append('=');
					strBuf.append(URLEncoder.encode(keyValuePairs[i].value, this.encodeCharset));
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new AnalysisError("Unknow charset (\"" + this.encodeCharset + "\")!\r\nFunction: protected String setHttpHeadForGet(String path,KeyValuePair[] keyValuePairs,CookieInfo[] cookies,String accept,String acceptLanguage)  throws AnalysisError",
					ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.SETHTTPHEADFORGET|
					ConstValue.ERRORTYPE.UNKNOWNCHARSET|ConstValue.OTHERINFORMATION.NONE);
		}
		
		strBuf.append(" HTTP/1.1\r\n");
		strBuf.append("Accept: " + accept + "\r\n");
		strBuf.append("Accept-Language: " + acceptLanguage + "\r\n");
		strBuf.append("Accept-Encoding: gzip\r\n");
		strBuf.append("Connection: Keep-Alive\r\n");
		strBuf.append("Host: " + this.host + "\r\n");
		if(cookies != null && cookies.length > 0)
		{
			strBuf.append("Cookie:");
			for(int i=0;i<cookies.length;++i)
			{
				strBuf.append(' ');
				strBuf.append(cookies[i].getKey());
				strBuf.append('=');
				strBuf.append(cookies[i].getValue());
				strBuf.append(';');
			}
			strBuf.setCharAt(strBuf.length()-1, '\r');
			strBuf.append('\n');
		}
		strBuf.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.48 Safari/537.36\r\n\r\n");
		return strBuf.toString();
	}
	
	protected HttpResponse sendAndGetResponse(String req) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = new HttpResponse();
		this.sendRequestAndAnalysisStatus(req, HR);
		OtherInfo otherInfo = this.analysisHead(HR);
		byte[] bodyBytes;
		if(otherInfo.contentLength < 0)
			bodyBytes = this.getBodyBytesByChunk();
		else
			bodyBytes = this.getBodyBytesByContentLength(otherInfo.contentLength);
		HR.setBody(bodyBytes);
		if(!otherInfo.keepAlive)
			try
			{
				this.webSocket.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new WebCilentException("The web socket close error!\r\nFunction: protected HttpResponse sendAndGetResponse(String req) throws AnalysisError, WebCilentException"
						,ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.SENDANDGETRESPONSE|
						ConstValue.ERRORTYPE.IOERROR|ConstValue.OTHERINFORMATION.CLOSESOCKETERROR);
			}
		return HR;
	}
	
	protected void sendRequestAndAnalysisStatus(String req, HttpResponse HR) throws WebCilentException, AnalysisError
	{
		String status = this.webSocket.sendAndReadLine(req);
		while(this.weekEqual(status, ""))
		{
			try
			{
				status = this.webSocket.readLine();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: protected void sendRequestAndAnalysisStatus(String req, HttpResponse HR) throws WebCilentException, AnalysisError"
						,ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.SENDREQUESTANDANALYSISSTATUS|
						ConstValue.ERRORTYPE.UNEXPECTEND|ConstValue.OTHERINFORMATION.NONE);
			}
		}
		this.analysisStatus(status, HR);
	}
	
	protected void analysisStatus(String statusStr, HttpResponse HR) throws NumberFormatException, AnalysisError
	{
		String[] arr = statusStr.split(" ", 3);
		if(arr.length < 2)
		{
			throw new AnalysisError("Analysis head error!\r\nFuction: protected void analysisStatus(String statusStr, HttpResponse HR) throws NumberFormatException, AnalysisError",
					ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.ANALYSISSTATUS|
					ConstValue.ERRORTYPE.FORMERROR|ConstValue.OTHERINFORMATION.NONE);
		}
		HR.setVersion(arr[0]);
		try
		{
			HR.setStatus(Integer.parseInt(arr[1]));
		}
		catch(NumberFormatException e)
		{
			throw new AnalysisError("Analysis head error!\r\nFuction: protected void analysisStatus(String statusStr, HttpResponse HR) throws NumberFormatException, AnalysisError",
					ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.ANALYSISSTATUS|
					ConstValue.ERRORTYPE.FORMERROR|ConstValue.OTHERINFORMATION.NONE);
		}
	}
	
	protected OtherInfo analysisHead(HttpResponse HR) throws WebCilentException, AnalysisError
	{
		OtherInfo otherInfo = new OtherInfo();
		boolean inContinue = true;
		StringBuffer strBuffForOtherInfo= new StringBuffer(50);
		while(inContinue)
		{
			String line;
			try
			{
				line = this.webSocket.readLine();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: protected OtherInfo analysisHead(HttpResponse HR) throws WebCilentException, AnalysisError"
						,ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.ANALYSISHEAD|
						ConstValue.ERRORTYPE.UNEXPECTEND|ConstValue.OTHERINFORMATION.NONE);
			}
			if(line == null)
			{
				throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: protected OtherInfo analysisHead(HttpResponse HR) throws WebCilentException, AnalysisError"
						,ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.ANALYSISHEAD|
						ConstValue.ERRORTYPE.UNEXPECTEND|ConstValue.OTHERINFORMATION.NONE);
			}
			String[] arr = line.split(":",2);
			try
			{
				arr[0] = arr[0].replaceFirst("\\s*$", "");
				switch(arr[0].toLowerCase())
				{
				case "date":
					this.setDate(HR, arr[1]);
					break;
				case "connection":
					if(this.weekEqual(arr[1],"keep-alive"))
						break;
					else
					{
						otherInfo.keepAlive = false;
						break;
					}
				case "content-type":
					this.setContentType(HR, arr[1]);
					break;
				case "expires":
					this.setExpires(HR, arr[1]);
					break;
				case "content-length":
					otherInfo.contentLength = Integer.parseInt(arr[1].replaceAll("^\\s*|\\s*$", ""));
					break;
				case "set-cookie":
					HR.getCookieList().addCookie(new CookieInfo(arr[1]));
					break;
				case "content-encoding":
					if(arr.length > 1)
						HR.setContentEncoding(arr[1].toLowerCase());
					break;
				case "":
					inContinue = false;
					break;
				default:
					strBuffForOtherInfo.append(arr[0]);
					strBuffForOtherInfo.append(":");
					strBuffForOtherInfo.append(arr[1]);
					strBuffForOtherInfo.append("\r\n");
					break;
				}
			}
			catch(ParseException p)
			{
				p.printStackTrace();
				throw new AnalysisError("Analysis head error!\r\nFuction: protected int analysisHead(HttpResponse HR) throws WebCilentException, AnalysisError",
						ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.ANALYSISHEAD|
						ConstValue.ERRORTYPE.FORMERROR|ConstValue.OTHERINFORMATION.NONE);
			}
		}
		HR.setOtherInfomation(strBuffForOtherInfo.toString());
		return otherInfo;
	}
	
	protected void setDate(HttpResponse HR, String strDate) throws ParseException
	{
		if(this.weekEqual(strDate, "-1"))
			HR.setExpires(new java.util.Date());
		else
			HR.setDate(this.turnStrTodate(strDate));
	}
	
	protected void setExpires(HttpResponse HR, String strDate) throws ParseException
	{
		HR.setExpires(this.turnStrTodate(strDate));
	}
	
	protected java.util.Date turnStrTodate(String strDate)
	{
		strDate = strDate.replaceFirst("^\\s*", "");
		strDate = strDate.replaceAll("\\s+", " ");
		strDate = strDate.replaceAll("\\s,|,\\s", ",");
		SimpleDateFormat simpleDateFormat;
		if(strDate.matches("^[a-zA-Z]+,\\d{2}-[a-zA-Z]+-\\d{2,4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]+ *$"))
			simpleDateFormat = new java.text.SimpleDateFormat("E,d-MMM-y H:m:s z",Locale.US);
		else if(strDate.matches("^[a-zA-Z]+,\\d{2} [a-zA-Z]+ \\d{2,4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]+ *$"))
			simpleDateFormat = new java.text.SimpleDateFormat("E,d MMM y H:m:s z",Locale.US);
		else if(strDate.matches("^[a-zA-Z]+,\\d{2}-\\d{2}+-\\d{2,4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]+ *$"))
			simpleDateFormat = new java.text.SimpleDateFormat("E,d-M-y H:m:s z",Locale.US);
		else
			simpleDateFormat = new java.text.SimpleDateFormat("E,d MMM y H:m:s Z",Locale.US);
		try
		{
			return simpleDateFormat.parse(strDate);
		} catch (ParseException e)
		{
			return new java.util.Date();
		}
	}
	
	protected void setContentType(HttpResponse HR, String strContentType)
	{
		String[] arr = strContentType.split(";",2);
		if(arr.length < 1)
		{
			//Throw a exception
		}
		HR.setDataType(arr[0]);
		if(arr.length > 1)
		{
			String[] arrCharset = arr[1].split("=",2);
			if(arrCharset.length == 2 && this.weekEqual(arrCharset[0],"charset"))
				HR.setCharset(arrCharset[1]);
		}
	}
	
	protected boolean weekEqual(String str1, String str2)
	{
		return str1.replaceAll("^\\s*|\\s*$", "").toLowerCase().equals(str2.replaceAll("^\\s*|\\s*$", "").toLowerCase());
	}
	
	protected byte[] getBodyBytesByContentLength(int length) throws WebCilentException
	{
		byte[] byteArray = this.webSocket.read(length);
		return byteArray;
	}
	
	protected byte[] getBodyBytesByChunk() throws WebCilentException
	{
		ByteArray byteArray = new ByteArray(this.bufLength);
		while(true)
		{
			String strLength;
			try
			{
				strLength = this.webSocket.readLine();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: protected byte[] getBodyByteByChunk() throws IOException, WebCilentException"
						,ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.GETBODYBYTESBYCHUNK|
						ConstValue.ERRORTYPE.UNEXPECTEND|ConstValue.OTHERINFORMATION.NONE);
			}
			if(strLength == null)
			{
				throw new WebCilentException("The http response from web has a unexpected end!\r\nFunction: protected byte[] getBodyByteByChunk() throws IOException, WebCilentException"
						,ConstValue.CLASSNAME.WEBANALYSIS|ConstValue.FUNCTIONNAME.GETBODYBYTESBYCHUNK|
						ConstValue.ERRORTYPE.UNEXPECTEND|ConstValue.OTHERINFORMATION.NONE);
			}
			if(this.weekEqual(strLength, ""))
				continue;
			int length; 
			if((length = Integer.parseInt(strLength.replaceAll("^\\s*|\\s*$", ""),16)) > 0)
			{
				byte[] bytes = this.webSocket.read(length);
				byteArray.add(bytes, 0, length);
			}
			else
				break;
		}
		return byteArray.toArray();
	}
	
	public final int getBufLength()
	{
		return bufLength;
	}

	public final void setBufLength(int bufLength)
	{
		this.bufLength = bufLength;
	}
	
	protected class OtherInfo
	{
		int contentLength = -1;
		boolean keepAlive = true;
	}
	
	public final void setHost(String host)
	{
		this.host = host;
	}
	
	public final String getHost()
	{
		return this.host;
	}
	
	public final String getEncodeCharset()
	{
		return this.encodeCharset;
	}
	
	public final void setEncodeCharset(String encodeCharset)
	{
		this.encodeCharset = encodeCharset;
	}
}
