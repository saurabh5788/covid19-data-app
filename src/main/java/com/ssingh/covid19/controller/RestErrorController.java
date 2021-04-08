package com.ssingh.covid19.controller;

import io.jsonwebtoken.JwtException;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.ssingh.covid19.annotation.Loggable;
import com.ssingh.covid19.dto.ApplicationErrorDTO;
import com.ssingh.covid19.exception.CaseServiceException;
import com.ssingh.covid19.exception.StateServiceException;
import com.ssingh.covid19.exception.UserDetailsServiceException;

/**
 * Application level Error Handler.
 * 
 * @author Saurabh Singh
 *
 */
@RestControllerAdvice
public class RestErrorController extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RestErrorController.class);

	@Override
	@Loggable
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOGGER.error(ex.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Parameter(s) missing.");
		errorDTO.addError(ex.getParameterName());
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@Override
	@Loggable
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOGGER.error(ex.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Validation Error(s).");
		List<ObjectError> errorList = ex.getAllErrors();
		for (ObjectError error : errorList) {
			errorDTO.addError(error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}
	
	@Override
	@Loggable
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOGGER.error(ex.toString());
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		LOGGER.error(rootCause.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Bad Request.");
		if(rootCause instanceof UnrecognizedPropertyException){
			errorDTO.addError("Only "+((UnrecognizedPropertyException)rootCause).getKnownPropertyIds()+ " properties allowed.");
		}
		else{
			errorDTO.addError(rootCause.getMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	@Loggable
	public ResponseEntity<Object> handleConstraintViolations(
			ConstraintViolationException ex) {
		LOGGER.error(ex.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Validation Error(s).");
		Set<ConstraintViolation<?>> constraintViolations = ex
				.getConstraintViolations();
		for (ConstraintViolation<?> violation : constraintViolations) {
			errorDTO.addError(violation.getMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	@Loggable
	public ResponseEntity<Object> handleDataIntegrityViolations(
			DataIntegrityViolationException ex) {
		LOGGER.error(ex.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Bad Request.");
		errorDTO.addError("Possibly duplicate data.");
		return ResponseEntity.badRequest().body(errorDTO);
	}
	
	@ExceptionHandler({ BadCredentialsException.class })
	@Loggable
	public ResponseEntity<Object> handleBadCredentials(
			BadCredentialsException ex) {
		LOGGER.error(ex.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Bad Credentials.");
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@ExceptionHandler({ StateServiceException.class, CaseServiceException.class, UserDetailsServiceException.class })
	@Loggable
	public ResponseEntity<Object> handleServiceErrors(ResponseStatusException ex) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		LOGGER.error(rootCause.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				ex.getRawStatusCode(), "Application Service Error Ocurred.");
		errorDTO.addError(ex.getReason());
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}

	
	@ExceptionHandler(JwtException.class)
	@Loggable
	public ResponseEntity<Object> handleAuthenticationErrors(JwtException ex) {
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.UNAUTHORIZED.value(),
				"Token failed to be authorized.");
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}

	@ExceptionHandler({ RuntimeException.class, Exception.class,
			Throwable.class })
	@Loggable
	public ResponseEntity<Object> handleAllErrors(Throwable ex) {
		LOGGER.error(ex.toString());
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		LOGGER.error(rootCause.toString());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Application Error ocurred.");
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}

	
}
