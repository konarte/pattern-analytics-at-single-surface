package edu.mgupi.pass.modules;

public class ModuleNotFoundException extends ModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2449029171108350041L;

	public ModuleNotFoundException(String message) {
		super(message);
	}

	public ModuleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
