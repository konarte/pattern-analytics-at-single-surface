package edu.mgupi.pass.face.gui;

import edu.mgupi.pass.util.Utils;

/**
 * Simple interface, define condition for keep execution.
 * 
 * @author raidan
 * 
 * @see WorkSet
 * @see Utils
 */
public interface WaitCondition {

	/**
	 * Define, need we wait more
	 * 
	 * @return true if we need wait, false if all work is done
	 */
	boolean keepWorking();
}
