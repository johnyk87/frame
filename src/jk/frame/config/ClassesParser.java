package jk.frame.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jk.frame.FrameEvent;
import jk.frame.FrameModule;
import jk.frame.config.xsd.ClassDefinition;
import jk.frame.config.xsd.ClassDefinitions;
import jk.frame.config.xsd.Classes;
import jk.frame.utils.classes.ClassLoader;

public class ClassesParser {
	
	public static Map<String, Class<? extends FrameEvent>> parseEvents(Classes classes) throws ClassNotFoundException
	{		
		if(classes == null || classes.getEvents() == null)
			return new HashMap<String, Class<? extends FrameEvent>>();
		
		return parse(classes.getEvents(), FrameEvent.class);
	}
	
	public static Map<String, Class<? extends FrameModule>> parseModules(Classes classes) throws ClassNotFoundException
	{		
		if(classes == null || classes.getModules() == null)
			return new HashMap<String, Class<? extends FrameModule>>();
		
		return parse(classes.getModules(), FrameModule.class);
	}
	
	private static <T> Map<String, Class<? extends T>> parse(ClassDefinitions classDefs, Class<T> superclass) throws ClassNotFoundException
	{
		Map<String, Class<? extends T>> classes = new HashMap<String, Class<? extends T>>();
		
		List<ClassDefinition> classList = classDefs.getClassdef();
		
		for(int i=0; i<classList.size(); i++)
		{
			ClassDefinition classDef = classList.get(i);
			String id = classDef.getId();
			String className = classDef.getName();
			
			if(id != null && className != null)
			{
				classes.put(id, ClassLoader.load(className, superclass));
			}
		}
		
		return classes;
	}
}
