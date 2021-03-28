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
	private BigInteger totalActiveCases;
	private BigInteger totalDeathCases;
	private BigInteger totalCureCases;
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
	public BigInteger getTotalActiveCases() {
		return totalActiveCases;
	}
	public void setTotalActiveCases(BigInteger totalActiveCases) {
		this.totalActiveCases = totalActiveCases;
	}
	public BigInteger getTotalDeathCases() {
		return totalDeathCases;
	}
	public void setTotalDeathCases(BigInteger totalDeathCases) {
		this.totalDeathCases = totalDeathCases;
	}
	public BigInteger getTotalCureCases() {
		return totalCureCases;
	}
	public void setTotalCureCases(BigInteger totalCureCases) {
		this.totalCureCases = totalCureCases;
	}
}
