package memcache;

public class CacheException extends RuntimeException {
	
	private static final long serialVersionUID = 7298453228772589863L;

	public CacheException() {
		super();
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}
}
