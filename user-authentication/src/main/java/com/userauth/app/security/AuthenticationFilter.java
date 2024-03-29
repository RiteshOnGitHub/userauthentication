package com.userauth.app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userauth.app.SpringApplicationContext;
import com.userauth.app.dto.UserDto;
import com.userauth.app.requestmodel.UserLoginRequestModel;
import com.userauth.app.service.UserService;

/*this class is for authenticate the user based on credentials.*/
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		try {
			UserLoginRequestModel creds = new ObjectMapper().readValue(
					req.getInputStream(), UserLoginRequestModel.class);

			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(creds
							.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/* this method will get called if username and password are correct. */
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain, Authentication auth)
			throws IOException, ServletException {
		String userName = ((User) auth.getPrincipal()).getUsername();

		String token = Jwts
				.builder()
				.setSubject(userName)
				.setExpiration(
						new Date(System.currentTimeMillis()
								+ SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512,
						SecurityConstants.getTokenSecret()).compact();

		UserService userService = (UserService) SpringApplicationContext
				.getBean("userServiceImpl");
		UserDto userDto = userService.getUser(userName);

		res.addHeader(SecurityConstants.HEADER_STRING,
				SecurityConstants.TOKEN_PREFIX + token);
		res.addHeader("userID", userDto.getUserId());
	}

	
	

}
