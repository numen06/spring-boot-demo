package com.demo.mqtt.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttFactoryAuto {

	@Value("${mqtt.broker.url}")
	private String url;
	@Value("${mqtt.broker.username}")
	private String username;
	@Value("${mqtt.broker.password}")
	private String password;

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setServerURIs(url);
		factory.setUserName(username);
		factory.setPassword(password);
		factory.setConnectionTimeout(10);
		factory.setKeepAliveInterval(20);
		return factory;
	}

	@Bean(name = "mqttInputChannel")
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean(name = "mqttOutboundChannel")
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@Bean(name = "errorChannel")
	public MessageChannel errorChannel() {
		return new DirectChannel();
	}

}
