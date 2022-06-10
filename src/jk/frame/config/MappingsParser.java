package jk.frame.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jk.frame.FrameEvent;
import jk.frame.FrameModule;
import jk.frame.config.xsd.HandlerMappings;
import jk.frame.config.xsd.Mappings;
import jk.frame.config.xsd.ModuleMapping;

public class MappingsParser {
	
	public static Map<Class<? extends FrameEvent>, List<FrameModule>> parseEvents(Mappings mappings, Map<String, Class<? extends FrameEvent>> eventClasses, Map<String, FrameModule> modules) throws ClassNotFoundException
	{
		if(mappings == null)
			return new HashMap<Class<? extends FrameEvent>, List<FrameModule>>();

		return parseClassMappings(parseStringMappings(mappings, modules), eventClasses);
	}
	
	private static Map<String, List<FrameModule>> parseStringMappings(Mappings mappingsConfig, Map<String, FrameModule> modules) throws ClassNotFoundException
	{
		Map<String, List<FrameModule>> stringMappings = new HashMap<String, List<FrameModule>>();
		
		List<HandlerMappings> mappings = mappingsConfig.getEvent();
		
		for(int i=0; i<mappings.size(); i++)
		{
			HandlerMappings mapping = mappings.get(i);
			List<ModuleMapping> handlers = mapping.getHandler();
			List<FrameModule> moduleList = new LinkedList<FrameModule>();
			
			for(int j=0; j<handlers.size(); j++)
			{
				String classid = handlers.get(j).getModule();
				FrameModule module = modules.get(classid);
				
				if(module == null)
					throw new ClassNotFoundException("No class defined for id \"" + classid + "\"!");
				
				moduleList.add(module);
			}
			
			stringMappings.put(mapping.getClassid(), moduleList);
		}
		
		return stringMappings;
	}
	
	private static Map<Class<? extends FrameEvent>, List<FrameModule>> parseClassMappings(Map<String, List<FrameModule>> stringMappings, Map<String, Class<? extends FrameEvent>> classes) throws ClassNotFoundException
	{
		Map<Class<? extends FrameEvent>, List<FrameModule>> flatMappings = new HashMap<Class<? extends FrameEvent>, List<FrameModule>>();
		
		Iterator<String> it = classes.keySet().iterator();
		
		while(it.hasNext())
		{
			String classid = it.next();
			Class<? extends FrameEvent> clazz = classes.get(classid);
			
			if(clazz == null)
				throw new ClassNotFoundException("No class defined for id \"" + classid + "\"!");
			
			List<FrameModule> modules = stringMappings.get(classid);
			if(modules == null)
				modules = new LinkedList<FrameModule>();
			
			flatMappings.put(clazz, modules);
		}
		
		return flatMappings;
	}
}
