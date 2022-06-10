package jk.frame.events;

import jk.frame.FrameEvent;

public class ExceptionEvent extends FrameEvent {
	
	private Exception exception;
	private boolean trace;
	
	public ExceptionEvent(Exception exception, boolean trace)
	{
		this.exception = exception;
		this.trace = trace;
	}
	
	public Exception getException()
	{
		return exception;
	}
	
	public boolean trace()
	{
		return trace;
	}

	@Override
	public String toString()
	{
		return exception.toString();
	}
}
