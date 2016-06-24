package com.demo.ws.app;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		// Thread.sleep(3000); // simulated delay
		return new Greeting("Hello, " + message.getName() + "!");
	}

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@PostConstruct
	private void init() throws InterruptedException {
		new AbstractExecutionThreadService() {
			final SimpMessagingTemplate messagingTemplate = simpMessagingTemplate;

			@Override
			protected void run() throws Exception {
				for (int i = 0; i < 50; i++) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.messagingTemplate.convertAndSend("/topic/greetings", new Greeting("Hello, " + UUID.randomUUID().toString() + "!"));
				}

			}
		}.startAsync();
	}

}