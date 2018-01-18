package com.radoslav.services.email.exception;

import com.sun.jersey.api.MessageException;

public class UnsupportedDataException extends MessageException {

	private static final long serialVersionUID = 5820733042732517919L;

	public UnsupportedDataException() {
		super("Unsupported operation : Unrecognizable entity");
		// TODO Auto-generated constructor stub
	}

}
