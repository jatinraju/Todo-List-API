package com.todo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long ACCESS_TOKEN_EXPIRATION_TIME = 10 * 1000; // 15 MINUTES
	private static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000; // 1 HOUR

	public String generateAccessToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return createAccessToken(claims, email, ACCESS_TOKEN_EXPIRATION_TIME);
	}

	public String generateRefreshToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return createAccessToken(claims, email, REFRESH_TOKEN_EXPIRATION_TIME);
	}

	private String createAccessToken(Map<String, Object> claims, String subject, long time) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + time)).signWith(key).compact();
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, String email) {
		final String extractedEmail = extractEmail(token);
		return (extractedEmail.equals(email) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
