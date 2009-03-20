package edu.mgupi.pass.util;

public interface IProgress {
	void startProgress(int max);

	void setProgress(int value);

	void stopProgress();

	void printMessage(String message);

	void clearMessage();
}
