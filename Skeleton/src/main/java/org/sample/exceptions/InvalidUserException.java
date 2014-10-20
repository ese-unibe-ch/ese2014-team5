package org.sample.exceptions;

public class InvalidUserException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8838495548210299908L;

	public InvalidUserException(String s) {
        super(s);
    }
}
