package jk.test;

import jk.frame.FrameEvent;
import jk.frame.FrameModule;
import jk.frame.events.ExceptionEvent;
import jk.frame.exceptions.FrameException;
import jk.frame.exceptions.InitializationException;

public class EventLoggerModule extends FrameModule {
	
	public void init() throws InitializationException
	{
		System.out.println("[EventLogger] EventLogger is now active.");
	}
	
	public void handle(FrameEvent event) throws FrameException
	{
		System.out.println("[EventLogger] " + event.toString());
	}
	
	public void handle(ExceptionEvent event) throws FrameException
	{
		System.out.println("[EventLogger] Exception stack trace:");
		event.getException().printStackTrace();
	}
}