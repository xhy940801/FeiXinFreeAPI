package com.xiao.Socket.WebClient;

public class ByteArray
{
	private int size;
	private byte[] elementDate;
	
	public ByteArray(int initialCapacity)
	{
		this.elementDate = new byte[initialCapacity];
		this.size = 0;
	}
	
	public ByteArray()
	{
		this.elementDate = new byte[10];
		this.size = 0;
	}
	
	public void add(byte b)
	{
		this.ensureCapacityInternal(size + 1);
		this.elementDate[size++] = b;
	}
	
	public void add(byte[] bArr, int offset, int length)
	{
		this.ensureCapacityInternal(size + length);
		System.arraycopy(bArr, offset, this.elementDate, this.size, length);
		this.size += length;
	}
	
	public void ensureCapacityInternal(int minCapacity)
	{
		if(minCapacity > this.elementDate.length)
		{
			int newCapacity = this.elementDate.length + (this.elementDate.length >> 1);
			if(newCapacity < minCapacity)
				newCapacity = minCapacity;
			byte[] newElementDate = new byte[newCapacity];
			System.arraycopy(this.elementDate, 0, newElementDate, 0, this.elementDate.length);
			this.elementDate = newElementDate;
		}
	}
	
	public byte[] toArray()
	{
		byte[] arr = new byte[this.size];
		System.arraycopy(this.elementDate, 0, arr, 0, this.size);
		return arr;
	}
	
	public final int size()
	{
		return size;
	}
}
