package com.demo.mqtt.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

@Configuration
@AutoConfigureAfter(MqttFactoryAuto.class)
public class MqttProvider {

	@Value("${mqtt.provider.topic}")
	private String topic;
	@Value("${mqtt.provider.clientId}")
	private String clientId;

	@Autowired
	private MqttPahoClientFactory client;

	@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
	public interface MessageGateway {
		@Gateway(replyTimeout = 1, requestTimeout = 30)
		void sendToMqtt(String data) throws Exception;

		void sendDateToMqtt(Date data);

		void sendGreeting(Greeting data);
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, client);
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(topic);
		messageHandler.setDefaultQos(2);
		return messageHandler;
	}

}
