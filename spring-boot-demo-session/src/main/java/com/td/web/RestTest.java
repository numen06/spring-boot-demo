package com.td.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTest {

	@RequestMapping("/test")
	public String login(HttpSession session) {
		Object name = session.getAttribute("name");
		return name.toString();
	}

	@RequestMapping("/")
	public String index(HttpSession session) {
		Object name = session.getAttribute("name");
		return name.toString();
	}
}
