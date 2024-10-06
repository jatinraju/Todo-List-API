package com.todo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.todo.constants.ErrorCodeEnum;
import com.todo.constants.TokenExpirationTime;
import com.todo.exception.JwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {

	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateAccessToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return createAccessToken(claims, email, TokenExpirationTime.ACCESS_TOKEN_EXPIRATION_TIME);
	}

	public String generateRefreshToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return createAccessToken(claims, email, TokenExpirationTime.REFRESH_TOKEN_EXPIRATION_TIME);
	}

	private String createAccessToken(Map<String, Object> claims, String subject, long time) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + time)).signWith(key).compact();
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new JwtTokenException(ErrorCodeEnum.JWT_TOKEN_EXPIRED.getCode(),
					ErrorCodeEnum.JWT_TOKEN_EXPIRED.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (SignatureException e) {
			throw new JwtTokenException(ErrorCodeEnum.JWT_INVALID_SIGNATURE.getCode(),
					ErrorCodeEnum.JWT_INVALID_SIGNATURE.getMessage(), HttpStatus.OK);
		} catch (MalformedJwtException e) {
			throw new JwtTokenException(ErrorCodeEnum.JWT_MALFORMED_TOKEN.getCode(),
					ErrorCodeEnum.JWT_MALFORMED_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			throw new JwtTokenException(ErrorCodeEnum.JWT_INVALID_TOKEN.getCode(),
					ErrorCodeEnum.JWT_INVALID_TOKEN.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	public boolean validateToken(String token, String email) {
		try {
			final String extractedEmail = extractEmail(token);
			return (extractedEmail.equals(email) && !isTokenExpired(token));
		} catch (JwtTokenException e) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
