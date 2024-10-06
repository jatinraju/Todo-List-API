package com.todo.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todo.constants.TodoQueries;
import com.todo.entity.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

	@Query(value = TodoQueries.GET_ALL_TODOS, nativeQuery = true)
	List<Object[]> getAllTodos(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2);

	@Query(value = TodoQueries.GET_ALL_FILTERED_TODOS, nativeQuery = true)
	List<Object[]> getAllFilteredTodos(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2,
			@Param("f") String target);

	@Query(value = TodoQueries.GET_ALL_SORTED_BY_TODOS_ASC, nativeQuery = true)
	List<Object[]> getAllSortedByTodosASC(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2);

	@Query(value = TodoQueries.GET_ALL_SORTED_BY_TODOS_DESC, nativeQuery = true)
	List<Object[]> getAllSortedByTodosDESC(@Param("id") Long id, @Param("l1") int limit1, @Param("l2") int limit2);

	@Query(value = TodoQueries.GET_ALL_FILTERED_AND_SORTED_TODOS_ASC, nativeQuery = true)
	List<Object[]> getAllFilteredAndSortedTodosASC(@Param("id") Long id, @Param("l1") int limit1,
			@Param("l2") int limit2, @Param("f") String target);

	@Query(value = TodoQueries.GET_ALL_FILTERED_AND_SORTED_TODOS_DESC, nativeQuery = true)
	List<Object[]> getAllFilteredAndSortedTodosDESC(@Param("id") Long id, @Param("l1") int limit1,
			@Param("l2") int limit2, @Param("f") String target);
}
