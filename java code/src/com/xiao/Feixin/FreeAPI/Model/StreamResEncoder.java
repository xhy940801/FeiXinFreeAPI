package com.xiao.Feixin.FreeAPI.Model;

import java.io.IOException;
import java.io.OutputStream;

import com.xiao.Feixin.FreeAPI.Config.ServerConfig;
import com.xiao.Feixin.FreeAPI.Log.Log;
import com.xiao.Feixin.FreeAPI.Operater.VerificationImage;

public class StreamResEncoder implements ResponseEncoder
{
	private OutputStream oStream;

	public StreamResEncoder(OutputStream oStream)
	{
		this.oStream = oStream;
	}

	@Override
	public void sendResponse(Response res)
	{
		Log.getResponseLog().log(res);
		byte[] msg;
		switch(res.getResCode())
		{
		case NOTLOGIN:
			msg = this.notLoginByte(res);
			break;
		case FAIL:
			msg = this.failByte(res);
			break;
		case REQUESTERROR:
			msg = this.resquestErrorByte(res);
			break;
		case SUCCESS:
			msg = this.successByte(res);
			break;
		case TIMEOUT:
			msg = this.timeoutByte(res);
			break;
		default:
			msg = this.failByte(res);
			break;
		}
		try
		{
			oStream.write(msg, 0, msg.length);
			oStream.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private byte[] notLoginByte(Response res)
	{
		String resCode = String.valueOf((Response.ResponseCode.NOTLOGIN.ordinal())) + "\n";
		Object o = res.getOtherInfo();
		if(o != null)
		{
			resCode += o.toString();
			resCode += "\n";
		}
		else
		{
			resCode += "Unknow error";
			resCode += "\n";
		}
		return resCode.getBytes(ServerConfig.getCharset());
	}
	
	private byte[] failByte(Response res)
	{
		String resCode = String.valueOf((Response.ResponseCode.FAIL.ordinal())) + "\n";
		Object o = res.getOtherInfo();
		if(o != null)
		{
			resCode += o.toString();
			resCode += "\n";
		}
		else
		{
			resCode += "Unknow error";
			resCode += "\n";
		}
		return resCode.getBytes(ServerConfig.getCharset());
	}
	
	private byte[] resquestErrorByte(Response res)
	{
		String resCode = String.valueOf((Response.ResponseCode.REQUESTERROR.ordinal())) + "\n";
		Object o = res.getOtherInfo();
		if(o != null)
		{
			resCode += o.toString();
			resCode += "\n";
		}
		else
		{
			resCode += "Unknow error";
			resCode += "\n";
		}
		return resCode.getBytes(ServerConfig.getCharset());
	}
	
	private byte[] timeoutByte(Response res)
	{
		String resCode = String.valueOf((Response.ResponseCode.TIMEOUT.ordinal())) + "\n";
		Object o = res.getOtherInfo();
		if(o != null)
		{
			resCode += o.toString();
			resCode += "\n";
		}
		else
		{
			resCode += "Unknow error";
			resCode += "\n";
		}
		return resCode.getBytes(ServerConfig.getCharset());
	}
	
	private byte[] successByte(Response res)
	{
		String resCode = String.valueOf((Response.ResponseCode.SUCCESS.ordinal()));
		resCode += "\n";
		if(res.getOtherInfo() != null)
		{
			if(res.getOtherInfo() instanceof VerificationImage)
			{
				VerificationImage vImg = (VerificationImage) res.getOtherInfo();
				resCode += vImg.getCookie().getValue();
				resCode += "\n";
				resCode += vImg.getImg().length;
				resCode += "\n";
				byte[] strBytes = resCode.getBytes(ServerConfig.getCharset());
				byte[] bytes = new byte[strBytes.length + vImg.getImg().length];
				System.arraycopy(strBytes, 0, bytes, 0, strBytes.length);
				System.arraycopy(vImg.getImg(), 0, bytes, strBytes.length, vImg.getImg().length);
				return bytes;
			}
		}
		return resCode.getBytes(ServerConfig.getCharset());
	}

}
