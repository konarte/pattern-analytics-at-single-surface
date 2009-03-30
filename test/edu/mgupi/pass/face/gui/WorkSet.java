package edu.mgupi.pass.face.gui;

import edu.mgupi.pass.util.Utils;

/**
 * Simple interface for define work
 * 
 * @author raidan
 * 
 * @see WaitCondition
 * @see Utils
 */
public interface WorkSet {

	/**
	 * Implementation of method provide work we made and wait
	 * 
	 * @throws Exception
	 */
	void workImpl() throws Exception;
}
