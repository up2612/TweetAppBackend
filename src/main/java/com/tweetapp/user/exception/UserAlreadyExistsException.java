package com.tweetapp.user.exception;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends RuntimeException{

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
