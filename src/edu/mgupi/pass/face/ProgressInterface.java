package edu.mgupi.pass.face;

public interface ProgressInterface {
	void startProgress(int max);

	void setProgress(int value);

	void stopProgress();

	void printMessage(String message);

	void clearMessage();
}
