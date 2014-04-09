package com.xiao.Socket.WebClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GzipDecoder implements Decoder
{
	public GzipDecoder()
	{
		
	}

	@Override
	public byte[] decode(byte[] bytes) throws AnalysisError
	{
		try
		{
			ByteArrayInputStream bIS = new ByteArrayInputStream(bytes);
			GZIPInputStream gzipIS = new GZIPInputStream(bIS);
			ByteArray decodeByte = new ByteArray(bytes.length << 1);
			
			byte[] buffer = new byte[bytes.length];
			int count;
			while((count = gzipIS.read(buffer, 0, buffer.length)) != -1)
				decodeByte.add(buffer, 0, count);
			return decodeByte.toArray();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new AnalysisError("Gzip decode fail!\r\nFunction: public byte[] decode(byte[] bytes)",
					ConstValue.CLASSNAME.GZIPDECODER|ConstValue.FUNCTIONNAME.GZIPDECODE|
					ConstValue.ERRORTYPE.IOERROR|ConstValue.OTHERINFORMATION.NONE);
		}
	}
}
