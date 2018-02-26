/**
 * @fileName:  StudentStudentWebSocketServer.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:04:39
 */
package com.xuanli.oepcms.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author QiaoYu
 */
@Component
@ServerEndpoint(value = "/studentWebSocketServer/{userId}")
public class StudentWebSocketServer {
	private Long userId;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static int onlineCount = 0;
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<StudentWebSocketServer> webSocketSet = new CopyOnWriteArraySet<StudentWebSocketServer>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("userId") Long userId, Session session) {
		this.session = session;
		this.userId = userId;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		logger.info("新用户[" + userId + "]加入！当前在线人数为" + getOnlineCount());
		//sendMessage("连接成功");
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("来自客户端的消息:" + message);
		for (StudentWebSocketServer item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error("websocket发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 群发自定义消息
	 */
	public void sendInfo(String message,Long[] userIds) throws IOException {
		if (null == userIds  || userIds.length<=0) {
			for (StudentWebSocketServer item : webSocketSet) {
				try {
					logger.info("发送消息[" + message + "]给[" + item.userId + "]");
					item.sendMessage(message);
				} catch (IOException e) {
					continue;
				}
			}
		}else {
			for (StudentWebSocketServer item : webSocketSet) {
				try {
					for (Long userId : userIds) {
						if (item.getUserId().longValue() == userId.longValue()) {
							logger.info("发送消息[" + message + "]给[" + item.userId + "]");
							item.sendMessage(message);
						}
					}
				} catch (IOException e) {
					continue;
				}
			}
		}
		
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		StudentWebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		StudentWebSocketServer.onlineCount--;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
