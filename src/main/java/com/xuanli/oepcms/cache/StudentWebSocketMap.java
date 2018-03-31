/**
 * 
 */
package com.xuanli.oepcms.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author lijinchao
 * @date 2018年3月30日 下午4:53:46
 */
@Component
@Scope("singleton")
public class StudentWebSocketMap {
	public Map<String, WebSocketSession> studentWebSocketMap = new HashMap<String, WebSocketSession>();

	public Map<String, WebSocketSession> getStudentWebSocketMap() {
		return studentWebSocketMap;
	}

	public void setStudentWebSocketMap(Map<String, WebSocketSession> studentWebSocketMap) {
		this.studentWebSocketMap = studentWebSocketMap;
	}
}
