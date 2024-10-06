package com.todo.dao.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.dao.UserDao;
import com.todo.dao.repo.UserRepository;
import com.todo.dto.AuthRequestDTO;
import com.todo.dto.UserDTO;
import com.todo.entity.UserEntity;

@Service
public class UserDaoImpl implements UserDao {

	private BCryptPasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;
	private UserRepository userRepo;

	public UserDaoImpl(BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRepository userRepo) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
		this.userRepo = userRepo;
	}

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash the password
		UserEntity userEntityResponse = userRepo.save(userEntity);
		UserDTO userDTOResponse = modelMapper.map(userEntityResponse, UserDTO.class);
		userDTOResponse.setPassword(userDto.getPassword());
		return userDTOResponse;
	}

	@Override
	public UserDTO authenticateUser(AuthRequestDTO authReqDto) {
		Optional<UserEntity> userEntity = userRepo.findByEmail(authReqDto.getEmail());
		if (userEntity.isPresent()
				&& passwordEncoder.matches(authReqDto.getPassword(), userEntity.get().getPassword())) {
			return modelMapper.map(userEntity, UserDTO.class);
		}
		return null;
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		Optional<UserEntity> op = userRepo.findByEmail(email);
		if (!op.isEmpty()) {
			return modelMapper.map(op.get(), UserDTO.class);
		}
		return null;
	}

}
