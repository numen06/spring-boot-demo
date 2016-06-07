package com.wesley.conn;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainCon {

	@RequestMapping("/")
	public String index() {
		return "i am tiancai wesley";
	}

}
