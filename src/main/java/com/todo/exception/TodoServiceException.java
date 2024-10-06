package com.todo.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TodoServiceException extends RuntimeException {

	private static final long serialVersionUID = 5806279225416597028L;
	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatusCode;

}
