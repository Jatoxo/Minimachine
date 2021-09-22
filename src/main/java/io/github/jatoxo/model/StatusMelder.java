package io.github.jatoxo.model;

public interface StatusMelder {
	void displayError(String message, int position);

	void displayStatusMessage(String message);
}
