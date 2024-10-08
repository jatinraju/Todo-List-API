package com.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
	private Long id;
	private Long userId;
	private String title;
	private String description;
}
