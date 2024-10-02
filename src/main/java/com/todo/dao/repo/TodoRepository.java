package com.todo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todo.entity.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

	@Query(value = "SELECT t.*, (SELECT COUNT(*) FROM todo WHERE user_id = :id) AS total_count "
			+ "FROM todo t WHERE user_id = :id limit :l1, :l2", nativeQuery = true)
	List<Object[]> getAllTodos(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2);
}
