package com.todo.service.interfaces.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todo.constants.ErrorCodeEnum;
import com.todo.dao.UserDao;
import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.exception.UserServiceException;
import com.todo.pojo.auth.AuthResponse;
import com.todo.pojo.auth.RefreshTokenRes;
import com.todo.service.interfaces.UserService;
import com.todo.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private JwtUtil jwtUtil;

	public UserServiceImpl(UserDao userDao, JwtUtil jwtUtil) {
		super();
		this.userDao = userDao;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		try {
			UserDTO userDtoRes = userDao.registerUser(userDto);
			return userDtoRes;
		} catch (Exception ex) {
			throw new UserServiceException(ErrorCodeEnum.DUPLICATE_ENTRY_FOUND.getCode(),
					ErrorCodeEnum.DUPLICATE_ENTRY_FOUND.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@Override
	public AuthResponse authenticateUser(AuthRequestDTO authRequestDTO) {
		UserDTO userDtoRes = userDao.authenticateUser(authRequestDTO);
		if (userDtoRes != null) {
			String generatedAccessToken = jwtUtil.generateAccessToken(userDtoRes.getEmail());
			String generatedRefreshToken = jwtUtil.generateRefreshToken(userDtoRes.getEmail());
			return AuthResponse.builder().email(userDtoRes.getEmail()).access_token(generatedAccessToken)
					.refresh_token(generatedRefreshToken).build();
		}
		throw new UserServiceException(ErrorCodeEnum.USER_NOT_FOUND.getCode(),
				ErrorCodeEnum.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
	}

	@Override
	public RefreshTokenRes refreshAccessToken() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		String generatedRefreshToken = jwtUtil.generateRefreshToken(email);
		return RefreshTokenRes.builder().access_token(generatedRefreshToken).build();
	}
}
