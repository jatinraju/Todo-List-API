package com.todo.dto;

import lombok.Data;

@Data
public class TodoDTO {
	private Long id;
	private Long userId;
	private String title;
	private String description;
}
