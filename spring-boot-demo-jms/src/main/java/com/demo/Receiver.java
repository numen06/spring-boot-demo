package com.demo;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

@Component
public class Receiver {
	/**
	 * When you receive a message, print it out, then shut down the application.
	 * Finally, clean up any ActiveMQ server stuff.
	 */
	@JmsListener(destination = "test/", containerFactory = "myJmsContainerFactory")
	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
	}
}