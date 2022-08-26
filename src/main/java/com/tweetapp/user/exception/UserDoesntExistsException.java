package com.tweetapp.user.exception;

@SuppressWarnings("serial")
public class UserDoesntExistsException extends RuntimeException {
	public UserDoesntExistsException(String message) {
		super(message);
	}
}
