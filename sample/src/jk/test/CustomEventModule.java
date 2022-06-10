package jk.test;

import jk.frame.FrameModule;
import jk.frame.exceptions.FrameException;
import jk.frame.exceptions.InitializationException;

public class CustomEventModule extends FrameModule {
	
	public void init() throws InitializationException
	{
		System.out.println("[CustomEvent] CustomEventModule is now active.");
	}
	
	public void handle(CustomEvent event) throws FrameException
	{
		System.out.println("[CustomEvent] Caught custom event from \"" + event.getFrom() + "\" with message: " + event.getMessage());
	}
}