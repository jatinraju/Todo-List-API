package com.todo.dao;

import com.todo.dto.TodoDTO;
import com.todo.pojo.AllTodoRes;

public interface TodoDao {

	TodoDTO addTodo(TodoDTO todoDto);

	TodoDTO getTodoById(TodoDTO todoDto);

	TodoDTO updateTodo(TodoDTO todoDto);

	boolean deleteTodo(Long id);

	AllTodoRes getAllTodos(Long userId, int page, int limit);

	AllTodoRes getAllFilteredTodos(Long userId, int page, int limit, String filter);
}
