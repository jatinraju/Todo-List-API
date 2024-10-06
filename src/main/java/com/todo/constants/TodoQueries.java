package com.todo.constants;

public class TodoQueries {

	private TodoQueries() {
		super();
	}

	public static final String GET_ALL_TODOS = "WITH total_count AS ( SELECT COUNT(*) AS total FROM todo WHERE user_id = :id) "
			+ "SELECT t.*, tc.total FROM todo t CROSS JOIN total_count tc WHERE t.user_id = :id LIMIT :l2 OFFSET :l1;";

	public static final String GET_ALL_FILTERED_TODOS = "WITH filtered_todos AS (SELECT * FROM todo WHERE user_id = :id "
			+ "AND (title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))), "
			+ "total_count AS (SELECT COUNT(*) AS total FROM todo WHERE user_id = :id "
			+ "AND (title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))) "
			+ "SELECT ft.*, tc.total FROM filtered_todos ft CROSS JOIN total_count tc LIMIT :l2 OFFSET :l1;";

	public static final String GET_ALL_SORTED_BY_TODOS_ASC = "WITH total_count AS (SELECT COUNT(*) AS total FROM todo WHERE user_id = :id) "
			+ "SELECT t.*, tc.total FROM todo t CROSS JOIN total_count tc WHERE t.user_id = :id ORDER BY t.title ASC LIMIT :l2 OFFSET :l1;";

	public static final String GET_ALL_SORTED_BY_TODOS_DESC = "WITH total_count AS (SELECT COUNT(*) AS total FROM todo WHERE user_id = :id) "
			+ "SELECT t.*, tc.total FROM todo t CROSS JOIN total_count tc WHERE t.user_id = :id ORDER BY t.title DESC LIMIT :l2 OFFSET :l1;";

	public static final String GET_ALL_FILTERED_AND_SORTED_TODOS_ASC = "WITH filtered_todos AS (SELECT * FROM todo WHERE user_id = :id "
			+ "AND (:f IS NULL OR title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))), "
			+ "total_count AS (SELECT COUNT(*) AS total FROM todo WHERE user_id = :id "
			+ "AND (:f IS NULL OR title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))) "
			+ "SELECT ft.*, tc.total FROM filtered_todos ft CROSS JOIN total_count tc "
			+ "ORDER BY ft.title ASC LIMIT :l2 OFFSET :l1;";

	public static final String GET_ALL_FILTERED_AND_SORTED_TODOS_DESC = "WITH filtered_todos AS (SELECT * FROM todo WHERE user_id = :id "
			+ "AND (:f IS NULL OR title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))), "
			+ "total_count AS (SELECT COUNT(*) AS total FROM todo WHERE user_id = :id "
			+ "AND (:f IS NULL OR title LIKE CONCAT('%', :f, '%') OR description LIKE CONCAT('%', :f, '%'))) "
			+ "SELECT ft.*, tc.total FROM filtered_todos ft CROSS JOIN total_count tc "
			+ "ORDER BY ft.title DESC LIMIT :l2 OFFSET :l1;";
}
