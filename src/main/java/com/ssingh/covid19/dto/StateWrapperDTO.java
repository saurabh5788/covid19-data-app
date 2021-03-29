package com.ssingh.covid19.dto;

import java.util.List;

public class StateWrapperDTO extends AbstractDTO {	
	private static final long serialVersionUID = 8421655280760223050L;	
	private List<StateDTO> stateList;

	public List<StateDTO> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateDTO> stateList) {
		this.stateList = stateList;
	}
}
