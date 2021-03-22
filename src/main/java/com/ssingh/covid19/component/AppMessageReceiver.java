package com.ssingh.covid19.component;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AppMessageReceiver {
	@JmsListener(destination = "jms.message.endpoint.update")
	public void receiveUpdateMessage(String msg) {
		System.out.println("Received " + msg);
	}
}
