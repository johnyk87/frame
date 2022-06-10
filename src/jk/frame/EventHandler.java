package jk.frame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class EventHandler {
	
	private FrameModule module;
	private Method method;
	private Class<? extends FrameEvent> eventClass;

	EventHandler(FrameModule module, Class<? extends FrameEvent> eventClass) throws NoSuchMethodException
	{
		this.module = module;
		this.eventClass = eventClass;
		this.method = module.getMethod(eventClass);
	}
	
	void handle(FrameEvent event) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		method.invoke(module, event);
	}
	
	public boolean equals(EventHandler handler)
	{
		return this.module.equals(handler.module) && this.eventClass.equals(handler.eventClass);
	}
}
