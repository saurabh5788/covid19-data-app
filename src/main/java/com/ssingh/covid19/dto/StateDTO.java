package com.ssingh.covid19.dto;

import java.math.BigInteger;


public class StateDTO extends AbstractDTO {
	private static final long serialVersionUID = -8341212091161470591L;
	private String stateCode;
	private String stateName;
	private BigInteger population;

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

	public BigInteger getPopulation() {
		return population;
	}

	public void setPopulation(BigInteger population) {
		this.population = population;
	}	
}
