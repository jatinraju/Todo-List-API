package com.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
	@GetMapping("/hello")
	public ResponseEntity<String> getMessage() {
		return new ResponseEntity<>("Hello from PublicController", HttpStatus.OK);
	}
}
