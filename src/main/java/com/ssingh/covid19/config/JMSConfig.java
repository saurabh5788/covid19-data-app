package com.ssingh.covid19.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class JMSConfig {
	@Bean
	public Object testJMS(JmsTemplate jmsTemplate){
		jmsTemplate.convertAndSend("jms.message.endpoint.update", "Test Message.");
		return new Object();
	}
}
