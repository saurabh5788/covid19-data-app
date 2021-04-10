package com.ssingh.covid19.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("state")
public class StateDTO extends AbstractDTO {
	private static final long serialVersionUID = -8341212091161470591L;
	private String stateCode;
	private String stateName;
	private long population;

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

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}	
}
