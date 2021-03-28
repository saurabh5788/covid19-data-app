package com.ssingh.covid19.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="STATE_TABLE")
public class StateBO extends AbstractBO {
	@Column(length = 2, nullable = false, unique = true, updatable = false)
	private String stateCode;
	@Column(length = 50, nullable = false, unique = true, updatable = false)
	private String stateName;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
