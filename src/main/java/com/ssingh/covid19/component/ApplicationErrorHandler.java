package com.ssingh.covid19.component;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ssingh.covid19.dto.ApplicationErrorDTO;
import com.ssingh.covid19.exception.NoElementFoundException;

/**
 * @author Saurabh Singh
 */
@ControllerAdvice
public class ApplicationErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Bad Request.");
		List<ObjectError> errorList = ex.getAllErrors();
		for (ObjectError error : errorList) {
			errorDTO.addError(error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@Override
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleExceptionInternal(Exception ex,
			Object body, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown Error.");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				errorDTO);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleConstraintViolationException(
			ConstraintViolationException e) {
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.BAD_REQUEST.value(), "Validation Errors.");
		Set<ConstraintViolation<?>> constraintViolations = e
				.getConstraintViolations();
		for (ConstraintViolation<?> violation : constraintViolations) {
			errorDTO.addError(violation.getMessage());
		}
		return ResponseEntity.badRequest().body(errorDTO);
	}

	@ExceptionHandler(NoElementFoundException.class)
	public ResponseEntity<Object> handleNotFoundErrors(NoElementFoundException e) {
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.NOT_FOUND.value(), "Element Not Found.");
		errorDTO.addError(e.getReason());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
	}

	@ExceptionHandler({ RuntimeException.class, Exception.class,
			Throwable.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unknown Error.")
	public ResponseEntity<Object> handleAllErrors(Throwable e) {
		ApplicationErrorDTO errorDTO = new ApplicationErrorDTO(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown Error.");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				errorDTO);
	}
}
