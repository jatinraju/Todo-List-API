package com.todo.service.interfaces.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todo.dao.TodoDao;
import com.todo.dao.UserDao;
import com.todo.dto.TodoDTO;
import com.todo.dto.UserDTO;
import com.todo.pojo.AllTodoRes;
import com.todo.service.interfaces.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TodoDao todoDao;

	@Override
	public TodoDTO addTodo(TodoDTO todoDto) {
		System.out.println("TodoServiceImpl.addTodo() | todoDto: " + todoDto);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		// TODO: add if user not found check;
		UserDTO userDto = userDao.getUserByEmail(email);
		todoDto.setUserId(userDto.getId());

		System.out.println("Todo Ready for DB | todoDto: " + todoDto);
		// call db layer to get the full userDTO details
		todoDto = todoDao.addTodo(todoDto);
		return todoDto;
	}

	@Override
	public TodoDTO updateTodo(TodoDTO todoDto) {
		System.out.println("TodoServiceImpl.updateTodo() | todoDto: " + todoDto);
		if (isMatchingTodoAndUser(todoDto)) {
			return todoDao.updateTodo(todoDto);
		}
		System.out.println("user id mismatch with todo userid");
		return null;
	}

	@Override
	public boolean deleteTodo(Long id) {
		System.out.println("TodoServiceImpl.deleteTodo() | id: " + id);
		TodoDTO tempTodoDto = TodoDTO.builder().id(id).build();

		TodoDTO resTodoDto = todoDao.getTodoById(tempTodoDto);
		if (resTodoDto != null && isMatchingTodoAndUser(resTodoDto)) {
			boolean res = todoDao.deleteTodo(id);
			return res;
		}

		// throw exception here todo not found or user idd not match with todo.
		return false;
	}

	@Override
	public AllTodoRes getTodos(int page, int limit, String filter) {
		System.out.println(
				"TodoServiceImpl.getTodos() | page: " + page + " || limit: " + limit + " || filter: " + filter);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDTO userDto = userDao.getUserByEmail(email);
		page = (page - 1) * 10;
		AllTodoRes allTodoRes;
		if (filter == null || filter.isEmpty()) {
			allTodoRes = todoDao.getAllTodos(userDto.getId(), page, limit);
		} else {
			allTodoRes = todoDao.getAllFilteredTodos(userDto.getId(), page, limit, filter);
		}
		if (allTodoRes != null) {
			allTodoRes.setPage((page / 10) + 1);
			allTodoRes.setLimit(limit);
			return allTodoRes;
		}
		return null;
	}

	// Checking todo userid and user id is matching or not
	public boolean isMatchingTodoAndUser(TodoDTO todoDto) {
		System.out.println("TodoServiceImpl.isMatichingTodoAndUser() | todoDto: " + todoDto);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		// Getting details of User and Todo from the Database;
		UserDTO userDto = userDao.getUserByEmail(email);
		TodoDTO oldTodoDto = todoDao.getTodoById(todoDto);
		System.out.println("UserDTO: " + userDto + "\n TodoDTO: " + oldTodoDto);

		return oldTodoDto != null && userDto.getId().equals(oldTodoDto.getUserId());
	}
}
