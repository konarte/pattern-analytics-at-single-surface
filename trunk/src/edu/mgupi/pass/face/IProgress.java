package edu.mgupi.pass.face;

/**
 * Special interface for all 'faces'. Provide simple progress interfaces for
 * processing modules, when module does not know, with GUI it run or without.
 * 
 * @author raidan
 * 
 */
public interface IProgress {

	/**
	 * Begin new work.
	 * 
	 * @param max
	 *            all progress steps
	 */
	void startProgress(int max);

	/**
	 * Continue execution.
	 * 
	 * @param value
	 *            current step
	 */
	void setProgress(int value);

	/**
	 * Work is over.
	 */
	void stopProgress();

	/**
	 * Show message.
	 * 
	 * @param message
	 */
	void printMessage(String message);

	/**
	 * Clear message.
	 */
	void clearMessage();
}
