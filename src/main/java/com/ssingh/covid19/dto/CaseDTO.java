package com.ssingh.covid19.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("case")
public class CaseDTO extends AbstractDTO {	
	private static final long serialVersionUID = -4914360137030859040L;
	private long id;
	private String name;
	private String nationalIDNo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationalIDNo() {
		return nationalIDNo;
	}
	public void setNationalID(String nationalIDNo) {
		this.nationalIDNo = nationalIDNo;
	}
}
