package jk.frame.events;

import jk.frame.FrameEvent;

public class MessageEvent extends FrameEvent {
	
	private String from;
	private String message;
	
	public MessageEvent(String from, String message)
	{
		this.from = from;
		this.message = message;
	}

	public String getFrom()
	{
		return from;
	}

	public String getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + ": message [" + message + "] from [" + from + "]";
	}
}
