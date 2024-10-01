package com.todo.dao.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.dao.TodoDao;
import com.todo.dao.repo.TodoRepository;
import com.todo.dto.TodoDTO;
import com.todo.entity.TodoEntity;

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

}
