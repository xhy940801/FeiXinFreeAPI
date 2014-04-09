package com.xiao.Feixin.FreeAPI.Operater;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveJsonDecoder
{
	private JSONObject jsonObj;
	
	public ReceiveJsonDecoder(String json) throws JSONException
	{
		jsonObj = new JSONObject(json);
	}
	
	public int getStatus()
	{
		if(jsonObj == null)
			return 10002;
		try
		{
			Object o = jsonObj.get("rc");
			if(o instanceof Integer)
				return (int)o;
			else
				return Integer.parseInt(o.toString());
		}
		catch (JSONException | NumberFormatException e)
		{
			return 10002;
		}
	}
	
	public List<Message> getMessages()
	{
		try
		{
			Object j = jsonObj.get("rv");
			if(j instanceof JSONArray)
			{
				JSONArray jsa = (JSONArray) j;
				ArrayList<Message> messages = new ArrayList<Message>();
				for(int i=0;i<jsa.length();++i)
				{
					JSONObject jso = jsa.getJSONObject(i);
					Object o = jso.get("DataType");
					int dataType;
					if(o instanceof Integer)
						dataType =  (int)o;
					else
						dataType =  Integer.parseInt(o.toString());
					
					if(dataType != 3)
						continue;
					
					o = jso.get("Data");
					if(o instanceof JSONObject)
					{
						messages.add(new Message((JSONObject) o));
					}
				}
				
				return messages;
			}
			else
				return null;
		}
		catch (JSONException | NumberFormatException e)
		{
			return null;
		}
	}
}
