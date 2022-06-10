package jk.frame.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jk.frame.FrameEvent;
import jk.frame.exceptions.FrameException;

public class MappedDataEvent<P, R> extends FrameEvent {
	
	private P params;
	private Map<String, R> results;
	
	public MappedDataEvent(P parameters)
	{
		this.params = parameters;
		this.results = new HashMap<String, R>();
	}
	
	public P getParameters()
	{
		return params;
	}

	public void addResult(String key, R result) throws FrameException
	{
		results.put(key, result);
	}
	
	public R getResult(String actor)
	{
		return results.get(actor);
	}
	
	public Iterator<String> actorIterator()
	{
		return results.keySet().iterator(); 
	}
	
	public Iterator<R> resultIterator()
	{
		return results.values().iterator(); 
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + ": " + params;
	}
}
