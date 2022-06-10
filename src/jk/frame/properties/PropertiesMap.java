package jk.frame.properties;

import java.util.HashMap;
import java.util.Map;

public final class PropertiesMap {
	
	private Map<String, String> map;
	
	public PropertiesMap(Map<String, String> map)
	{
		this.map = (map == null) ? new HashMap<String, String>() : map;
	}
	
	/**************************************************************/
	//API
	
	public String getProperty(String name)
	{
		return map.get(name);
	}
}
