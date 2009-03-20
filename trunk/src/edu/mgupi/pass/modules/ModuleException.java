package edu.mgupi.pass.modules;

public class ModuleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2042298775888321564L;

	public ModuleException(String message) {
		super(message);
	}

	public ModuleException(String message, Throwable cause) {
		super(message, cause);
	}
}
