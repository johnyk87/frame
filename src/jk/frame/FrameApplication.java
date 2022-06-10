package jk.frame;

import jk.frame.properties.PropertiesMap;

public final class FrameApplication {
	
	private static FrameApplication instance;
	
	public static synchronized FrameApplication getInstance()
	{
		if(instance == null)
			instance = new FrameApplication();
		
		return instance;
	}
	
	/****************************************************************************/
	
	private PropertiesMap properties;
	private Dispatcher dispatcher;
	
	private FrameApplication()
	{
		this.properties = new PropertiesMap(null);
		this.dispatcher = new Dispatcher();
	}
	
	void setProperties(PropertiesMap properties)
	{
		this.properties = properties;
	}
	
	void setDispatcher(Dispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}
	
	Dispatcher getDispatcher()
	{
		return dispatcher;
	}
	
	//-------------------------------------------------------------------------
	//API
	
	public boolean isLoaded()
	{
		return dispatcher.isLoaded();
	}
	
	public boolean isEnabled()
	{
		return dispatcher.isEnabled();
	}
	
	public void enable()
	{
		dispatcher.enable();
	}
	
	public void disable()
	{
		dispatcher.disable();
	}
	
	public String getProperty(String name)
	{
		return properties.getProperty(name);
	}
	
	/** OLD
	
	final void trigger(FrameEvent event) throws HandlerException, CyclicDispatchException
	{
		getDispatcher().dispatch(event);
	}

	@SuppressWarnings("unchecked")
	public <T extends FrameEvent> T trigger(String event_def, Object...args) throws HandlerException, CyclicDispatchException, DispatchException
	{
		Class<T> type = (Class<T>) events.get(event_def);
		if(type == null)
			throw new NoSuchEventException("No event defined with name \"" + event_def + "\"");
		
		FrameEvent event = null;
		try
		{
			event = FrameEvent.newEvent(type, args);
		}
		catch(Exception e)
		{
			throw new DispatchException(e);
		}
		
		trigger(event);
		
		return (T) event;
	}
	*/
}
