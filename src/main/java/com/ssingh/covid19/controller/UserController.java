package com.ssingh.covid19.controller;


import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssingh.covid19.annotation.ApiRestEndpoint;
import com.ssingh.covid19.component.JWTHelper;
import com.ssingh.covid19.dto.JWTResponseDTO;
import com.ssingh.covid19.dto.UserDTO;
import com.ssingh.covid19.service.UserDetailsServiceImpl;

@ApiRestEndpoint("/user")
@Validated
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JWTHelper jwtHelper;
	
	@PostMapping(value = { "/add", "/add/" }, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserDTO> addNewUser(
			@Valid @RequestBody UserDTO userDto) {
		userDto = userDetailsService.addNewUser(userDto);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping(value = { "/jwt", "/jwt/" }, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<JWTResponseDTO> createToken(@Valid @RequestBody UserDTO userDto) {
		LOGGER.debug("Authenticating User : {}", userDto.getUsername());
		Authentication authentication = authenticate(userDto.getUsername(), userDto.getPassword());
		LOGGER.debug(ToStringBuilder.reflectionToString(authentication,
				ToStringStyle.JSON_STYLE));

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(userDto.getUsername());

		final String token = jwtHelper.generateToken(userDetails);
		return ResponseEntity.ok(new JWTResponseDTO(token));
	}
	
	@GetMapping(value = "/details")
	public ResponseEntity<UserDTO> fetchDetails(Authentication authentication) {
		if(authentication == null){
			throw new AuthenticationServiceException("No Authentication configured.");
		}
		UserDTO userDto = userDetailsService
				.fetchUserByUsername(authentication.getName());		
		return ResponseEntity.ok(userDto);		
	}

	private Authentication authenticate(String username, String password) {
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username,
						password));

	}
}
