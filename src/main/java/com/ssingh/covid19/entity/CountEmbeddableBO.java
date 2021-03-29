package com.ssingh.covid19.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CountEmbeddableBO {
	@Column(name = "active_cases")
	private BigInteger activeCases = BigInteger.ZERO;
	@Column(name = "death_cases")
	private BigInteger deathCases = BigInteger.ZERO;
	@Column(name = "recovered_cases")
	private BigInteger recoveredCases = BigInteger.ZERO;
	
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
