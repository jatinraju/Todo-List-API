package com.todo.dao;

import java.util.List;

import com.todo.dto.TodoDTO;
import com.todo.pojo.TodoRes;

public interface TodoDao {

	TodoDTO addTodo(TodoDTO todoDto);

	TodoDTO getTodoById(TodoDTO todoDto);

	TodoDTO updateTodo(TodoDTO todoDto);

	boolean deleteTodo(Long id);

	List<TodoRes> getAllTodos(Long userId, int page, int limit);
}
