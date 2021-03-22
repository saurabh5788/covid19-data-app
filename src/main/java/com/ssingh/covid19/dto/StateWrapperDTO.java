package com.ssingh.covid19.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("states")
public class StateWrapperDTO extends AbstractDTO {	
	private static final long serialVersionUID = 8421655280760223050L;
	
	private Map<String,StateDetailDTO> stateMap;

	@JsonProperty(value="state_list")
	public Map<String, StateDetailDTO> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<String, StateDetailDTO> stateMap) {
		this.stateMap = stateMap;
	}
}
