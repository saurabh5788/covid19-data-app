package com.ssingh.covid19.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public class UserDTO extends AbstractDTO {
	private static final long serialVersionUID = -8341212091161470591L;
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String username;
	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	public UserDTO(){}
	public UserDTO(String name, String username) {
		super();
		this.name = name;
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
