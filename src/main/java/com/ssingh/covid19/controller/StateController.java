package com.ssingh.covid19.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssingh.covid19.annotation.ApiRestEndpoint;
import com.ssingh.covid19.annotation.ValidStateCode;
import com.ssingh.covid19.dto.StateDTO;
import com.ssingh.covid19.dto.StateWrapperDTO;
import com.ssingh.covid19.service.StateService;

/**
 * API Controller for State Details.
 * 
 * @author Saurabh Singh
 */
@ApiRestEndpoint("/state")
public class StateController {
	private StateService stateService;

	@Autowired
	public StateController(StateService stateService) {
		this.stateService = stateService;
	}

	@Autowired
	private ObjectMapper mapper;

	@GetMapping
	public ResponseEntity<StateWrapperDTO> getAllStates() {
		List<StateDTO> stateList = stateService.fetchAllStates();
		StateWrapperDTO states = new StateWrapperDTO();
		states.setStateList(stateList);
		ResponseEntity<StateWrapperDTO> response = ResponseEntity.ok(states);
		return response;
	}

	@GetMapping(value = "/{code}")
	public ResponseEntity<StateDTO> fetchState(
			@ValidStateCode @PathVariable("code") String code) {
		StateDTO state = stateService.fetchState(code);
		ResponseEntity<StateDTO> response = ResponseEntity.ok(state);
		return response;
	}
}
