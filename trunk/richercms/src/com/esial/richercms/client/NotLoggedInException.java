package com.esial.richercms.client;

public class NotLoggedInException extends Exception {

	public NotLoggedInException() {
	    super();
	  }

	  public NotLoggedInException(String message) {
	    super(message);
	  }
}
