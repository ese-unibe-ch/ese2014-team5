package org.sample.exceptions;

public class InvalidAdException extends RuntimeException {

	/**
	 * Exception when ad is entered incorrect by the user
	 */
	private static final long serialVersionUID = -7732109008568440705L;

	public InvalidAdException(String s) {
        super(s);
    }
}
