package com.todo.service.interfaces.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todo.dao.UserDao;
import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.pojo.auth.AuthResponse;
import com.todo.pojo.auth.RefreshTokenRes;
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
			String generatedAccessToken = jwtUtil.generateAccessToken(userDtoRes.getEmail());
			String generatedRefreshToken = jwtUtil.generateRefreshToken(userDtoRes.getEmail());
			return AuthResponse.builder().email(userDtoRes.getEmail()).access_token(generatedAccessToken)
					.refresh_token(generatedRefreshToken).build();
		}
		return null;
	}

	@Override
	public RefreshTokenRes refreshAccessToken() {
		System.out.println("UserServiceImpl.refreshAccessToken()");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println("Refresh Token Request for Email: " + email);
		String generatedRefreshToken = jwtUtil.generateRefreshToken(email);
		return RefreshTokenRes.builder().access_token(generatedRefreshToken).build();
	}
}
