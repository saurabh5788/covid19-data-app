package com.ssingh.covid19.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoElementFoundException extends ResponseStatusException {

	private static final long serialVersionUID = 530873763491531686L;

	public NoElementFoundException(String reason) {
		super(HttpStatus.NOT_FOUND, reason);
	}

}
