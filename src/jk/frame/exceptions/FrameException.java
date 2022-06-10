package jk.frame.exceptions;

public class FrameException extends Exception {
	
	private static final long serialVersionUID = -713634701547887846L;
		
	public FrameException() {
		super();
	}

	public FrameException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameException(String message) {
		super(message);
	}

	public FrameException(Throwable cause) {
		super(cause);
	}
}
