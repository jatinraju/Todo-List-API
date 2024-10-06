package com.todo.constants;

import lombok.Getter;

@Getter
public class TokenExpirationTime {

	private TokenExpirationTime() {
		super();
	}

	public static final long ACCESS_TOKEN_EXPIRATION_TIME = 10 * 1000; // 15 MINUTES
	public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000; // 1 HOUR
}
