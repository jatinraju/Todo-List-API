package com.todo.exception.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.constants.ErrorCodeEnum;
import com.todo.constants.ErrorMessages;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		Map<String, Object> errors = new HashMap<>();
		errors.put(ErrorMessages.STATUS, ErrorMessages.ERROR);
		errors.put(ErrorMessages.ERRORS, new HashMap<>());

		// Loop through all field errors
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String errorCode = ErrorCodeEnum.getByCode(fieldName).getMessage();
			String errorMessage = error.getDefaultMessage();

			// Check if there is already an entry for the field
			Map<String, Object> fieldErrors;
			Map<String, Object> err = (Map<String, Object>) errors.get(ErrorMessages.ERRORS);

			if (err.containsKey(fieldName)) {
				// If field already has errors, retrieve it
				fieldErrors = (Map<String, Object>) err.get(fieldName);
			} else {
				// If not, create a new list for the field
				fieldErrors = new HashMap<>();
				fieldErrors.put(ErrorCodeEnum.ERR_UNKNOWN.getCode(),
						errorCode != null ? errorCode : ErrorCodeEnum.ERR_UNKNOWN);
				fieldErrors.put(ErrorMessages.MESSAGE, new ArrayList<String>());
			}
			// Add the current error message to the list of messages for this field
			List<String> fieldMessage = (List<String>) fieldErrors.get(ErrorMessages.MESSAGE);
			fieldMessage.add(errorMessage);
			err.put(fieldName, fieldErrors);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put(ErrorMessages.STATUS, ErrorMessages.ERROR);
		errors.put(ErrorMessages.ERRORS, new HashMap<>());

		// Loop through all constraint violations
		ex.getConstraintViolations().forEach(violation -> {
			String fieldName = violation.getPropertyPath().toString();
			String errorCode = ErrorCodeEnum.getByCode(fieldName).getMessage();
			String errorMessage = violation.getMessage();

			// Check if there is already an entry for the field
			Map<String, Object> fieldErrors;
			Map<String, Object> err = (Map<String, Object>) errors.get(ErrorMessages.ERRORS);

			if (err.containsKey(fieldName)) {
				// If field already has errors, retrieve it
				fieldErrors = (Map<String, Object>) err.get(fieldName);
			} else {
				// If not, create a new entry for the field
				fieldErrors = new HashMap<>();
				fieldErrors.put(ErrorCodeEnum.ERR_UNKNOWN.getCode(),
						errorCode != null ? errorCode : ErrorCodeEnum.ERR_UNKNOWN);
				fieldErrors.put(ErrorMessages.MESSAGE, new ArrayList<String>());
			}
			// Add the current error message to the list of messages for this field
			List<String> fieldMessage = (List<String>) fieldErrors.get(ErrorMessages.MESSAGE);
			fieldMessage.add(errorMessage);
			err.put(fieldName, fieldErrors);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
