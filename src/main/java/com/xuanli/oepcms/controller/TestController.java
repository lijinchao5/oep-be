/**
 * @fileName:  TestController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:10:57
 */
package com.xuanli.oepcms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.websocket.StudentWebSocketServer;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author QiaoYu
 */
@RestController
@RequestMapping(value = "/test/")
public class TestController {
	@Autowired
	StudentWebSocketServer studentWebSocketServer;

	@ApiIgnore
	@RequestMapping(value = "testWebSocket.do")
	public String testwebSocket() {
		try {
			studentWebSocketServer.sendInfo("群体发送消息", null);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
