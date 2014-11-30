package org.sample.exceptions;

public class InvalidDateParseException extends Exception {

	/**
	 * Exception when date couldn't be parsed
	 */
	private static final long serialVersionUID = -7732109008568440705L;

	public InvalidDateParseException(String s) {
        super(s);
    }
}
