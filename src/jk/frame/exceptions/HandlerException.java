package jk.frame.exceptions;


public class HandlerException extends DispatchException {
	
	private static final long serialVersionUID = 817319400248635525L;

	public HandlerException() {
		super();
	}

	public HandlerException(String message, Throwable cause) {
		super(message, cause);
	}

	public HandlerException(String message) {
		super(message);
	}

	public HandlerException(Throwable cause) {
		super(cause);
	}
}
