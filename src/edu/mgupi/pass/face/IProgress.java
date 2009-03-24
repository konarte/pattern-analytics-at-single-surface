package edu.mgupi.pass.face;

/**
 * Special interface for all 'faces'. Provide simple progress interfaces for
 * processing modules, when module does not know, with GUI it run or without.
 * 
 * @author raidan
 * 
 */
public interface IProgress {
	void startProgress(int max);

	void setProgress(int value);

	void stopProgress();

	void printMessage(String message);

	void clearMessage();
}
