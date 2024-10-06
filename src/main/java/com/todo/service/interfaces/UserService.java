package com.todo.service.interfaces;

import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.pojo.auth.AuthResponse;
import com.todo.pojo.auth.RefreshTokenRes;

public interface UserService {

	UserDTO registerUser(UserDTO userDto);

	AuthResponse authenticateUser(AuthRequestDTO authRequestDTO);

	RefreshTokenRes refreshAccessToken();

}
