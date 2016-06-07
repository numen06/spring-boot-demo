package com.td.test;

import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.td.entity.TestUser;

import junit.framework.TestCase;

public class RestTempTest extends TestCase {

	private RestTemplate restTemplate = new RestTemplate();

	private final static String baseUrl = "http://127.0.0.1:9999/";

	public void testSession() {
		TestUser user = new TestUser("test", "test");
		System.out.println(restTemplate.postForObject(baseUrl + "test", user, String.class));
	}

	public void testOAuth() {
		System.out.println(
			restTemplate.postForObject("http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=acme&redirect_uri=http://example.com", null, String.class));
	}

}
