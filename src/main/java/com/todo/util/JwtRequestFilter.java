package com.todo.util;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.exception.JwtTokenException;
import com.todo.pojo.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		super();
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			final String authorizationHeader = request.getHeader("Authorization");

			String email = null;
			String jwt = null;
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
				email = jwtUtil.extractEmail(jwt);
			}

			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				if (jwtUtil.validateToken(jwt, email)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			chain.doFilter(request, response);
		} catch (JwtTokenException e) {
			response.setStatus(e.getHttpStatusCode().value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(new ObjectMapper().writeValueAsString(
					ErrorResponse.builder().errorCode(e.getErrorCode()).errorMessage(e.getErrorMessage()).build()));
		}
	}
}
