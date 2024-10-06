package com.todo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.constants.EndPoints;
import com.todo.dto.TodoDTO;
import com.todo.pojo.AllTodoRes;
import com.todo.pojo.Todo;
import com.todo.pojo.TodoRes;
import com.todo.service.interfaces.TodoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(EndPoints.V1_TODO)
@Validated
public class TodoController {

	private TodoService todoService;
	private ModelMapper modelMapper;

	public TodoController(TodoService todoService, ModelMapper modelMapper) {
		super();
		this.todoService = todoService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<AllTodoRes> getAllTodos(@RequestParam @Min(1) int page,
			@RequestParam @Min(1) @Max(100) int limit, @RequestParam(required = false) String filter,
			@RequestParam(required = false) @Pattern(regexp = "ASC|DESC", message = "Sort direction must be 'ASC' or 'DESC'") String sortDirection) {
		AllTodoRes allFilteredTodoFinalRes = todoService.getTodos(page, limit, filter, sortDirection);
		return new ResponseEntity<>(allFilteredTodoFinalRes, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TodoRes> addTodo(@Valid @RequestBody Todo todo) {
		TodoDTO todoDto = modelMapper.map(todo, TodoDTO.class);
		TodoDTO todoDtoRes = todoService.addTodo(todoDto);
		TodoRes todoRes = modelMapper.map(todoDtoRes, TodoRes.class);
		return new ResponseEntity<>(todoRes, HttpStatus.CREATED);
	}

	@PutMapping(EndPoints.UPDATE_TODO)
	public ResponseEntity<TodoRes> updateTodo(@PathVariable(required = true) Long id, @Valid @RequestBody Todo todo) {
		TodoDTO todoDto = modelMapper.map(todo, TodoDTO.class);
		todoDto.setId(id);
		TodoDTO updatedTodoDto = todoService.updateTodo(todoDto);
		TodoRes todoRes = modelMapper.map(updatedTodoDto, TodoRes.class);
		return new ResponseEntity<>(todoRes, HttpStatus.OK);
	}

	@DeleteMapping(EndPoints.DELETE_TODO)
	public ResponseEntity<?> deleteTodo(@PathVariable(required = true) Long id) {
		todoService.deleteTodo(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
