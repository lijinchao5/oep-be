/**
 * 
 */
package com.xuanli.oepcms.activemq.publish;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * @author lijinchao
 * @date 2018年3月29日 上午10:50:37
 */
@Service
public class MQPublisherServer {
	private static final Logger logger = LoggerFactory.getLogger(MQPublisherServer.class);

	@Resource
	private JmsTemplate jmsTemplate;

	public void publish(String destinationName, String message) {
		logger.info("给地址主题为" + destinationName + "发送消息:" + message);
		Destination destination = new ActiveMQTopic(destinationName);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
}
