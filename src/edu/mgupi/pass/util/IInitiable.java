package edu.mgupi.pass.util;

/**
 * Interface for any initiable filters/modules. Allow to create init and close
 * methods.
 * 
 * @author raidan
 * 
 */
public interface IInitiable {

	/**
	 * Method will be launched just after instantiating class.
	 */
	public void init();

	/**
	 * Method will be launched after processing.
	 */
	public void close();
}
