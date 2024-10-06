package com.todo.controller;

import org.modelmapper.ModelMapper;
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
import com.todo.pojo.auth.AuthRequest;
import com.todo.pojo.auth.AuthResponse;
import com.todo.pojo.auth.RefreshTokenRes;
import com.todo.service.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(EndPoints.V1)
public class AuthController {

	private UserService userService;
	private ModelMapper modelMapper;

	public AuthController(UserService userService, ModelMapper modelMapper) {
		super();
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@PostMapping(EndPoints.REGISTER_USER)
	public ResponseEntity<AuthResponse> doRegister(@Valid @RequestBody User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		UserDTO userDtoRes = userService.registerUser(userDTO);
		AuthRequestDTO authRequestDto = modelMapper.map(userDtoRes, AuthRequestDTO.class);
		AuthResponse authResponse = userService.authenticateUser(authRequestDto);
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping(EndPoints.LOGIN_USER)
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
		AuthRequestDTO authRequestDto = modelMapper.map(authRequest, AuthRequestDTO.class);
		AuthResponse authResponse = userService.authenticateUser(authRequestDto);
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping(EndPoints.REFRESH_TOKEN)
	public ResponseEntity<RefreshTokenRes> getRefreshToken() {
		RefreshTokenRes refreshTokenRes = userService.refreshAccessToken();
		return new ResponseEntity<>(refreshTokenRes, HttpStatus.OK);
	}

	@GetMapping(EndPoints.TEST)
	public ResponseEntity<String> getMessage() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<>("Hi!, " + email, HttpStatus.OK);
	}
}
