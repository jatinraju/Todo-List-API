package com.todo.service.interfaces;

import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.pojo.auth.AuthResponse;

public interface UserService {
	UserDTO registerUser(UserDTO userDto);

	AuthResponse authenticateUser(AuthRequestDTO authRequestDTO);

	AuthResponse refreshAccessToken(String refreshToken);
}
