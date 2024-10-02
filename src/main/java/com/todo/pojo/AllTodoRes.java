package com.todo.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllTodoRes {
	private List<TodoRes> data;
	private int page;
	private int limit;
	private int total;
}
