package com.demo.wechat.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.oauth.OAuthException;
import org.sword.wechat4j.oauth.OAuthManager;
import org.sword.wechat4j.oauth.protocol.get_access_token.GetAccessTokenRequest;
import org.sword.wechat4j.oauth.protocol.get_access_token.GetAccessTokenResponse;

import com.demo.wechat.service.MyWechat;

@RestController
@RequestMapping("/wechat")
public class WeChatRestApi {

	private static Logger logger = Logger.getLogger(WeChatRestApi.class);

	@RequestMapping("")
	public void wechat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MyWechat lejian = new MyWechat(request);
		String result = lejian.execute();
		response.getOutputStream().write(result.getBytes());
	}

	@RequestMapping("/auth2")
	public String auth2(String code, String state) throws ServletException, IOException, OAuthException {
		logger.info("微信授权成功" + code + "," + state);
		GetAccessTokenResponse getAccessTokenResponse = OAuthManager.getAccessToken(new GetAccessTokenRequest(code));
		CustomerMsg customerMsg = new CustomerMsg(getAccessTokenResponse.getOpenid());
		customerMsg.sendText("微信授权成功");
		System.out.println(getAccessTokenResponse);
		return "微信授权成功";
	}
}
