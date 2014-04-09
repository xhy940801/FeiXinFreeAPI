package com.xiao.Feixin.FreeAPI.Controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.xiao.Feixin.FreeAPI.Model.MessageModel;
import com.xiao.Feixin.FreeAPI.Operater.Message;

public class MsgQueue implements Queue<Message>
{
	static private final int capacity = 50;
	
	private Queue<Message> msgQueue = new LinkedList<Message>();
	public MsgQueue()
	{
		
	}

	@Override
	public int size()
	{
		int msgSize = msgQueue.size();
		if(msgSize >= capacity)
			return msgSize + MessageModel.getMsgCount();
		else
			return msgSize;
	}

	@Override
	public boolean isEmpty()
	{
		return msgQueue.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		if(o instanceof Message)
		{
			Message msg = (Message) o;
			if(msgQueue.size() >= capacity)
			{
				if(msgQueue.contains(msgQueue))
					return true;
				else
					return MessageModel.isExist(msg);
			}
			else
				return msgQueue.contains(msg);
		}
		else
			return false;
	}

	@Override
	public Iterator<Message> iterator()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray()
	{
		Object[] os = msgQueue.toArray();
		msgQueue.clear();
		Message[] msgs = MessageModel.poll(capacity);
		for(Message msg : msgs)
			msgQueue.offer(msg);
		return os;
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Message> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear()
	{
		msgQueue.clear();
		MessageModel.clear();
	}

	@Override
	public boolean add(Message e)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean offer(Message e)
	{
		if(msgQueue.size() < capacity)
			return msgQueue.offer(e);
		else
			return MessageModel.saveMessage(e);
	}

	@Override
	public Message remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Message poll()
	{
		if(msgQueue.size() < capacity)
		{
			Message msg = msgQueue.poll();
			return msg;
		}
		else
		{
			Message msg = msgQueue.poll();
			Message[] msgs = MessageModel.poll(1);
			for(Message m: msgs)
				msgQueue.offer(m);
			return msg;
		}
	}

	@Override
	public Message element()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Message peek()
	{
		throw new UnsupportedOperationException();
	}

}
