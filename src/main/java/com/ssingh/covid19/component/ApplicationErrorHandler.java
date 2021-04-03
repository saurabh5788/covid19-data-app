package com.ssingh.covid19.component;

import io.jsonwebtoken.JwtException;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.ssingh.covid19.dto.ApplicationErrorDTO;
import com.ssingh.covid19.exception.CaseServiceException;
import com.ssingh.covid19.exception.NoElementFoundException;
import com.ssingh.covid19.exception.StateServiceException;

/**
 * Application level Error Handler.
 * 
 * @author Saurabh Singh
 *
 */
@ControllerAdvice
public class ApplicationErrorHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationErrorHandler.class);

	// BAD Request
	// Validation
	// Not Found
	// Authentication (JWT)
	// Service Error

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOGGER.error(ex.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Parameter(s) missing.");
		errorDTO.addError(ex.getParameterName());
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOGGER.error(ex.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Validation Error(s).");
		List<ObjectError> errorList = ex.getAllErrors();
		for (ObjectError error : errorList) {
			errorDTO.addError(error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolationException(
			ConstraintViolationException ex) {
		ex.printStackTrace();
		LOGGER.error(ex.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Validation Error(s).");
		Set<ConstraintViolation<?>> constraintViolations = ex
				.getConstraintViolations();
		for (ConstraintViolation<?> violation : constraintViolations) {
			errorDTO.addError(violation.getMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}
	
	@ExceptionHandler(NoElementFoundException.class)
	public ResponseEntity<Object> handleNotFoundErrors(NoElementFoundException e) {
		e.printStackTrace();
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				e.getRawStatusCode(), "Element Not Found.");
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}


	@ExceptionHandler({ StateServiceException.class, CaseServiceException.class })
	public ResponseEntity<Object> handleServiceErrors(ResponseStatusException e) {
		e.printStackTrace();
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		LOGGER.error(rootCause.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				e.getRawStatusCode(), "Application Error Ocurred.");
		errorDTO.addError(e.getReason());
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}

	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Object> handleAuthenticationErrors(JwtException e) {
		LOGGER.error(e.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.UNAUTHORIZED.value(),
				"Token failed to be authorized.");
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}

	@ExceptionHandler({ RuntimeException.class, Exception.class,
			Throwable.class })
	public ResponseEntity<Object> handleAllErrors(Throwable e) {
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		LOGGER.error(rootCause.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Application Error.");
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
}
