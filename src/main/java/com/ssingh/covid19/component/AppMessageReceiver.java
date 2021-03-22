package com.ssingh.covid19.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ssingh.covid19.dto.StateDetailDTO;

@Component
public class AppMessageReceiver {
	@RabbitListener(queues = "${msg.queue.updatecount}")
	public void receiveUpdateMessage(StateDetailDTO msg) {
		System.out.println("Received " + msg);
	}
}
