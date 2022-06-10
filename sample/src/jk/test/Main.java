package jk.test;

import jk.frame.FrameLoader;
import jk.frame.events.ExceptionEvent;
import jk.frame.exceptions.HandlerException;

public class Main {
	
	public static void main(String[] args)
	{
		try
		{
			new FrameLoader().load();
			new CustomEvent(Main.class.getName(), "Test").trigger();
			new ExceptionEvent(new HandlerException("exception"), true).trigger();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}