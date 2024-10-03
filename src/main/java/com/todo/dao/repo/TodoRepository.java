package com.todo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todo.entity.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

//	@Query(value = "SELECT t.*, (SELECT COUNT(*) FROM todo WHERE user_id = :id) AS total_count "
//			+ "FROM todo t WHERE user_id = :id limit :l1, :l2", nativeQuery = true)

	@Query(value = "WITH total_count AS ( SELECT COUNT(*) AS total FROM todo WHERE user_id = :id) SELECT t.*, tc.total FROM todo t CROSS JOIN total_count tc  WHERE t.user_id = :id  LIMIT :l2 OFFSET :l1;", nativeQuery = true)
	List<Object[]> getAllTodos(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2);

	@Query(value = "WITH filtered_todos AS (SELECT * FROM todo WHERE user_id = :id AND (title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))), total_count AS (SELECT COUNT(*) AS total FROM todo WHERE user_id = :id AND (title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))) SELECT ft.*, tc.total FROM filtered_todos ft CROSS JOIN total_count tc LIMIT :l2 OFFSET :l1;", nativeQuery = true)
	List<Object[]> getAllFilteredTodos(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2,
			@Param("f") String target);

}
