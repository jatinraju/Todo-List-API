package com.todo.constants;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	// Validation Error Codes and Messages
	ERR_UNKNOWN("code", "ERR_UNKNOWN"), ERR_INVALID_USERNAME("username", "ERR_INVALID_USERNAME"),
	ERR_INVALID_EMAIL("email", "ERR_INVALID_EMAIL"), ERR_INVALID_PASSWORD("password", "ERR_INVALID_PASSWORD"),

	ERR_LIMIT("getAllTodos.limit", "ERR_INVALID_LIMIT"), ERR_PAGE("getAllTodos.page", "ERR_INVALID_PAGE"),
	ERR_SORT_DIRECTION("getAllTodos.sortDirection", "ERR_SORT_DIRECTION"),

	ERR_TITLE("title", "ERR_TODO_TITLE"), ERR_DESCRIPTION("description", "ERR_TODO_DESCRIPTION"),
	// User Data Validation Codes and Messages
	DUPLICATE_ENTRY_FOUND("20001",
			"A duplicate entry was found with the email provided. Please use a unique email address and try again."),

	USER_NOT_FOUND("20002", "User not found with the provided identifier. Please check and try again."),

	// TO DO Exceptions
	UNAUTHORIZED_RESOURCE_ACCESS("30000", "Unauthorized resource access. Please check your permissions and try again."),
	RESOURCE_NOT_FOUND("30001", "The requested resource was not found. Please check the identifier and try again."),

	// Generic Error
	GENERIC_ERROR("40000", "An unexpected error occurred. Please try again later."),

	// JWT Token Errors
	JWT_TOKEN_EXPIRED("50000", "JWT token has expired"), JWT_INVALID_SIGNATURE("50001", "Invalid JWT signature"),
	JWT_MALFORMED_TOKEN("50002", "Malformed JWT token"), JWT_INVALID_TOKEN("50003", "Invalid JWT token");

	private final String code;
	private final String message;

	// Constructor to set the error code
	ErrorCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	// Method to get an ErrorCodeEnum by code
	public static ErrorCodeEnum getByCode(String code) {
		for (ErrorCodeEnum errorCode : ErrorCodeEnum.values()) {
			if (errorCode.getCode().equals(code)) {
				return errorCode;
			}
		}
		return null; // or throw an exception if you prefer
	}

	// Method to get an ErrorCodeEnum by name (message)
	public static ErrorCodeEnum getByName(String name) {
		for (ErrorCodeEnum errorCode : ErrorCodeEnum.values()) {
			if (errorCode.getMessage().equals(name)) {
				return errorCode;
			}
		}
		return null; // or throw an exception if you prefer
	}
}
