package jk.frame.utils.classes;


public final class ClassLoader {

	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> load(String className, Class<T> superclass) throws ClassNotFoundException
	{
		return (Class<? extends T>) Class.forName(className);
	}
}
