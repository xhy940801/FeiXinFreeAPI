package com.xiao.Socket.WebClient;

public class ConstValue
{
	//Class name
	class CLASSNAME
	{
		public static final int COOKIEINFO = 0x01000000;
		public static final int WEBANALYSIS = 0x02000000;
		public static final int HTTPSOCKET = 0x03000000;
		public static final int HTTPCILENT = 0x04000000;
		public static final int GZIPDECODER = 0x05000000;
	}
	
	//function name
	class FUNCTIONNAME
	{
		public static final int SET_SETCOOKIESTR = 0x00010000;
		public static final int STRINGTODATE = 0x00020000;
		public static final int GZIPDECODE = 0x00030000;
		public static final int SENDREQUESTANDANALYSISSTATUS = 0x00040000;
		public static final int ANALYSISHEAD = 0x00050000;
		public static final int SENDANDGETRESPONSE = 0x00060000;
		public static final int GETBODYBYTESBYCHUNK = 0x00070000;
		public static final int GETBODYBYTESBYCONTENTLENGTH = 0x00080000;
		//HttpSocket
		public static final int SENDANDREADLINE = 0x00090000;
		public static final int READ = 0x00100000;
		//WebAnalysis
		public static final int ANALYSISSTATUS = 0x00110000;
		public static final int SETHTTPHEADFORPOST = 0x00120000;
		public static final int SETHTTPHEADFORGET = 0x00130000;
		//HttpCilent
		public static final int HTTPCILENT = 0x00120000;
		//Decoder
		public static final int DECODE = 0x00130000;
	}
	
	//ErrorType
	class ERRORTYPE
	{
		public static final int FORMERROR = 0x00000100;
		public static final int IOERROR = 0x00000200;
		public static final int UNEXPECTEND = 0x00000300;
		public static final int CONNECTIONERROR = 0x00000400;
		public static final int UNKNOWNCONTENTENCORDING = 0x00000500;
		public static final int UNKNOWNCHARSET = 0x000000600;
	}
	
	//Other information
	class OTHERINFORMATION
	{
		public static final int NONE = 0x00000000;
		//
		public static final int CLOSESOCKETERROR = 0x00000001;
	}
}