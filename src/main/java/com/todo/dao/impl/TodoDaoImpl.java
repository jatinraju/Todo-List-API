package com.todo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.dao.TodoDao;
import com.todo.dao.repo.TodoRepository;
import com.todo.dto.TodoDTO;
import com.todo.entity.TodoEntity;
import com.todo.pojo.AllTodoRes;
import com.todo.pojo.TodoRes;

@Component
public class TodoDaoImpl implements TodoDao {

	@Autowired
	private TodoRepository todoRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TodoDTO addTodo(TodoDTO todoDto) {
		System.out.println("TodoDaoImpl.addTodo() | todoDto: " + todoDto);

		TodoEntity todoEntity = modelMapper.map(todoDto, TodoEntity.class);
		System.out.println("Mapped TodoEntitny | todoEntity: " + todoEntity);

		TodoEntity todoEntityRes = todoRepo.save(todoEntity);
		System.out.println("Response from Database | todoEntityRes: " + todoEntityRes);

		TodoDTO todoDtoRes = modelMapper.map(todoEntityRes, TodoDTO.class);
		return todoDtoRes;
	}

	@Override
	public TodoDTO getTodoById(TodoDTO todoDto) {
		System.out.println("TodoDaoImpl.getTodoById() | todoDto: " + todoDto);

		Optional<TodoEntity> op = todoRepo.findById(todoDto.getId());
		if (op.isPresent()) {
			TodoDTO todoDtoRes = modelMapper.map(op.get(), TodoDTO.class);
			System.out.println("Response From Database | todoDtoRes: +" + todoDtoRes);
			return todoDtoRes;
		}
		return null;
	}

	@Override
	public TodoDTO updateTodo(TodoDTO todoDto) {
		System.out.println("TodoDaoImpl.updateTodo() | todoDto: " + todoDto);

		Optional<TodoEntity> op = todoRepo.findById(todoDto.getId());
		if (op.isPresent()) {
			TodoEntity todoEntity = op.get();
			todoEntity.setTitle(todoDto.getTitle());
			todoEntity.setDescription(todoDto.getDescription());

			TodoEntity todoEntityRes = todoRepo.save(todoEntity);
			System.out.println("Updated TodoEntity in Database | todoEntityRes: " + todoEntityRes);

			TodoDTO updatedTodoDto = modelMapper.map(todoEntityRes, TodoDTO.class);
			return updatedTodoDto;
		}

		return null;
	}

	@Override
	public boolean deleteTodo(Long id) {
		System.out.println("TodoDaoImpl.deleteTodo() | id: " + id);
		Optional<TodoEntity> op = todoRepo.findById(id);
		System.out.println("Todo found for deletion | op: " + op.get());

		if (op.isPresent()) {
			todoRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public AllTodoRes getAllTodos(Long userId, int page, int limit) {
		System.out
				.println("TodoDaoImpl.getAllTodos() | userId: " + userId + " || page: " + page + " || limit: " + limit);

		List<Object[]> todosAndCount = todoRepo.getAllTodos(userId, page, limit);
		return generateResponse(todosAndCount);
	}

	@Override
	public AllTodoRes getAllFilteredTodos(Long userId, int page, int limit, String filter) {
		System.out.println("TodoDaoImpl.getAllFilteredTodos() | userId: " + userId + " || page: " + page + " || limit: "
				+ limit + " || filter: " + filter);

		List<Object[]> filteredTodosAndCount = todoRepo.getAllFilteredTodos(userId, page, limit, filter);
		return generateResponse(filteredTodosAndCount);
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
		System.out.println("getting null????");
		return null;
	}

}
