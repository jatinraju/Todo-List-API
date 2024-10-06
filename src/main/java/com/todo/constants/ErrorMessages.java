package com.todo.constants;

import lombok.Getter;

@Getter
public class ErrorMessages {

	private ErrorMessages() {
		super();
	}

	// Error Response JSON Format
	public static final String STATUS = "status";
	public static final String ERROR = "error";
	public static final String ERRORS = "errors";
	public static final String MESSAGE = "message";

	// User Messages
	public static final String USERNAME_MANDATORY = "Username is mandatory";
	public static final String EMAIL_MANDATORY = "Email is mandatory";
	public static final String EMAIL_INVALID_FORMAT = "Invalid email format";
	public static final String PASSWORD_MANDATORY = "Password is mandatory";
	public static final String PASSWORD_MIN_LENGTH = "Password must be at least 8 characters";

	// TO DO Messages
	public static final String TITLE_MANDATORY = "title is mandatory";
	public static final String DESCRIPTION_MANDATORY = "description is mandatory";
}
