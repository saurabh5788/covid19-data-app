package com.ssingh.covid19.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	@Bean
	public Queue queue(
			@Value("${msg.queue.update-case-count}") String updatecountQueue) {
		return new Queue(updatecountQueue, true);
	}
}
