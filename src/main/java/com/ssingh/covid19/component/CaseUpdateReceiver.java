package com.ssingh.covid19.component;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ssingh.covid19.dto.CaseDTO;
import com.ssingh.covid19.service.CaseService;

@Component
public class CaseUpdateReceiver {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseUpdateReceiver.class);
	private CaseService caseService;
	public CaseUpdateReceiver(CaseService caseService){
		this.caseService = caseService;
	}
	
	@RabbitListener(queues = "${msg.queue.update-case-count}")
	public void receiveUpdateCaseMessage(@Valid CaseDTO newStateCase) {
		LOGGER.debug("New Update Received : {}",newStateCase.toString());
		CaseDTO updatedStateCase = null;
		updatedStateCase = caseService.updateStateCase(newStateCase);
		LOGGER.info("Cases updated.");
		LOGGER.debug(updatedStateCase.toString());
	}
}
