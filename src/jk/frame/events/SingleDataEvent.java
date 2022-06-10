package jk.frame.events;

import jk.frame.FrameEvent;

public class SingleDataEvent<R> extends FrameEvent {
	
	private R data;
	
	public SingleDataEvent(R data)
	{
		this.data = data;
	}
	
	public R getData()
	{
		return data;
	}

	public void setData(R data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + ": Data [" + data + "]";
	}
}
