package jk.frame;

import jk.frame.exceptions.CyclicDispatchException;
import jk.frame.exceptions.HandlerException;

public abstract class FrameEvent {
	
	public final void trigger() throws HandlerException, CyclicDispatchException
	{
		FrameApplication.getInstance().getDispatcher().dispatch(this);
	}

	@Override
	public String toString()
	{
		return this.getClass().getName();
	}
	
	/** OLD
	
	@SuppressWarnings("unchecked")
	static <T extends FrameEvent> T newEvent(Class<T> type, Object...args) throws Exception
	{
		Constructor<T>[] consts = (Constructor<T>[]) type.getConstructors();
		Constructor<T> cons = null;
		for(int i=0; i<consts.length; i++)
		{
			Constructor<T> current = consts[i];
			if(current.getParameterTypes().length != args.length)
				continue;
			
			for(int j=0; j<args.length; j++)
			{
				if(!current.getParameterTypes()[j].isAssignableFrom(args[i].getClass()))
					continue;
				if(j == args.length - 1)
					cons = current;
			}
			
			if(cons != null)
				break;
		}
		if(cons == null)
			throw new NoSuchMethodException(type.getName() + "()");
		
		T ev = cons.newInstance(args);
		
		return ev;
	}
	
	 */
}
