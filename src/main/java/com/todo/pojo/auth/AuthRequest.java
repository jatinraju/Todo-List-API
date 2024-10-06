package com.todo.pojo.auth;

import com.todo.constants.ErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

	@NotBlank(message = ErrorMessages.EMAIL_MANDATORY)
	@Email(message = ErrorMessages.EMAIL_INVALID_FORMAT)
	private String email;

	@NotBlank(message = ErrorMessages.PASSWORD_MANDATORY)
	@Size(min = 8, message = ErrorMessages.PASSWORD_MIN_LENGTH)
	private String password;

}
