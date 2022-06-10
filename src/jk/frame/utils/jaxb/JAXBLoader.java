package jk.frame.utils.jaxb;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import jk.frame.exceptions.LoadingException;

public final class JAXBLoader<T> {
	
	private JAXBContext jaxb;
	
	public JAXBLoader(String context) throws LoadingException
	{
		try
		{
			this.jaxb = JAXBContext.newInstance(context);
		}
		catch(Exception e)
		{
			throw new LoadingException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T load(File file) throws LoadingException
	{
		try
		{
			return ((JAXBElement<T>) jaxb.createUnmarshaller().unmarshal(file)).getValue();
		}
		catch(Exception e)
		{
			throw new LoadingException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T load(URL url) throws LoadingException
	{
		try
		{
			return ((JAXBElement<T>) jaxb.createUnmarshaller().unmarshal(url)).getValue();
		}
		catch(Exception e)
		{
			throw new LoadingException(e);
		}
	}
}
