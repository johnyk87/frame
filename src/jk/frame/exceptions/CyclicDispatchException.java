package jk.frame.exceptions;


public class CyclicDispatchException extends DispatchException {
	
	private static final long serialVersionUID = 817319400248635525L;

	public CyclicDispatchException() {
		super();
	}

	public CyclicDispatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public CyclicDispatchException(String message) {
		super(message);
	}

	public CyclicDispatchException(Throwable cause) {
		super(cause);
	}
}
