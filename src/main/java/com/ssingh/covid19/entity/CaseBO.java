package com.ssingh.covid19.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "case_table")
public class CaseBO {
	@Id
	@GeneratedValue
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Embedded
	private CountEmbeddableBO caseCount;

	@OneToOne(optional = false)
	@JoinColumn(name = "state_fk_id", nullable = false, updatable = false, unique = true)
	private StateBO state;

	public StateBO getState() {
		return state;
	}

	public void setState(StateBO state) {
		this.state = state;
	}

	public CountEmbeddableBO getCaseCount() {
		return caseCount;
	}

	public void setCaseCount(CountEmbeddableBO caseCount) {
		this.caseCount = caseCount;
	}
}
