package com.revature.exceptions;

/**
 * Thrown when an update or a save operation on the database fails.
 */
public class ResourcePersistenceException extends AmealgoException{

	public ResourcePersistenceException(){
		super("Resource could not be persisted.");
	}

	public ResourcePersistenceException(String msg) {
		super(msg);
	}

	public ResourcePersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
