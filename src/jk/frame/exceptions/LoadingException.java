package jk.frame.exceptions;

public class LoadingException extends FrameException {

	private static final long serialVersionUID = 4781375117713500393L;

	public LoadingException() {
	}

	public LoadingException(String message) {
		super(message);
	}

	public LoadingException(Throwable cause) {
		super(cause);
	}

	public LoadingException(String message, Throwable cause) {
		super(message, cause);
	}

}
