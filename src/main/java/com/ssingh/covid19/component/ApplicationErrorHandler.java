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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Bad Request.");
		List<ObjectError> errorList = ex.getAllErrors();
		for (ObjectError error : errorList) {
			errorDTO.addError(error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex,
			Object body, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		ex.printStackTrace();
		return handleAllErrors(ex);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(
			ConstraintViolationException e) {
		e.printStackTrace();
		LOGGER.error(e.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Errors.");
		Set<ConstraintViolation<?>> constraintViolations = e
				.getConstraintViolations();
		for (ConstraintViolation<?> violation : constraintViolations) {
			errorDTO.addError(violation.getMessage());
		}
		return ResponseEntity.unprocessableEntity().body(errorDTO);
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

	@ExceptionHandler(NoElementFoundException.class)
	public ResponseEntity<Object> handleNotFoundErrors(NoElementFoundException e) {
		e.printStackTrace();
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				e.getRawStatusCode(), "Element Not Found.");
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Object> handleAuthenticationErrors(JwtException e) {
		LOGGER.error(e.getMessage());
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.UNAUTHORIZED.value(), "Token failed to be authorized.");
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
