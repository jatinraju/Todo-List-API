package com.todo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.constants.EndPoints;
import com.todo.dto.TodoDTO;
import com.todo.pojo.Todo;
import com.todo.pojo.TodoRes;
import com.todo.service.interfaces.TodoService;

@RestController
@RequestMapping(EndPoints.V1_TODO)
public class TodoController {

	@Autowired
	private TodoService todoService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<TodoRes> addTodo(@RequestBody Todo todo) {
		System.out.println("TodoController.addTodo() | todo: " + todo);

		TodoDTO todoDto = modelMapper.map(todo, TodoDTO.class);
		System.out.println("Mapped TodoDTO | todoDto: " + todoDto);

		TodoDTO todoDtoRes = todoService.addTodo(todoDto);
		System.out.println("Response From Service | todoDto: " + todoDtoRes);

		TodoRes todoRes = modelMapper.map(todoDtoRes, TodoRes.class);
		return new ResponseEntity<>(todoRes, HttpStatus.CREATED);
	}

	@PutMapping
	@RequestMapping(EndPoints.UPDATE_TODO)
	public ResponseEntity<TodoRes> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
		System.out.println("TodoController.updateTodo() | todo: " + todo);

		TodoDTO todoDto = modelMapper.map(todo, TodoDTO.class);
		todoDto.setId(id);
		System.out.println("Mapped TodoDTO | todoDto: " + todoDto);

		// call service class method
		TodoDTO updatedTodoDto = todoService.updateTodo(todoDto);
		System.out.println("Response From Service | updatedTodoDto: " + updatedTodoDto);

		TodoRes todoRes = modelMapper.map(updatedTodoDto, TodoRes.class);
		return new ResponseEntity<>(todoRes, HttpStatus.OK);

	}

}
