package com.ssingh.covid19.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ssingh.covid19.annotation.ValidStateCode;
import com.ssingh.covid19.dto.StateDTO;
import com.ssingh.covid19.entity.StateBO;
import com.ssingh.covid19.exception.NoElementFoundException;
import com.ssingh.covid19.exception.StateServiceException;
import com.ssingh.covid19.repository.StateRepository;

@Service
@CacheConfig(cacheNames = "state")
@Validated
public class StateService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StateService.class);

	private StateRepository stateRepository;

	@Autowired
	public StateService(StateRepository stateRepository) {
		this.stateRepository = stateRepository;
	}

	@Cacheable(value = "states")
	public List<StateDTO> fetchAllStates() {
		LOGGER.info("Fetching States");
		List<StateBO> stateBOList = stateRepository.findAll();
		List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
		try {
			for (StateBO stateBO : stateBOList) {
				StateDTO stateDTO = new StateDTO();
				BeanUtils.copyProperties(stateDTO, stateBO);
				stateDTOList.add(stateDTO);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new StateServiceException(e);
		}
		LOGGER.debug(stateDTOList.toString());
		return stateDTOList;
	}

	@Cacheable(value = "state", key = "#code")
	public StateDTO fetchState(
			@ValidStateCode String code) {
		LOGGER.info("Fetching State");
		LOGGER.debug("Fetching State : {}", code);
		code = StringUtils.lowerCase(code);
		Optional<StateBO> stateOp = stateRepository.findByStateCode(code);

		if (!stateOp.isPresent()) {
			throw new NoElementFoundException(
					"No State Details found for State Code : " + code);
		}

		StateBO state = stateOp.get();
		StateDTO stateDTO = new StateDTO();
		try {
			BeanUtils.copyProperties(stateDTO, state);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new StateServiceException(e);
		}

		LOGGER.debug(stateDTO.toString());
		return stateDTO;
	}
}
