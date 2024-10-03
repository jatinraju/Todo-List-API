package com.todo.dto;

import com.todo.entity.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntityWithCountDTO {

	private TodoEntity todoEntity;
	private long totalCount;

}
