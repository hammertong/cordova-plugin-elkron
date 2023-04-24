package com.elkron.cordova.plugin;

@SuppressWarnings("serial")
public class ElkronApiException extends Exception {
	
	public ElkronApiException() {
		super();
	}
	public ElkronApiException(String message) {
		super(message);
	}
	public ElkronApiException(Throwable t) {
		super(t);
	}

}
