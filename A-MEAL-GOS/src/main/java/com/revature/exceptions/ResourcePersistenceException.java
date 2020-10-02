package com.revature.exceptions;

/**
 * Thrown when an update or a save operation on the database fails.
 */
public class ResourcePersistenceException extends AmealgoException{

	/**
	 * Creates the exception and prints
	 * "Resource could not be persisted."
	 * to the screen.
	 */
	public ResourcePersistenceException(){
		super("Resource could not be persisted.");
	}

	/**
	 * Creates the exception and prints message "msg" to the screen.
	 * @param msg the String message to print to the screen.
	 */
	public ResourcePersistenceException(String msg) {
		super(msg);
	}

	/**
	 * creates this exception and prints a message "msg"
	 * to the screen.
	 * @param msg the String message to pring to the screen.
	 * @param cause a throwable to pass to this exception's parent.
	 */
	public ResourcePersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
