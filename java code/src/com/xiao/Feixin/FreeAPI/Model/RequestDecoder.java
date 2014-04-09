package com.xiao.Feixin.FreeAPI.Model;

import java.net.SocketTimeoutException;

public interface RequestDecoder
{
	public Request getRequest() throws SocketTimeoutException, IllegalDataStructException, TokenErrorException;
}
