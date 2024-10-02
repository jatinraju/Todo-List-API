package com.todo.service.interfaces;

import java.util.List;

import com.todo.dto.TodoDTO;
import com.todo.pojo.TodoRes;

public interface TodoService {

	TodoDTO addTodo(TodoDTO todoDto);

	TodoDTO updateTodo(TodoDTO todoDto);

	boolean deleteTodo(Long id);

	List<TodoRes> getAllTodos(int page, int limit);

}
