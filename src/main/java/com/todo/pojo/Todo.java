package com.todo.pojo;

import com.todo.constants.ErrorMessages;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Todo {
	@NotBlank(message = ErrorMessages.TITLE_MANDATORY)
	private String title;

	@NotBlank(message = ErrorMessages.DESCRIPTION_MANDATORY)
	private String description;
}
