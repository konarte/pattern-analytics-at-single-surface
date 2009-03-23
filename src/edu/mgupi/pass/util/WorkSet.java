package edu.mgupi.pass.util;

/**
 * Simple interface for define work
 * 
 * @author raidan
 * 
 * @see WaitCondition
 * @see SwingHelper
 */
public interface WorkSet {

	/**
	 * Implementation of method provide work we made and wait
	 * 
	 * @throws Exception
	 */
	void workImpl() throws Exception;
}
