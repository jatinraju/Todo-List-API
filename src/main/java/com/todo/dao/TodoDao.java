package com.todo.dao;

import com.todo.dto.TodoDTO;

public interface TodoDao {

	TodoDTO addTodo(TodoDTO todoDto);

	TodoDTO getTodoById(TodoDTO todoDto);

	TodoDTO updateTodo(TodoDTO todoDto);
}
