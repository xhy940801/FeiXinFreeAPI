package com.xiao.Socket.WebClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpClient implements WebClient
{
	private static String defaultAccept = "*/*";
	private static String defaultAcceptLanguage = "zh-cn";
	
	WebCilentSocket webSocket;
	WebAnalysis webAnalysis;
	public HttpClient()
	{
		
	}
	
	public HttpClient(String host) throws UnknownHostException, WebCilentException
	{
		this.setHost(host);
	}
	
	public HttpClient(String host, String ipAddr) throws WebCilentException
	{
		this.setHost(host, ipAddr);
	}
	
	public HttpClient(String host, InetAddress ipAddr) throws WebCilentException
	{
		this.setHost(host, ipAddr);
	}
	
	public HttpClient(String host, int port) throws UnknownHostException, WebCilentException
	{
		this.setHost(host);
		this.webSocket.setPort(port);
	}
	
	public HttpClient(String host, String ipAddr, int port) throws WebCilentException
	{
		this.setHost(host, ipAddr);
		this.webSocket.setPort(port);
	}
	
	public HttpClient(String host, InetAddress ipAddr, int port) throws WebCilentException
	{
		this.setHost(host, ipAddr);
		this.webSocket.setPort(port);
	}

	@Override
	public void setHost(String host) throws WebCilentException
	{
		webSocket = this.getSocket();
		try
		{
			webSocket.connect(InetAddress.getByName(host));
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new WebCilentException("Create socket error!\r\nFunction: public HttpClient(String host) throws UnknownHostException",
					ConstValue.CLASSNAME.HTTPCILENT|ConstValue.FUNCTIONNAME.HTTPCILENT|
					ConstValue.ERRORTYPE.IOERROR|ConstValue.OTHERINFORMATION.NONE);
		}
		this.webAnalysis = new WebAnalysis();
		this.webAnalysis.setWebSocket(this.webSocket);
		this.webAnalysis.setHost(host);
		
	}

	@Override
	public void setHost(String host, String ipAddr) throws WebCilentException
	{
		webSocket = this.getSocket();
		try
		{
			webSocket.connect(InetAddress.getByName(ipAddr));
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new WebCilentException("Create socket error!\r\nFunction: public HttpClient(String host) throws UnknownHostException",
					ConstValue.CLASSNAME.HTTPCILENT|ConstValue.FUNCTIONNAME.HTTPCILENT|
					ConstValue.ERRORTYPE.IOERROR|ConstValue.OTHERINFORMATION.NONE);
		}
		this.webAnalysis = new WebAnalysis();
		this.webAnalysis.setWebSocket(this.webSocket);
		this.webAnalysis.setHost(host);
		
	}

	@Override
	public void setHost(String host, InetAddress ipAddr) throws WebCilentException
	{
		webSocket = this.getSocket();
		try
		{
			webSocket.connect(ipAddr);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new WebCilentException("Create socket error!\r\nFunction: public HttpClient(String host) throws UnknownHostException",
					ConstValue.CLASSNAME.HTTPCILENT|ConstValue.FUNCTIONNAME.HTTPCILENT|
					ConstValue.ERRORTYPE.IOERROR|ConstValue.OTHERINFORMATION.NONE);
		}
		this.webAnalysis = new WebAnalysis();
		this.webAnalysis.setWebSocket(this.webSocket);
		this.webAnalysis.setHost(host);
	}

	@Override
	public HttpResponse doGet(String path) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.getForHttp(path, null, null, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doGet(String path, KeyValuePair[] keyValuePairs) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.getForHttp(path, keyValuePairs, null, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doGet(String path, CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.getForHttp(path, null, CookieInfos, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doGet(String path, KeyValuePair[] keyValuePairs,
			CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.getForHttp(path, keyValuePairs, CookieInfos, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doGet(String path, KeyValuePair[] keyValuePairs,
			CookieInfo[] cookies, String accept, String acceptLanguage) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.getForHttp(path, keyValuePairs, cookies, accept, acceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doPost(String path) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.postForHttp(path, null, null, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doPost(String path, KeyValuePair[] keyValuePairs) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.postForHttp(path, keyValuePairs, null, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doPost(String path, CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.postForHttp(path, null, CookieInfos, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doPost(String path, KeyValuePair[] keyValuePairs,
			CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.postForHttp(path, keyValuePairs, CookieInfos, defaultAccept, defaultAcceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public HttpResponse doPost(String path, KeyValuePair[] keyValuePairs,
			CookieInfo[] cookies, String accept, String acceptLanguage) throws AnalysisError, WebCilentException
	{
		HttpResponse HR = this.webAnalysis.postForHttp(path, keyValuePairs, cookies, accept, acceptLanguage);
		this.Decode(HR);
		return HR;
	}

	@Override
	public int getTimeOut()
	{
		return this.webSocket.getTimeOut();
	}

	@Override
	public void setTimeOut(int timeOut)
	{
		this.webSocket.setTimeOut(timeOut);
	}

	@Override
	public String getDefaultAccept()
	{
		return HttpClient.defaultAccept;
	}

	@Override
	public void setDefaultAccept(String defaultAccept)
	{
		HttpClient.defaultAccept = defaultAccept;
	}

	@Override
	public String getDefaultAcceptLanguage()
	{
		return HttpClient.defaultAcceptLanguage;
	}

	@Override
	public void setDefaultAcceptLanguage(String defaultAcceptLanguage)
	{
		HttpClient.defaultAcceptLanguage = defaultAcceptLanguage;
	}

	@Override
	public int getDefaultPort()
	{
		return this.webSocket.getPort();
	}
	
	private void Decode(HttpResponse HR) throws AnalysisError
	{
		if(HR.getContentEncoding() == null)
			HR.setContentEncoding("");
		Decoder decoder;
		switch(HR.getContentEncoding().toLowerCase())
		{
		case "gzip":
			decoder = new GzipDecoder();
			break;
		default:
				decoder = null;
		}
		if(decoder != null)
		{
			HR.setBody(decoder.decode(HR.getBody()));
			HR.setContentEncoding("");
		}
	}

	@Override
	public void setHost(String host, int port) throws WebCilentException
	{
		this.setHost(host);
		this.webSocket.setPort(port);
	}

	@Override
	public void setHost(String host, String ipAddr, int port)
			throws WebCilentException
	{
		this.setHost(host, ipAddr);
		this.webSocket.setPort(port);
	}

	@Override
	public void setHost(String host, InetAddress ipAddr, int port)
			throws WebCilentException
	{
		this.setHost(host, ipAddr);
		this.webSocket.setPort(port);
	}

	@Override
	public void setEncodeCharset(String encodeCharset)
	{
		this.webAnalysis.setEncodeCharset(encodeCharset);
	}

	@Override
	public String getEncodeCharset()
	{
		return this.webAnalysis.getEncodeCharset();
	}
	
	protected WebCilentSocket getSocket()
	{
		return new HttpSocket();
	}

	@Override
	public int getPort()
	{
		return this.webSocket.getPort();
	}

	@Override
	public void setPort(int port)
	{
		this.webSocket.setPort(port);
	}
}
