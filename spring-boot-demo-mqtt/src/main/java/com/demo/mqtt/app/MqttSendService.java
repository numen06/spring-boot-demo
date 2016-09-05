package com.demo.mqtt.app;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.demo.mqtt.app.MqttProvider.MessageGateway;

@Service
public class MqttSendService {
	@Autowired
	private MessageGateway gateway;

	@Value("${runtime:1}")
	private Integer runtime;

	@Scheduled(cron = "${scheduled:0/1 * * * * ?}")
	public void init() {
		try {
			gateway.sendToMqtt(UUID.randomUUID().toString());
			// Thread.sleep(1000);
			for (int i = 0; i < runtime; i++) {
				gateway.sendToMqtt(JSON.toJSONString(new Greeting(System.currentTimeMillis() + "天才张" + "[" + i + "]")));
			}
		} catch (MessageDeliveryException e) {
			System.err.println("发送异常");
		} catch (Exception e) {
			System.err.println("没有消费者");
		}
	}

}
