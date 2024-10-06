package com.todo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.todo.dao.TodoDao;
import com.todo.dao.repo.TodoRepository;
import com.todo.dto.TodoDTO;
import com.todo.entity.TodoEntity;
import com.todo.pojo.AllTodoRes;
import com.todo.pojo.TodoRes;

@Component
public class TodoDaoImpl implements TodoDao {

	private TodoRepository todoRepo;
	private ModelMapper modelMapper;

	public TodoDaoImpl(TodoRepository todoRepo, ModelMapper modelMapper) {
		super();
		this.todoRepo = todoRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public TodoDTO addTodo(TodoDTO todoDto) {
		TodoEntity todoEntity = modelMapper.map(todoDto, TodoEntity.class);
		TodoEntity todoEntityRes = todoRepo.save(todoEntity);
		return modelMapper.map(todoEntityRes, TodoDTO.class);
	}

	@Override
	public TodoDTO getTodoById(TodoDTO todoDto) {
		Optional<TodoEntity> op = todoRepo.findById(todoDto.getId());
		if (op.isPresent()) {
			return modelMapper.map(op.get(), TodoDTO.class);
		}
		return null;
	}

	@Override
	public TodoDTO updateTodo(TodoDTO todoDto) {
		Optional<TodoEntity> op = todoRepo.findById(todoDto.getId());
		if (op.isPresent()) {
			TodoEntity todoEntity = op.get();
			todoEntity.setTitle(todoDto.getTitle());
			todoEntity.setDescription(todoDto.getDescription());
			TodoEntity todoEntityRes = todoRepo.save(todoEntity);
			return modelMapper.map(todoEntityRes, TodoDTO.class);
		}
		return null;
	}

	@Override
	public boolean deleteTodo(Long id) {
		todoRepo.deleteById(id);
		return true;
	}

	@Override
	public AllTodoRes getAllTodos(Long userId, int page, int limit) {
		List<Object[]> todosAndCount = todoRepo.getAllTodos(userId, page, limit);
		return generateResponse(todosAndCount);
	}

	@Override
	public AllTodoRes getAllFilteredTodos(Long userId, int page, int limit, String filter) {
		List<Object[]> filteredTodosAndCount = todoRepo.getAllFilteredTodos(userId, page, limit, filter);
		return generateResponse(filteredTodosAndCount);
	}

	@Override
	public AllTodoRes getAllSortedTodos(Long userId, int page, int limit, String odr) {
		List<Object[]> sortedTodosAndCount;
		if (odr.equalsIgnoreCase("asc")) {
			sortedTodosAndCount = todoRepo.getAllSortedByTodosASC(userId, page, limit);
		} else {
			sortedTodosAndCount = todoRepo.getAllSortedByTodosDESC(userId, page, limit);
		}
		return generateResponse(sortedTodosAndCount);
	}

	@Override
	public AllTodoRes getAllFilteredAndSortedTodos(Long userId, int page, int limit, String filter, String odr) {
		List<Object[]> filteredAndSortedTodosAndCount;
		if (odr.equalsIgnoreCase("asc")) {
			filteredAndSortedTodosAndCount = todoRepo.getAllFilteredAndSortedTodosASC(userId, page, limit, filter);
		} else {
			filteredAndSortedTodosAndCount = todoRepo.getAllFilteredAndSortedTodosDESC(userId, page, limit, filter);
		}
		return generateResponse(filteredAndSortedTodosAndCount);

	}

	private AllTodoRes generateResponse(List<Object[]> listOfObject) {
		if (!listOfObject.isEmpty()) {
			List<TodoRes> finalList = new ArrayList<>();
			for (Object[] entity : listOfObject) {
				TodoRes todoRes = TodoRes.builder().id(Long.parseLong(entity[0].toString())).title(entity[1].toString())
						.description(entity[2].toString()).build();
				finalList.add(todoRes);
			}
			long totalCount = ((Number) listOfObject.get(0)[listOfObject.get(0).length - 1]).longValue();
			return AllTodoRes.builder().data(finalList).total(totalCount).build();
		}
		return null;
	}

}
