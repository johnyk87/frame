package jk.frame.exceptions;

public class DispatchException extends FrameException {

	private static final long serialVersionUID = 4781375117713500393L;

	public DispatchException() {
	}

	public DispatchException(String message) {
		super(message);
	}

	public DispatchException(Throwable cause) {
		super(cause);
	}

	public DispatchException(String message, Throwable cause) {
		super(message, cause);
	}

}
