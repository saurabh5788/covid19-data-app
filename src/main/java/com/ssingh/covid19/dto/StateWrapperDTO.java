package com.ssingh.covid19.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("states")
public class StateWrapperDTO extends AbstractDTO {	
	private static final long serialVersionUID = 8421655280760223050L;	
	private List<StateDetailDTO> stateList;

	@JsonProperty(value="state_list")
	public List<StateDetailDTO> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateDetailDTO> stateList) {
		this.stateList = stateList;
	}
}
