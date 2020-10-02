package com.revature.exceptions;

/**
 * Thrown in the Amealgo web application when child exceptions are inapplicable.
 */
public class AmealgoException extends RuntimeException {

	/**
	 * creates this exception and prints a message "msg"
	 * to the screen.
	 * @param msg the String message to print to the screen.
	 */
	public AmealgoException(String msg) {
		super(msg);
	}

	/**
	 * creates this exception and prints a message "msg"
	 * to the screen.
	 * @param msg the String message to pring to the screen.
	 * @param cause a throwable to pass to this exception's parent.
	 */
	public AmealgoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * creates this exception and prints a message "msg"
	 * to the screen.
	 * @param cause a throwable to pass to this exception's parent.
	 */
	public AmealgoException(Throwable cause) {
		super(cause);
	}
}
