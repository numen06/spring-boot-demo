package com.demo.ws.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableStompBrokerRelay("/topic", "/queue").setRelayHost("192.168.1.6").setRelayPort(61613).setSystemHeartbeatSendInterval(20000)
			.setSystemHeartbeatReceiveInterval(20000).setSystemLogin("admin").setSystemPasscode("password").setClientLogin("admin").setClientPasscode("password");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/hello").withSockJS();
	}

	public class MyHandler extends TextWebSocketHandler {
		@Override
		public void handleTextMessage(WebSocketSession session, TextMessage message) {
			System.err.println(message.getPayload());
		}
	}

	@Bean
	public WebSocketHandler myHandler() {
		return new MyHandler();
	}

}