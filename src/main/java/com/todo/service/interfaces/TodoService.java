package com.todo.service.interfaces;

import com.todo.dto.TodoDTO;
import com.todo.pojo.AllTodoRes;

public interface TodoService {

	TodoDTO addTodo(TodoDTO todoDto);

	TodoDTO updateTodo(TodoDTO todoDto);

	boolean deleteTodo(Long id);

	AllTodoRes getAllTodos(int page, int limit);

}
