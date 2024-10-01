package com.todo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.constants.EndPoints;
import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.pojo.User;
import com.todo.pojo.UserRes;
import com.todo.pojo.auth.AuthRequest;
import com.todo.pojo.auth.AuthResponse;
import com.todo.service.interfaces.UserService;

@RestController
@RequestMapping(EndPoints.V1)
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(EndPoints.REGISTER_USER)
	public ResponseEntity<AuthResponse> doRegister(@RequestBody User user) {
		System.out.println("AuthController.doRegister() || user: " + user);

		// Stored the user details in the Database
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		System.out.println("UserDTO : " + userDTO);

		UserDTO userDtoRes = userService.registerUser(userDTO);
		UserRes userRes = modelMapper.map(userDtoRes, UserRes.class);

		// Generate the Token and give response back with Token
		AuthRequestDTO authRequestDto = modelMapper.map(userDtoRes, AuthRequestDTO.class);
		System.out.println("AuthRequestDto | authRequestDto: " + authRequestDto);

		AuthResponse authResponse = userService.authenticateUser(authRequestDto);
		System.out.println("AuthResponse || authResponse: " + authResponse);

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping(EndPoints.LOGIN_USER)
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
		System.out.println("AuthController.login() || authRequest ");

		AuthRequestDTO authRequestDto = modelMapper.map(authRequest, AuthRequestDTO.class);
//		System.out.println("AuthRequestDTO || authRequestDTO: " + authRequestDto); // password is visible here

		AuthResponse authResponse = userService.authenticateUser(authRequestDto);
		System.out.println("AuthResponse || authResponse: " + authResponse);
		// TODO: Check response if null then throw Exception here
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@GetMapping("/hello")
	public ResponseEntity<String> getMessage() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		System.out.println("email stored in the token ||" + email);
		return new ResponseEntity<>("Hi!, " + email, HttpStatus.OK);
	}
}
