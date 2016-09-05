package com.demo.ws.app;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

@Controller
public class GreetingController {
	private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		final Greeting greeting = new Greeting("Hello, " + message.getName() + "!");
		// jmsSender.convertAndSend("minion", greeting);
		log.info("input msg:" + message.getName());
		return greeting;
	}

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

//	@PostConstruct
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