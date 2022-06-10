package jk.frame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jk.frame.events.ExceptionEvent;
import jk.frame.events.frame.FrameDisabledEvent;
import jk.frame.events.frame.FrameEnabledEvent;
import jk.frame.exceptions.CyclicDispatchException;
import jk.frame.exceptions.HandlerException;
import jk.frame.utils.classes.ClassComparator;
import jk.frame.utils.tree.Tree;

@SuppressWarnings("rawtypes")
final class Dispatcher {
	
	private Map<Thread, List<Class<? extends FrameEvent>>> active;	
	private Map<Class<? extends FrameEvent>, List<FrameModule>> mappings;
	private Map<Class<? extends FrameEvent>, HandlerPipe> pipes;
	private Tree<Class> classTree;
	private boolean loaded;
	private boolean enabled;
	
	Dispatcher()
	{
		this.active = new HashMap<Thread, List<Class<? extends FrameEvent>>>();
		this.mappings = new HashMap<Class<? extends FrameEvent>, List<FrameModule>>();
		this.pipes = new HashMap<Class<? extends FrameEvent>, HandlerPipe>();
		this.classTree = new Tree<Class>(FrameEvent.class, new ClassComparator());
		initPipes();
		this.loaded = false;
		this.enabled = false;
	}
	
	Dispatcher(Map<Class<? extends FrameEvent>, List<FrameModule>> mappings)
	{
		this.active = new HashMap<Thread, List<Class<? extends FrameEvent>>>();
		this.mappings = (mappings == null) ? new HashMap<Class<? extends FrameEvent>, List<FrameModule>>() : mappings;
		this.pipes = new HashMap<Class<? extends FrameEvent>, HandlerPipe>();
		this.classTree = new Tree<Class>(FrameEvent.class, new ClassComparator());
		initPipes();
		this.loaded = true;
		this.enabled = false;
	}
	
	boolean isEnabled()
	{
		return enabled;
	}
	
	boolean isLoaded()
	{
		return loaded;
	}
	
	@SuppressWarnings("finally")
	void enable()
	{
		if(enabled)
			return;
				
		try
		{
			enabled = true;			
			new FrameEnabledEvent().trigger();
		}
		catch(Exception e)
		{
			new ExceptionEvent(e, true).trigger();
		}
		finally
		{
			return;
		}
	}
	
	@SuppressWarnings("finally")
	void disable()
	{
		if(!enabled)
			return;
		
		try
		{
			new FrameDisabledEvent().trigger();
		}
		catch(Exception e)
		{
			new ExceptionEvent(e, true).trigger();
		}
		finally
		{
			enabled = false;
			return;
		}
	}
	
	void dispatch(FrameEvent event) throws HandlerException, CyclicDispatchException
	{
		try
		{
			if(!enabled) return;
			
			register(event.getClass());
			updatePipes(event.getClass());
			
			HandlerPipe handler = pipes.get(event.getClass());
			if(handler != null)
				handler.execute(event);
		}
		catch(HandlerException e)
		{
			throw e;
		}
		catch(CyclicDispatchException e)
		{
			throw e;
		}
		finally
		{
			unregister(event.getClass());
		}
	}
	
	private void initPipes()
	{
		Iterator<Class<? extends FrameEvent>> it = mappings.keySet().iterator();
		while(it.hasNext())
			classTree.insert(it.next());
	}
	
	private void updatePipes(Class<? extends FrameEvent> eventClass) throws HandlerException
	{			
		if(pipes.containsKey(eventClass))
			return;
		
		try
		{
			List<EventHandler> handlers = new LinkedList<EventHandler>();
			
			classTree.insert(eventClass);
			List<Class> hierarchy = classTree.getOrder(eventClass);
			for(int i=0; i<hierarchy.size(); i++)
			{
				Class current = hierarchy.get(i);
				
				List<FrameModule> modules = mappings.get(current);
				if(modules == null)
					continue;
				
				for(int j=0; j<modules.size(); j++)
				{
					//System.out.println("Adding handler for event [" + eventClass.getName() + "] on module [" + modules.get(j).getClass().getName() + "]");
					handlers.add(new EventHandler(modules.get(j), eventClass));
				}
			}
			pipes.put(eventClass, new HandlerPipe(handlers));
		}
		catch(NoSuchMethodException e)
		{
			throw new HandlerException(e);
		}
	}
	
	private void register(Class<? extends FrameEvent> clazz) throws CyclicDispatchException
	{
		List<Class<? extends FrameEvent>> events = active.get(Thread.currentThread());
		if(events != null && events.contains(clazz))
		{
			active.remove(Thread.currentThread());
			throw new CyclicDispatchException("The event \"" + clazz.getName() + "\" is cyclic.");
		}
		
		if(events == null)
		{
			events = new LinkedList<Class<? extends FrameEvent>>();
			active.put(Thread.currentThread(), events);
		}
		
		events.add(clazz);
	}
	
	private void unregister(Class<? extends FrameEvent> clazz)
	{
		List<Class<? extends FrameEvent>> events = active.get(Thread.currentThread());
		if(events != null)
		{
			events.remove(clazz);
			if(events.size() == 0)
				active.remove(Thread.currentThread());
		}		
	}
}
