package com.todo.service.interfaces.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todo.dao.TodoDao;
import com.todo.dao.UserDao;
import com.todo.dto.TodoDTO;
import com.todo.dto.UserDTO;
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
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		// Getting details of User and Todo from the Database;
		UserDTO userDto = userDao.getUserByEmail(email);
		TodoDTO oldTodoDto = todoDao.getTodoById(todoDto);

		System.out.println("UserDTO: " + userDto + "\n TodoDTO: " + oldTodoDto);
		if (oldTodoDto != null && userDto.getId().equals(oldTodoDto.getUserId())) {
			TodoDTO updatedTodoDto = todoDao.updateTodo(todoDto);
			return updatedTodoDto;
		}
		System.out.println("user id mismatch with todo userid");
		return null;
	}

}
