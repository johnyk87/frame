package jk.frame.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jk.frame.config.xsd.ModuleProperties;
import jk.frame.config.xsd.Properties;
import jk.frame.config.xsd.Property;

public final class PropertiesParser {
	
	public static Map<String, String> parseGlobal(Properties config)
	{
		if(config == null || config.getGlobal() == null)
			return new HashMap<String, String>();
		
		return parseList(config.getGlobal().getProperty());
	}
	
	public static Map<String, Map<String, String>> parseModules(Properties config)
	{
		Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
		
		if(config == null || config.getModule() == null)
			return properties;
		
		List<ModuleProperties> list = config.getModule();
		for(int i=0; i<list.size(); i++)
		{
			ModuleProperties module = list.get(i);
			properties.put(module.getClassid(), parseList(module.getProperty()));
		}
		return properties;
	}
	
	private static Map<String, String> parseList(List<Property> list)
	{
		Map<String, String> properties = new HashMap<String, String>();
		
		if(list == null)
			return properties;
		
		for(int i=0; i<list.size(); i++)
		{
			Property property = list.get(i);
			if(property.getName() != null)
				properties.put(property.getName(), property.getValue());
		}
		return properties;
	}
}
