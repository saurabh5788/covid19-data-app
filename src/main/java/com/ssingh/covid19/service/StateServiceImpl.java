package com.ssingh.covid19.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ssingh.covid19.annotation.ValidStateCode;
import com.ssingh.covid19.dto.StateDTO;
import com.ssingh.covid19.entity.StateBO;
import com.ssingh.covid19.exception.StateServiceException;
import com.ssingh.covid19.repository.StateRepository;

@Service
@CacheConfig(cacheNames = "state")
@Validated
public class StateServiceImpl implements StateService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StateServiceImpl.class);

	private StateRepository stateRepository;

	@Autowired
	public StateServiceImpl(StateRepository stateRepository) {
		this.stateRepository = stateRepository;
	}

	@Cacheable(value = "states")
	@Override
	public List<StateDTO> fetchAllStates() {
		LOGGER.info("Fetching States");
		List<StateBO> stateBOList = stateRepository.findAll();
		List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
		stateBOList.stream().forEach((stateBO) -> {
			StateDTO stateDTO = new StateDTO();
			try {
				BeanUtils.copyProperties(stateDTO, stateBO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new StateServiceException(e);
			}
			stateDTOList.add(stateDTO);
		});
		LOGGER.debug(stateDTOList.toString());
		return stateDTOList;
	}

	@Cacheable(value = "state", key = "#code")
	@Override
	public StateDTO fetchState(@ValidStateCode String code) {
		LOGGER.info("Fetching State");
		LOGGER.debug("Fetching State : {}", code);
		code = StringUtils.lowerCase(code);
		Optional<StateBO> stateOp = stateRepository.findByStateCode(code);

		if (!stateOp.isPresent()) {
			throw new StateServiceException(
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

	@Override
	public List<StateDTO> fetchAllStates(int pageNo, int pageSize) {
		LOGGER.info("Fetching States with {} & {}", pageNo, pageSize);
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<StateBO> pagedStates = stateRepository.findAll(pageable);
		List<StateBO> stateBOList = null;
		List<StateDTO> stateDTOList = new ArrayList<StateDTO>();
		if(pagedStates.hasContent()){
			stateBOList = pagedStates.getContent();			
			stateBOList.stream().forEach((stateBO) -> {
				StateDTO stateDTO = new StateDTO();
				try {
					BeanUtils.copyProperties(stateDTO, stateBO);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new StateServiceException(e);
				}
				stateDTOList.add(stateDTO);
			});
			LOGGER.debug(stateDTOList.toString());
		}
		return stateDTOList;
	}
}
