package com.ssingh.covid19.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.Size;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ssingh.covid19.dto.StateDetailDTO;
import com.ssingh.covid19.exception.NoElementFoundException;

@Service
@CacheConfig(cacheNames = { "state" })
@Validated
public class StateService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StateService.class);

	@Cacheable(value = "states")
	public List<StateDetailDTO> getAllStates() {
		List<StateDetailDTO> stateList = new ArrayList<StateDetailDTO>();

		StateDetailDTO state = new StateDetailDTO();
		state.setStateCode("HR");
		state.setStateName("Haryana");
		state.setStatePopulation(BigInteger.valueOf(6876768));
		stateList.add(state);

		state = new StateDetailDTO();
		state.setStateCode("DL");
		state.setStateName("Delhi");
		state.setStatePopulation(BigInteger.valueOf(6876768));
		stateList.add(state);

		state = new StateDetailDTO();
		state.setStateCode("UP");
		state.setStateName("Uttar Pradesh");
		state.setStatePopulation(BigInteger.valueOf(6876768));
		stateList.add(state);

		return stateList;
	}

	@Cacheable(value = "state", key = "#code")
	public StateDetailDTO getState(@Size(max = 2, min = 2) String code) {
		LOGGER.debug("Fetching State : {}", code);
		List<StateDetailDTO> allStates = getAllStates();
		
		Map<String, StateDetailDTO> stateMap = new HashMap<>();
		MapUtils.populateMap(stateMap, allStates, StateDetailDTO::getStateCode);
		
		StateDetailDTO state = stateMap.get(code);
		if (Objects.isNull(state)) {
			throw new NoElementFoundException("No State found for Code : "
					+ code);
		}
		LOGGER.debug(state.toString());
		return state;
	}
}
