package com.ssingh.covid19.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StateServiceException extends ResponseStatusException {

	private static final long serialVersionUID = 530873763491531686L;

	public StateServiceException(String reason) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
	}

	public StateServiceException(Throwable t) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "State Service Error.", t);
	}

}
