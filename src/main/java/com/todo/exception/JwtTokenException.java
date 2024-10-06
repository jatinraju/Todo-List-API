package com.todo.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class JwtTokenException extends RuntimeException {

	private static final long serialVersionUID = 4171059752373063476L;
	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatusCode;

	public JwtTokenException(String errorCode, String errorMessage, HttpStatus httpStatusCode) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatusCode = httpStatusCode;
	}
}
