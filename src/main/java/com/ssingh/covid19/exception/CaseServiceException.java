package com.ssingh.covid19.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CaseServiceException extends ResponseStatusException {

	private static final long serialVersionUID = 530873763491531686L;

	public CaseServiceException(String reason) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
	}

	public CaseServiceException(Throwable t) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "Case Service Error.", t);
	}

}
