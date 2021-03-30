package com.ssingh.covid19.controller;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssingh.covid19.annotation.ApiRestEndpoint;
import com.ssingh.covid19.component.JWTHelper;
import com.ssingh.covid19.dto.JWTResponseDTO;

@ApiRestEndpoint("/jwt")
@Validated
public class JWTController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JWTController.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTHelper jwtHelper;

	@GetMapping(value = "/token")
	public ResponseEntity<JWTResponseDTO> updateStateCase(
			@NotBlank @RequestHeader("username") String userName,
			@NotBlank @RequestHeader("password") String password) {
		LOGGER.debug("Authenticating User : {}", userName);
		Authentication authentication = authenticate(userName, password);
		LOGGER.debug(ToStringBuilder.reflectionToString(authentication,
				ToStringStyle.JSON_STYLE));

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(userName);

		final String token = jwtHelper.generateToken(userDetails);
		return ResponseEntity.ok(new JWTResponseDTO(token));
	}

	private Authentication authenticate(String username, String password) {
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username,
						password));

	}
}
