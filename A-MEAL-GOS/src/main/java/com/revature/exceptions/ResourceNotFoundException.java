package com.revature.exceptions;

/**
 * Thrown when a Resource was trying to be found in the repository and wasn't.
 */
public class ResourceNotFoundException extends AmealgoException{

	/**
	 * Creates this exception and prints the exception to the screen:
	 * No resource found with the provided search criteria.
	 */
	public ResourceNotFoundException(){
		super("No resource found with the provided search criteria.");
	}

	/**
	 * Creates the exception and prints message "msg" to the screen.
	 * @param msg the String message to print to the screen.
	 */
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
