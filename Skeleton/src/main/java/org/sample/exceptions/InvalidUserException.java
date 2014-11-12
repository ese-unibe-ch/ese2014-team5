package org.sample.exceptions;

public class InvalidUserException extends RuntimeException {

    /**
	 * Exception when user is entered incorrect by the user
	 */
	private static final long serialVersionUID = 8838495548210299908L;

	public InvalidUserException(String s) {
        super(s);
    }
}
