package com.demo.ws.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsAppStart {

	public static void main(String[] args) {
		Date d = new Date(1473048539912l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// public final String format(Date date)
		String s = sdf.format(d);
		System.out.println(s);
		SpringApplication.run(WsAppStart.class, args);
	}

}
