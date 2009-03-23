package edu.mgupi.pass.filters;

public class IllegalParameterValueException extends FilterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1737426526014874057L;

	public IllegalParameterValueException(String message) {
		super(message);
	}

	public IllegalParameterValueException(String message, Throwable cause) {
		super(message, cause);
	}
}
