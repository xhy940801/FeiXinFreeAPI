package com.xiao.Socket.WebClient;

public interface Decoder
{
	public byte[] decode(byte[] bytes) throws AnalysisError;
}
