package com.todo.pojo;

import com.todo.constants.ErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@NotBlank(message = ErrorMessages.USERNAME_MANDATORY)
	private String username;

	@NotBlank(message = ErrorMessages.EMAIL_MANDATORY)
	@Email(message = ErrorMessages.EMAIL_INVALID_FORMAT)
	private String email;

	@NotBlank(message = ErrorMessages.PASSWORD_MANDATORY)
	@Size(min = 8, message = ErrorMessages.PASSWORD_MIN_LENGTH)
	private String password;
}
