package jk.frame.exceptions;


public class InitializationException extends LoadingException {
	
	private static final long serialVersionUID = 817319400248635525L;

	public InitializationException() {
		super();
	}

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}
}
