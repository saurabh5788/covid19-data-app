package com.ssingh.covid19.config;

import java.math.BigInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssingh.covid19.dto.StateDetailDTO;

@Configuration
public class JMSConfig {

	@Bean
	public Queue queue(
			@Value("${msg.queue.updatecount}") String updatecountQueue) {
		return new Queue(updatecountQueue, true);
	}

	@Bean
	public Object testJMS(Queue queue, RabbitTemplate jmsTemplate) {
		StateDetailDTO state = new StateDetailDTO();
		state.setStateCode("HR");
		state.setStateName("Haryana");
		state.setStatePopulation(BigInteger.valueOf(6876768));
		jmsTemplate.convertAndSend(queue.getName(), state);
		return new Object();
	}
}
