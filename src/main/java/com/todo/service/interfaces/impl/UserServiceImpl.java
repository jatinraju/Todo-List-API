package com.todo.service.interfaces.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dao.UserDao;
import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.pojo.auth.AuthResponse;
import com.todo.service.interfaces.UserService;
import com.todo.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		System.out.println("UserServiceImpl.registerUser() || userDto: " + userDto);
		UserDTO userDtoRes = userDao.registerUser(userDto);
		return userDtoRes;
	}

	@Override
	public AuthResponse authenticateUser(AuthRequestDTO authRequestDTO) {
		System.out.println("UserServiceImpl.authenticateUser() || authRequestDTO: "); // password is visible here
		UserDTO userDtoRes = userDao.authenticateUser(authRequestDTO);
		if (userDtoRes != null) {
			String generatedToken = jwtUtil.generateToken(userDtoRes.getEmail());
			return AuthResponse.builder().email(userDtoRes.getEmail()).token(generatedToken).build();
		}
		return null;
	}
}
