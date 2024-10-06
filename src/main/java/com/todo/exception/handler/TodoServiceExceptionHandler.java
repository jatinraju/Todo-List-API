package com.todo.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.exception.TodoServiceException;
import com.todo.pojo.ErrorResponse;

@ControllerAdvice
public class TodoServiceExceptionHandler {
	@ExceptionHandler(TodoServiceException.class)
	public ResponseEntity<ErrorResponse> handleTodoServiceException(TodoServiceException ex) {
		ErrorResponse errResponse = ErrorResponse.builder().errorCode(ex.getErrorCode())
				.errorMessage(ex.getErrorMessage()).build();
		return new ResponseEntity<>(errResponse, ex.getHttpStatusCode());
	}
}
