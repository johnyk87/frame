package jk.frame;

import java.lang.reflect.Method;

import jk.frame.exceptions.InitializationException;
import jk.frame.properties.PropertiesMap;

public abstract class FrameModule {
	
	private PropertiesMap properties;
	
	protected FrameModule()
	{		
		this.properties =new PropertiesMap(null);
	}
	
	void setProperties(PropertiesMap properties)
	{
		if(properties != null)
			this.properties = properties;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Method getMethod(Class<? extends FrameEvent> eventClass) throws NoSuchMethodException
	{
		Method method = null;
		Class mostSpecific = null;
		
		Method[] methods = this.getClass().getMethods();
		for(int i=0; i<methods.length; i++)
		{
			if(methods[i].getName().equals("handle") && methods[i].getParameterTypes().length == 1 && methods[i].getParameterTypes()[0].isAssignableFrom(eventClass))
			{
				Class temp = methods[i].getParameterTypes()[0];
				if(mostSpecific == null || mostSpecific.isAssignableFrom(temp))
				{
					mostSpecific = temp;
					method = methods[i];
				}
			}
		}
			
		if(method == null)
			throw new NoSuchMethodException(this.getClass().getName() + ".handle(" + eventClass.getName() + ")");
		
		return method;
	}

	public final String getProperty(String name)
	{
		return properties.getProperty(name);
	}

	public abstract void init() throws InitializationException;
	
}
