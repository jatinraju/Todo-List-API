package com.todo.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GenericExceptionHandler {
	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
	// System.out.println(ex.getLocalizedMessage());
	// ErrorResponse err = new ErrorResponse();
	// err.setErrorCode(ErrorCodeEnum.GENERIC_ERROR.getCode());
	// err.setErrorMessage(ErrorCodeEnum.GENERIC_ERROR.getMessage());
	// return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	// }
}
