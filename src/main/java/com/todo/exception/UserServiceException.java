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
public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 7402619881185925663L;
	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatusCode;

}