package com.ssingh.covid19.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Error")
public class ApplicationErrorDTO extends AbstractDTO {

	private static final long serialVersionUID = 1790819555078951083L;

	private int status;
	private String message;
	private List<String> errors;	

	public ApplicationErrorDTO(int status, String message) {
		this.status = status;
		this.message = message;
	}
	public int getStatus() {
		return status;
	}	
	public String getMessage() {
		return message;
	}	
	public List<String> getErrors() {
		return errors;
	}
	
	public void addError(String error){
		if(Objects.isNull(errors)){
			errors = new ArrayList<String>();
		}
		errors.add(error);
	}
}
