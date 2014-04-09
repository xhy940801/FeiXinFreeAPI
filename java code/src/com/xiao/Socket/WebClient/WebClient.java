package com.xiao.Socket.WebClient;
import java.net.InetAddress;

public interface WebClient
{
	public void setHost(String host) throws WebCilentException;
	public void setHost(String host, String ipAddr) throws WebCilentException;
	public void setHost(String host, InetAddress ipAddr) throws WebCilentException;
	public void setHost(String host, int port) throws WebCilentException;
	public void setHost(String host, String ipAddr, int port) throws WebCilentException;
	public void setHost(String host, InetAddress ipAddr, int port) throws WebCilentException;
	public HttpResponse doGet(String path) throws AnalysisError, WebCilentException;
	public HttpResponse doGet(String path, KeyValuePair[] keyValuePairs) throws AnalysisError, WebCilentException;
	public HttpResponse doGet(String path, CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException;
	public HttpResponse doGet(String path, KeyValuePair[] keyValuePairs, CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException;
	public HttpResponse doGet(String path, KeyValuePair[] keyValuePairs, CookieInfo[] cookies, String accept, String acceptLanguage) throws AnalysisError, WebCilentException;
	public HttpResponse doPost(String path) throws AnalysisError, WebCilentException;
	public HttpResponse doPost(String path, KeyValuePair[] keyValuePairs) throws AnalysisError, WebCilentException;
	public HttpResponse doPost(String path, CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException;
	public HttpResponse doPost(String path, KeyValuePair[] keyValuePairs, CookieInfo[] CookieInfos) throws AnalysisError, WebCilentException;
	public HttpResponse doPost(String path, KeyValuePair[] keyValuePairs, CookieInfo[] cookies, String accept, String acceptLanguage) throws AnalysisError, WebCilentException;
	public int getTimeOut();
	public void setTimeOut(int timeOut);
	public String getDefaultAccept();
	public void setDefaultAccept(String defaultAccept);
	public String getDefaultAcceptLanguage();
	public void setDefaultAcceptLanguage(String defaultAcceptLanguage);	
	public int getDefaultPort();
	public void setEncodeCharset(String encodeCharset);
	public String getEncodeCharset();
	public int getPort();
	public void setPort(int port);
	
}
