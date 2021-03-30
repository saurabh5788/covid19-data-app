package com.ssingh.covid19.service;

import java.util.List;

import com.ssingh.covid19.dto.StateDTO;

public interface StateService {

	List<StateDTO> fetchAllStates();

	StateDTO fetchState(String code);

}
