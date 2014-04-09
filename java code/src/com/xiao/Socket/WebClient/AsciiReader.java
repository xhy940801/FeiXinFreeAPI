package com.xiao.Socket.WebClient;

import java.io.IOException;
import java.nio.charset.Charset;
/**
 * This class is a stream reader like BufferedReader.
 * But it's read function can get char base array or a byte base array as you want.
 */
public class AsciiReader
{
	private java.io.InputStream inputStream;
	private Charset charset;
	/**
	 * This construct function needed a input stream.
	 * @param input the input stream.
	 */
	public AsciiReader(java.io.InputStream input)
	{
		this.inputStream = input;
		this.charset = Charset.forName("US-ASCII");
	}
	/**
	 * This construct function needed a input stream and a charset name.
	 * @param input the input stream.
	 * @param charset a string value, the charset name.
	 */
	public AsciiReader(java.io.InputStream input, String charset)
	{
		this.inputStream = input;
		this.charset = Charset.forName(charset);
	}
	/**
	 * This construct function needed a input stream and a charset name.
	 * @param input the input stream.
	 * @param charset a Charset object.
	 */
	public AsciiReader(java.io.InputStream input, Charset charset)
	{
		this.inputStream = input;
		this.charset = charset;
	}
	/**
     * Reads a line of text.  A line is considered to be terminated by any one
     * of a line feed ('\n') or a carriage return
     * This function may return a ('\r') in the end of the String.
     * followed immediately by a linefeed.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @see java.io.BufferedReader
     */
	public String readLine() throws IOException
	{
		ByteArray byteArray = new ByteArray(30);
		int b = this.inputStream.read();
		if(b == -1)
			return null;
		else if(b == (int)'\n')
			return "";
		else
			byteArray.add((byte)b);
		while((b=this.inputStream.read()) != -1 && b != (int)'\n')
			byteArray.add((byte)b);
		return new String(byteArray.toArray(),this.charset);
	}
	/**
	 * This function just to call InputStream.read(buf, offset, limit)
	 * 
	 * @see java.io.InputStream
	 */
	public int read(byte[] buf, int offset, int limit) throws IOException
	{
		return this.inputStream.read(buf, offset, limit);
	}
	/**
	 * This function just to call InputStream.read()
	 * 
	 * @see java.io.InputStream
	 */
	public int read() throws IOException
	{
		return this.inputStream.read();
	}
	
	public Charset getCharset()
	{
		return this.charset;
	}
	
	public void setCharset(String charset)
	{
		this.charset = Charset.forName(charset);
	}
	
	public void setCharset(Charset charset)
	{
		this.charset = charset;
	}
}
