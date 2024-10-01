package com.todo.dao.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.dao.UserDao;
import com.todo.dao.repo.UserRepository;
import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.entity.UserEntity;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		System.out.println("UserDaoImpl.registerUser() || userDto: " + userDto);

		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
		System.out.println(userEntity);

		UserEntity userEntityResponse = userRepo.save(userEntity);
		System.out.println("Response From Dabatese || userEntityResponse: " + userEntityResponse);

		UserDTO userDTOResponse = modelMapper.map(userEntityResponse, UserDTO.class);
		userDTOResponse.setPassword(userDto.getPassword());
		return userDTOResponse;
	}

	@Override
	public UserDTO authenticateUser(AuthRequestDTO authReqDto) {
		System.out.println("UserDaoImpl.authenticateUser() ||  authReqDto: " + authReqDto);
		Optional<UserEntity> userEntity = userRepo.findByEmail(authReqDto.getEmail());
		if (userEntity.isPresent()
				&& passwordEncoder.matches(authReqDto.getPassword(), userEntity.get().getPassword())) {
			UserDTO userDTOResponse = modelMapper.map(userEntity, UserDTO.class);
			return userDTOResponse;
		}
		return null;
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		Optional<UserEntity> op = userRepo.findByEmail(email);
		if (!op.isEmpty()) {
			UserDTO userDto = modelMapper.map(op.get(), UserDTO.class);
			return userDto;
		}
		return null;
	}

}
