package com.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.constants.EndPoints;

@RestController
@RequestMapping(EndPoints.TEST_PUBLIC)

public class PublicController {
	@GetMapping(EndPoints.TEST_HELLO)
	public ResponseEntity<String> getMessage() {
		return new ResponseEntity<>("Hello from Public Controller", HttpStatus.OK);
	}
}
