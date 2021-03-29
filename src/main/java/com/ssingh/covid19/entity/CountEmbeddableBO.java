package com.ssingh.covid19.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CountEmbeddableBO {
	@Column(name = "active_cases")
	private long activeCases;
	@Column(name = "death_cases")
	private long deathCases;
	@Column(name = "recovered_cases")
	private long recoveredCases;
	
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
