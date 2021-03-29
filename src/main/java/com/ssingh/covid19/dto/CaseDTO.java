package com.ssingh.covid19.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.ssingh.covid19.annotation.ValidStateCode;

@JsonRootName("Case")
public class CaseDTO extends AbstractDTO {
	private static final long serialVersionUID = -8341212091161470591L;

	@ValidStateCode
	private String stateCode;
	private BigInteger activeCases;
	private BigInteger deathCases;
	private BigInteger recoveredCases;

	public CaseDTO() {
	}

	public CaseDTO(
			@ValidStateCode String stateCode,
			BigInteger activeCases, BigInteger deathCases,
			BigInteger recoveredCases) {
		super();
		this.stateCode = stateCode;
		this.activeCases = activeCases;
		this.deathCases = deathCases;
		this.recoveredCases = recoveredCases;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public BigInteger getActiveCases() {
		return activeCases;
	}

	public void setActiveCases(BigInteger activeCases) {
		this.activeCases = activeCases;
	}

	public BigInteger getDeathCases() {
		return deathCases;
	}

	public void setDeathCases(BigInteger deathCases) {
		this.deathCases = deathCases;
	}

	public BigInteger getRecoveredCases() {
		return recoveredCases;
	}

	public void setRecoveredCases(BigInteger recoveredCases) {
		this.recoveredCases = recoveredCases;
	}
}
