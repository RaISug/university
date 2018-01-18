package com.radoslav.services.email.exception;

import com.sun.jersey.api.MessageException;

public class EmptyMessageException extends MessageException {

	private static final long serialVersionUID = 5908164652293394480L;

	public EmptyMessageException() {
		super("Empty body is not permited!");
	}
}
