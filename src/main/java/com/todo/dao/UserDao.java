package com.todo.dao;

import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;

public interface UserDao {

	UserDTO registerUser(UserDTO userDto);

	UserDTO authenticateUser(AuthRequestDTO authRequestDTO);

	UserDTO getUserByEmail(String email);

}
