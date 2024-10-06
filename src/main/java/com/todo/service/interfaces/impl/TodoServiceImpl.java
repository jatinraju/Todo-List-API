package com.todo.service.interfaces.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todo.constants.ErrorCodeEnum;
import com.todo.dao.TodoDao;
import com.todo.dao.UserDao;
import com.todo.dto.TodoDTO;
import com.todo.dto.UserDTO;
import com.todo.exception.TodoServiceException;
import com.todo.exception.UserServiceException;
import com.todo.pojo.AllTodoRes;
import com.todo.service.interfaces.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	private UserDao userDao;
	private TodoDao todoDao;

	public TodoServiceImpl(UserDao userDao, TodoDao todoDao) {
		super();
		this.userDao = userDao;
		this.todoDao = todoDao;
	}

	@Override
	public TodoDTO addTodo(TodoDTO todoDto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDTO userDto = userDao.getUserByEmail(email);
		if (userDto == null) {
			throw new UserServiceException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),
					ErrorCodeEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
		}
		todoDto.setUserId(userDto.getId());
		todoDto = todoDao.addTodo(todoDto);
		return todoDto;
	}

	@Override
	public TodoDTO updateTodo(TodoDTO todoDto) {
		isMatchingTodoAndUser(todoDto);
		return todoDao.updateTodo(todoDto);
	}

	@Override
	public boolean deleteTodo(Long id) {
		TodoDTO tempTodoDto = TodoDTO.builder().id(id).build();
		TodoDTO resTodoDto = todoDao.getTodoById(tempTodoDto);
		if (resTodoDto == null) {
			throw new TodoServiceException(ErrorCodeEnum.RESOURCE_NOT_FOUND.getCode(),
					ErrorCodeEnum.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
		}

		isMatchingTodoAndUser(resTodoDto);
		return todoDao.deleteTodo(id);
	}

	@Override
	public AllTodoRes getTodos(int page, int limit, String filter, String odr) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDTO userDto = userDao.getUserByEmail(email);
		if (userDto == null) {
			throw new UserServiceException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),
					ErrorCodeEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
		}
		page = (page - 1) * 10;

		AllTodoRes allTodoRes;
		if ((filter == null || filter.isEmpty()) && (odr == null || odr.isEmpty())) {
			allTodoRes = todoDao.getAllTodos(userDto.getId(), page, limit);
		} else if (filter == null || filter.isEmpty()) {
			allTodoRes = todoDao.getAllSortedTodos(userDto.getId(), page, limit, odr);
		} else if (odr == null || odr.isEmpty()) {
			allTodoRes = todoDao.getAllFilteredTodos(userDto.getId(), page, limit, filter);
		} else {
			allTodoRes = todoDao.getAllFilteredAndSortedTodos(userDto.getId(), page, limit, filter, odr);

		}

		if (allTodoRes != null) {
			allTodoRes.setPage((page / 10) + 1);
			allTodoRes.setLimit(limit);
			return allTodoRes;
		}
		throw new TodoServiceException(ErrorCodeEnum.RESOURCE_NOT_FOUND.getCode(),
				ErrorCodeEnum.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Checking todo userid and user id is matching or not
	public boolean isMatchingTodoAndUser(TodoDTO todoDto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		UserDTO userDto = userDao.getUserByEmail(email);
		if (userDto == null) {
			throw new UserServiceException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),
					ErrorCodeEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
		}

		TodoDTO oldTodoDto = todoDao.getTodoById(todoDto);
		if (oldTodoDto == null) {
			throw new TodoServiceException(ErrorCodeEnum.RESOURCE_NOT_FOUND.getCode(),
					ErrorCodeEnum.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
		}

		if (!userDto.getId().equals(oldTodoDto.getUserId())) {
			throw new TodoServiceException(ErrorCodeEnum.UNAUTHORIZED_RESOURCE_ACCESS.getCode(),
					ErrorCodeEnum.UNAUTHORIZED_RESOURCE_ACCESS.getMessage(), HttpStatus.UNAUTHORIZED);
		}

		return true;
	}
}
