package com.todo.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todo.exception.JwtTokenException;
import com.todo.pojo.ErrorResponse;

@RestControllerAdvice
public class JWTTokenExceptionHandler {

	@ExceptionHandler(JwtTokenException.class)
	public ResponseEntity<ErrorResponse> handleJwtTokenException(JwtTokenException ex) {
		ErrorResponse errResponse = ErrorResponse.builder().errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage()).build();
		return new ResponseEntity<>(errResponse, ex.getHttpStatusCode());
	}
}