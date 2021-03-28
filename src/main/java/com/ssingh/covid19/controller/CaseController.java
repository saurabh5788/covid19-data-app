package com.ssingh.covid19.controller;

import java.math.BigInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import com.ssingh.covid19.annotation.ApiRestController;
import com.ssingh.covid19.dto.StateDetailDTO;

@ApiRestController("/case")
public class CaseController {
	
	private RabbitTemplate jmsTemplate;
	private Queue queue;
	
	public CaseController(RabbitTemplate jmsTemplate, Queue queue){
		this.jmsTemplate = jmsTemplate;
		this.queue = queue;
	}
	
	@GetMapping(value = "/dummy")
	public void getAllStates() {
		StateDetailDTO state = new StateDetailDTO();
		state.setStateCode("HR");
		state.setStateName("Haryana");
		state.setStatePopulation(BigInteger.valueOf(6876768));
		jmsTemplate.convertAndSend(queue.getName(), state);
	}
}
