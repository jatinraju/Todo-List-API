package com.todo.service.interfaces;

import com.todo.dto.TodoDTO;

public interface TodoService {

	TodoDTO addTodo(TodoDTO todoDto);

	TodoDTO updateTodo(TodoDTO todoDto);

}
