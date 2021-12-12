package com.zonesoft.addressbook.exceptions;

public class AddressBookException extends RuntimeException {
	private static final long serialVersionUID = 3365111151656618159L;
	
	public AddressBookException() {
		    super();
	}

	public AddressBookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		    super(message, cause, enableSuppression, writableStackTrace);
	}

	public AddressBookException(String message, Throwable cause) {
		    super(message, cause);
	}

	public AddressBookException(String message) {
		    super(message);
	}

	public AddressBookException(Throwable cause) {
		    super(cause);
	}

}
