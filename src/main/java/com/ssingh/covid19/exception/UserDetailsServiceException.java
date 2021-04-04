package com.ssingh.covid19.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserDetailsServiceException extends ResponseStatusException {

	private static final long serialVersionUID = 530873763491531686L;

	public UserDetailsServiceException(String reason) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
	}

	public UserDetailsServiceException(Throwable t) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "User Service Error.", t);
	}
}
