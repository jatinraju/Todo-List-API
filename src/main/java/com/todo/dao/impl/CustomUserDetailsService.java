package com.todo.dao.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.dao.repo.UserRepository;
import com.todo.entity.UserEntity;
import com.todo.pojo.CustomUserDetail;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository uRepo;

	public CustomUserDetailsService(UserRepository uRepo) {
		super();
		this.uRepo = uRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<UserEntity> user = uRepo.findByEmail(email);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found !!!");
		}
		return new CustomUserDetail(user.get());
	}
}
