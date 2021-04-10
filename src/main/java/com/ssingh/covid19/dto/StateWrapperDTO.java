package com.ssingh.covid19.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("statelist")
public class StateWrapperDTO extends AbstractDTO {	
	private static final long serialVersionUID = 8421655280760223050L;	
	private List<StateDTO> stateList;
	private long totalPopulation;

	public List<StateDTO> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateDTO> stateList) {
		this.stateList = stateList;
	}

	public long getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(long totalPopulation) {
		this.totalPopulation = totalPopulation;
	}
}
