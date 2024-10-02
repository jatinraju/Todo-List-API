package com.todo.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.dao.TodoDao;
import com.todo.dao.repo.TodoRepository;
import com.todo.dto.TodoDTO;
import com.todo.entity.TodoEntity;
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
	public List<TodoRes> getAllTodos(Long userId, int page, int limit) {
		System.out
				.println("TodoDaoImpl.getAllTodos() | userId: " + userId + " || page: " + page + " || limit: " + limit);

		List<Object[]> todosAndCount = todoRepo.getAllTodos(userId, page, limit);
		System.out.println(" List<Object[]> todosAndCount: " + todosAndCount);
		if (!todosAndCount.isEmpty()) {
			// extract list of todos and map it to TodoDTO
			List<TodoDTO> todos = todosAndCount.stream().map(result -> modelMapper.map(result, TodoDTO.class))
					.collect(Collectors.toList());
			System.out.println(todos);

			// TODO: Pending extract todoDTO from Object[]
			// extract total count of todos by single user
			System.out.println(todosAndCount.get(0)[0]);
			System.out.println(todosAndCount.get(1)[0]);
			System.out.println(todosAndCount.get(2)[0]);
			System.out.println(todosAndCount.get(3)[0]);
			System.out.println(todosAndCount.get(4)[0]);
			System.out.println(todosAndCount.get(5)[0]);
			System.out.println(todosAndCount.get(6)[0]);
			System.out.println(todosAndCount.get(7)[0]);
			System.out.println(todosAndCount.get(8)[0]);
			System.out.println(todosAndCount.get(9)[0]);

			// done with count;
			int totalCount = ((Number) todosAndCount.get(0)[todosAndCount.get(0).length - 1]).intValue();
			System.out.println(totalCount);
		}

		return null;

//		return list.stream() // Stream<TodoEntity>
//				.map(entity -> modelMapper.map(entity, TodoRes.class)).collect(Collectors.toList());
	}

}
