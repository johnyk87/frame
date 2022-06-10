package jk.frame;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import jk.frame.exceptions.HandlerException;

final class HandlerPipe {
	
	private List<EventHandler> handlers;
	
	HandlerPipe(List<EventHandler> handlers)
	{
		this.handlers = (handlers == null) ? new LinkedList<EventHandler>() : handlers;
	}
	
	void execute(FrameEvent event) throws HandlerException
	{
		try
		{
			for(int i=0; i<handlers.size(); i++)
			{
				handlers.get(i).handle(event);
			}
		}
		catch(InvocationTargetException e)
		{
			throw new HandlerException(e.getCause());
		}
		catch(Exception e)
		{
			throw new HandlerException(e);
		}
	}
}
