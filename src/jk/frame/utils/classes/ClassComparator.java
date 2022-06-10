package jk.frame.utils.classes;

import java.util.Comparator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClassComparator implements Comparator<Class>{

	public int compare(Class c1, Class c2)
	{
		if(c1.equals(c2)) return 0;
		if(c1.isAssignableFrom(c2)) return -1;
		if(c2.isAssignableFrom(c1)) return 1;
		return 0;
	}

}
