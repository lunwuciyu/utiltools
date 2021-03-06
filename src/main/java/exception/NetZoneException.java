package exception;

public class NetZoneException extends RuntimeException{

	private static final long serialVersionUID = -8114733506054764318L;

	public NetZoneException() {
		super();
	}

	public NetZoneException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetZoneException(String message) {
		super(message);
	}

	public NetZoneException(Throwable cause) {
		super(cause);
	}
}
