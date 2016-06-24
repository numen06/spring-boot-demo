package com.demo;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsSendServer {

	@Autowired
	private JmsTemplate jmsTemplate;

	@PostConstruct
	private void init() {
		// Send a message
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public javax.jms.Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(UUID.randomUUID().toString());
			}
		};
		System.out.println("Sending a new message.");
		jmsTemplate.send("test/", messageCreator);
	}
}
