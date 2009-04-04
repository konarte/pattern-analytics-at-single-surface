package edu.mgupi.pass.filters;

public class IllegalParameterValueException extends FilterException {

	public IllegalParameterValueException(String message) {
		super(message);
	}

	public IllegalParameterValueException(String message, Throwable cause) {
		super(message, cause);
	}
}
