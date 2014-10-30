package org.sample.exceptions;

public class InvalidSearchException extends RuntimeException {

	/**
	 * When trying to save a search this exception should be thrown.
	 */
	//private static final long serialVersionUID = -7732109008568440705L;

	public InvalidSearchException(String s) {
        super(s);
    }
}
