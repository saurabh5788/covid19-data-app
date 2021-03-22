package com.ssingh.covid19.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssingh.covid19.annotation.AppRestController;
import com.ssingh.covid19.dto.StateDetailDTO;
import com.ssingh.covid19.dto.StateWrapperDTO;
import com.ssingh.covid19.service.StateService;

@AppRestController("/state")
public class StateController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StateController.class);
	private StateService stateService;

	@Autowired
	public StateController(StateService stateService) {
		this.stateService = stateService;
	}

	@Autowired
	private ObjectMapper mapper;

	@GetMapping(value = "/list")
	public ResponseEntity<StateWrapperDTO> getAllStates() {
		Map<String, StateDetailDTO> stateMap = stateService.getAllStates();
		StateWrapperDTO states = new StateWrapperDTO();
		states.setStateMap(stateMap);
		ResponseEntity<StateWrapperDTO> response = ResponseEntity.ok(states);
		return response;

	}

	@GetMapping(value = "/{code}")
	public ResponseEntity<StateDetailDTO> getState(@PathVariable("code") String code) {
		StateDetailDTO state = stateService.getState(code);
		ResponseEntity<StateDetailDTO> response = ResponseEntity.ok(state);
		return response;
	}
}
