package edu.mgupi.pass.util;

/**
 * Simple interface, define condition for keep execution.
 * 
 * @author raidan
 * 
 * @see WorkSet
 * @see SwingHelper
 */
public interface WaitCondition {

	/**
	 * Define, need we wait more
	 * 
	 * @return true if we need wait, false if all work is done
	 */
	boolean keepWorking();
}
