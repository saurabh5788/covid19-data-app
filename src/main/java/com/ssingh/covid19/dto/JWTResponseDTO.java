package com.ssingh.covid19.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("jwt")
public class JWTResponseDTO implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String token;

	public JWTResponseDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
