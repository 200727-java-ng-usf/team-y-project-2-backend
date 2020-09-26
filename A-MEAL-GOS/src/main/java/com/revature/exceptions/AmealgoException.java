package com.revature.exceptions;

public class AmealgoException extends RuntimeException {

	public AmealgoException(String msg) {
		super(msg);
	}

	public AmealgoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AmealgoException(Throwable cause) {
		super(cause);
	}
}
