package edu.mgupi.pass.util;

/**
 * Flexible interface for any windows/components must be refreshable.
 * 
 * @author raidan
 * 
 */
public interface IRefreshable {

	/**
	 * Do some action.
	 * 
	 * @return number of changed rows, for example
	 */
	int refresh();
}
