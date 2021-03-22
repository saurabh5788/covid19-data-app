package com.ssingh.covid19.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@JsonRootName("state")
public class StateDetailDTO extends AbstractDTO {
	private static final long serialVersionUID = 6705446403154362320L;
	@JsonIgnore
	private String stateCode;
	private String stateName;
	private BigInteger statePopulation;
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
	public BigInteger getStatePopulation() {
		return statePopulation;
	}
	public void setStatePopulation(BigInteger statePopulation) {
		this.statePopulation = statePopulation;
	}
}
