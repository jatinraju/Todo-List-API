package com.todo.constants;

public class EndPoints {

	private EndPoints() {
		super();
	}

	// AuthController
	public static final String V1 = "/v1";
	public static final String REGISTER_USER = "/register";
	public static final String LOGIN_USER = "/login";
	public static final String REFRESH_TOKEN = "/refresh-token";
	public static final String TEST = "/test";

	// TodoController
	public static final String V1_TODO = "/v1/todo";
	public static final String UPDATE_TODO = "/{id}";
	public static final String DELETE_TODO = "/{id}";

	// PublicController (Test)
	public static final String TEST_PUBLIC = "/public";
	public static final String TEST_HELLO = "/hello";

}
