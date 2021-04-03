package com.ssingh.covid19.service;

import java.util.Optional;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssingh.covid19.annotation.Loggable;
import com.ssingh.covid19.dto.CaseDTO;
import com.ssingh.covid19.entity.CaseBO;
import com.ssingh.covid19.entity.CountEmbeddableBO;
import com.ssingh.covid19.entity.StateBO;
import com.ssingh.covid19.exception.NoElementFoundException;
import com.ssingh.covid19.repository.CaseRepository;
import com.ssingh.covid19.repository.StateRepository;

@Service
public class CaseServiceImpl implements CaseService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StateService.class);

	private AmqpTemplate jmsTemplate;
	private Queue stateCaseUpdateQueue;
	private CaseRepository caseRepository;
	private StateRepository stateRepository;

	@Autowired
	public CaseServiceImpl(AmqpTemplate jmsTemplate, Queue stateCaseUpdateQueue,
			CaseRepository caseRepository, StateRepository stateRepository) {
		this.jmsTemplate = jmsTemplate;
		this.stateCaseUpdateQueue = stateCaseUpdateQueue;
		this.caseRepository = caseRepository;
		this.stateRepository = stateRepository;
	}

	@Loggable
	@Override
	public boolean addNewStateUpdate(CaseDTO caseDto) {
		LOGGER.debug("Sending Mesage : {}", caseDto);
		boolean sentStatus = false;
		jmsTemplate.convertAndSend(stateCaseUpdateQueue.getName(), caseDto);
		sentStatus = true;
		return sentStatus;
	}

	@Loggable
	@Override
	public CaseDTO fetchStateCase(String code) {
		LOGGER.info("Fetching State Case");
		LOGGER.debug("Fetching State Case : {}", code);
		code = StringUtils.lowerCase(code);
		Optional<CaseBO> stateCaseBOOp = caseRepository
				.findByStateStateCode(code);
		if (!stateCaseBOOp.isPresent()) {
			throw new NoElementFoundException(
					"Either State Code is invalid or no Case details present for this State : "
							+ code);
		}
		LOGGER.debug("State Case : {}", stateCaseBOOp.get());
		CaseBO fetchedCaseBO = stateCaseBOOp.get();
		CaseDTO stateCaseDto = new CaseDTO();
		stateCaseDto.setActiveCases(fetchedCaseBO.getCaseCount()
				.getActiveCases());
		stateCaseDto
				.setDeathCases(fetchedCaseBO.getCaseCount().getDeathCases());
		stateCaseDto.setRecoveredCases(fetchedCaseBO.getCaseCount()
				.getRecoveredCases());
		stateCaseDto.setStateCode(code);
		return stateCaseDto;
	}

	@Loggable
	@Override
	public CaseDTO updateStateCase(CaseDTO newStateCase) {
		Optional<StateBO> stateOp = stateRepository
				.findByStateCode(newStateCase.getStateCode());
		if (!stateOp.isPresent()) {
			throw new ValidationException("Invalid State Code : "
					+ newStateCase.getStateCode());
		}

		Optional<CaseBO> caseBOOp = caseRepository
				.findByStateStateCode(newStateCase.getStateCode());

		CaseBO caseBO = null;
		CountEmbeddableBO countEmbeddableBO = null;

		if (caseBOOp.isPresent()) {
			caseBO = caseBOOp.get();
			countEmbeddableBO = caseBO.getCaseCount();
		} else {
			caseBO = new CaseBO();
			countEmbeddableBO = new CountEmbeddableBO();
			caseBO.setState(stateOp.get());
			caseBO.setCaseCount(countEmbeddableBO);
		}
		countEmbeddableBO.setActiveCases(countEmbeddableBO.getActiveCases()
				+ newStateCase.getActiveCases());
		countEmbeddableBO.setDeathCases(countEmbeddableBO.getDeathCases()
				+ newStateCase.getDeathCases());
		countEmbeddableBO.setRecoveredCases(countEmbeddableBO
				.getRecoveredCases() + newStateCase.getRecoveredCases());

		LOGGER.debug("Persisting updated case status for State Code : {}",
				caseBO.getState().getStateCode());
		caseBO = caseRepository.save(caseBO);

		CaseDTO updatedCaseDTO = new CaseDTO();
		updatedCaseDTO.setStateCode(caseBO.getState().getStateCode());
		updatedCaseDTO.setActiveCases(caseBO.getCaseCount().getActiveCases());
		updatedCaseDTO.setDeathCases(caseBO.getCaseCount().getDeathCases());
		updatedCaseDTO.setRecoveredCases(caseBO.getCaseCount()
				.getRecoveredCases());

		return updatedCaseDTO;
	}
}
