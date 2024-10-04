package com.todo.pojo.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
	private String email;
	private String access_token;
	private String refresh_token;
}
