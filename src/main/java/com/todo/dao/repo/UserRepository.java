package com.todo.dao.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
}
