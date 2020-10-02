package com.revature.exceptions;

/**
 * Thrown to signify that authorization has not been given to the user to access a code block.
 */
public class AuthorizationException extends AmealgoException {

	/**
	 * Creates the exception and prints
	 * "This role is not allowed to perform this action."
	 * to the screen.
	 */
	public AuthorizationException(){
		super("This role is not allowed to perform this action.");
	}

	/**
	 * Creates the exception and prints message "msg" to the screen.
	 * @param msg the String message to print to the screen.
	 */
	public AuthorizationException(String msg) {
		super(msg);
	}

	/**
	 * creates this exception and prints a message "msg"
	 * to the screen.
	 * @param msg the String message to pring to the screen.
	 * @param cause a throwable to pass to this exception's parent.
	 */
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * creates this exception and prints a message "msg"
	 * to the screen.
	 * @param cause a throwable to pass to this exception's parent.
	 */
	public AuthorizationException(Throwable cause) {
		super(cause);
	}
}
