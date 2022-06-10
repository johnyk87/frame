package jk.frame;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jk.frame.config.ClassesParser;
import jk.frame.config.MappingsParser;
import jk.frame.config.PropertiesParser;
import jk.frame.config.xsd.ApplicationConfig;
import jk.frame.exceptions.LoadingException;
import jk.frame.utils.jaxb.JAXBLoader;
import jk.frame.properties.PropertiesMap;

public final class FrameLoader {

	private static final long serialVersionUID = -6137129954518696578L;
	
	private static final String CONTEXT = "jk.frame.config.xsd";
	private static final String DEFAULT_FILE = "./frame.xml";
	
	private File file;
	private URL url;
	
	public FrameLoader()
	{
		this.file = new File(DEFAULT_FILE);
		this.url = null;
	}
	
	public FrameLoader(File file)
	{
		this.file = file;
		this.url = null;
	}
	
	public FrameLoader(URL url)
	{
		this.file = null;
		this.url = url;
	}
	
	public void load() throws LoadingException
	{
		try
		{
			if(url == null)
				load(new JAXBLoader<ApplicationConfig>(CONTEXT).load(file));
			else
				load(new JAXBLoader<ApplicationConfig>(CONTEXT).load(url));				
		}
		catch(Exception e)
		{
			throw new LoadingException(e);
		}		
	}
	
	private void load(ApplicationConfig appConfig) throws Exception
	{
		//Properties

		Map<String, String> globalProperties = PropertiesParser.parseGlobal(appConfig.getProperties());
		//print("global properties", globalProperties);	
		Map<String, Map<String, String>> moduleProperties = PropertiesParser.parseModules(appConfig.getProperties());
		//print("module properties", moduleProperties);
					
		// Classes

		Map<String, Class<? extends FrameEvent>> eventClasses = ClassesParser.parseEvents(appConfig.getClasses());	
		//print("event classes", eventClasses);
		Map<String, Class<? extends FrameModule>> moduleClasses = ClassesParser.parseModules(appConfig.getClasses());	
		//print("module classes", moduleClasses);

		// Modules
		
		Map<String, FrameModule> modules = initModules(moduleClasses, moduleProperties);
		//print("modules", modules);
		
		// Mappings
		
		Map<Class<? extends FrameEvent>, List<FrameModule>> eventMappings = MappingsParser.parseEvents(appConfig.getMappings(), eventClasses, modules);
		//print("event mappings", eventMappings);
		validateMappings(eventMappings);
		
		//Dispatcher
		
		Dispatcher dispatcher = new Dispatcher(eventMappings);
		
		//Apply changes to FrameApplication
		
		FrameApplication.getInstance().setProperties(new PropertiesMap(globalProperties));
		FrameApplication.getInstance().setDispatcher(dispatcher);
		dispatcher.enable();
	}
	
	private Map<String, FrameModule> initModules(Map<String, Class<? extends FrameModule>> moduleClasses, Map<String, Map<String, String>> moduleProperties) throws Exception
	{
		Map<String, FrameModule> modules = new HashMap<String, FrameModule>();
		
		Iterator<String> it = moduleClasses.keySet().iterator();
		while(it.hasNext())
		{
			String classid = it.next();
			
			Class<? extends FrameModule> modClass = moduleClasses.get(classid);
			PropertiesMap properties = new PropertiesMap(moduleProperties.get(classid));
			
			FrameModule module = modClass.getConstructor().newInstance();
			module.setProperties(properties);
			module.init();
			modules.put(classid, module);
		}
		
		return modules;
	}
	
	private void validateMappings(Map<Class<? extends FrameEvent>, List<FrameModule>> eventMappings) throws NoSuchMethodException
	{
		Iterator<Class<? extends FrameEvent>> it = eventMappings.keySet().iterator();
		while(it.hasNext())
		{
			Class<? extends FrameEvent> eventClass = it.next();
			
			List<FrameModule> modules = eventMappings.get(eventClass);
			for(int i=0; i<modules.size(); i++)
				modules.get(i).getMethod(eventClass);
		}
	}
	
	/**
	 * DEBUG
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private void print(String message, Map map)
	{
		System.out.println("\nSTART " + message.toUpperCase());
		
		Iterator it = map.keySet().iterator();
		while(it.hasNext())
		{
			Object key = it.next();
			Object value = map.get(key);
			if(value instanceof Map)
				print(key.toString(), (Map) value);
			else
				if(value instanceof List)
					print(key.toString(), (List) value);
				else
					System.out.println("Key \"" + key + "\": " + value);
		}
		
		System.out.println("END " + message.toUpperCase() + "\n");
	}
	
	/**
	 * DEBUG
	 */
	@SuppressWarnings("rawtypes")
	private void print(String message, List list)
	{
		System.out.println("\nSTART " + message.toUpperCase());
		
		for(int i=0; i<list.size(); i++)
		{
			System.out.println("Pos. " + i + ": " + list.get(i));
		}
		
		System.out.println("END " + message.toUpperCase() + "\n");
	}
}
