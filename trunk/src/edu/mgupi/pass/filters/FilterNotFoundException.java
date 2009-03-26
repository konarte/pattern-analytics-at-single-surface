package edu.mgupi.pass.filters;

public class FilterNotFoundException extends FilterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482348509339148325L;

	public FilterNotFoundException(String message) {
		super(message);
	}

	public FilterNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
