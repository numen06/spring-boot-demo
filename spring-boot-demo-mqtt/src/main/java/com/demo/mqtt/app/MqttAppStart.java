package com.demo.mqtt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@IntegrationComponentScan
@EnableScheduling
public class MqttAppStart {

	public static void main(String[] args) {
		SpringApplication.run(MqttAppStart.class, args);
	}

}
