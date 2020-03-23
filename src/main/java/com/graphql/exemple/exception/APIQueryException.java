package com.graphql.exemple.exception;

public class APIQueryException extends RuntimeException{
	
	private static final long serialVersionUID = 4425587059232906239L;

	public APIQueryException(String message) {
		super(message);
	}
	
}
