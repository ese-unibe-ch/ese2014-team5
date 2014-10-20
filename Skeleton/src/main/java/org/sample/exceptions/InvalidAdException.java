package org.sample.exceptions;

public class InvalidAdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7732109008568440705L;

	public InvalidAdException(String s) {
        super(s);
    }
}
