/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)ModuleIntegrityException.java 1.0 30.03.2009
 */

package edu.mgupi.pass.modules;

public class ModuleIntegrityException extends ModuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModuleIntegrityException(Throwable integrityExceptiom, Throwable cause) {
		super("Module integrity exception: " + integrityExceptiom.toString(), cause);
	}

}
