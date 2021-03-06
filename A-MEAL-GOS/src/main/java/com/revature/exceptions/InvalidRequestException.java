package com.revature.exceptions;

/**
 * Thrown when improper requests are submitted,
 * i.e., null values where non-null values are essential.
 */
public class InvalidRequestException extends AmealgoException{

	public InvalidRequestException(){
		super("An Invalid request was made.");
	}
	/**
	 * Creates this exception and prints s to the screen.
	 * @param s
	 */
	public InvalidRequestException(String s){
		super(s);
	}
}
