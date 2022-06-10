package jk.test;

import jk.frame.events.MessageEvent;

public class CustomEvent extends MessageEvent {
	
	public CustomEvent(String from, String message)
	{
		super(from, message);
	}
}