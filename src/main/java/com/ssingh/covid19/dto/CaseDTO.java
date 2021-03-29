package com.ssingh.covid19.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.ssingh.covid19.annotation.ValidStateCode;

@JsonRootName("Case")
public class CaseDTO extends AbstractDTO {
	private static final long serialVersionUID = -8341212091161470591L;

	@ValidStateCode
	private String stateCode;
	private long activeCases;
	private long deathCases;
	private long recoveredCases;

	public CaseDTO() {
	}

	public CaseDTO(
			@ValidStateCode String stateCode,
			long activeCases, long deathCases,
			long recoveredCases) {
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

	public long getActiveCases() {
		return activeCases;
	}

	public void setActiveCases(long activeCases) {
		this.activeCases = activeCases;
	}

	public long getDeathCases() {
		return deathCases;
	}

	public void setDeathCases(long deathCases) {
		this.deathCases = deathCases;
	}

	public long getRecoveredCases() {
		return recoveredCases;
	}

	public void setRecoveredCases(long recoveredCases) {
		this.recoveredCases = recoveredCases;
	}
}
