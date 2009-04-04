package edu.mgupi.pass.filters;

public class FilterNotFoundException extends FilterException {

	public FilterNotFoundException(String message) {
		super(message);
	}

	public FilterNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
