/**
 * 
 */
package com.xuanli.oepcms.activemq.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.activemq.bean.ActivemqMsgBean;
import com.xuanli.oepcms.activemq.publish.MQPublisherServer;

/**
 * @author lijinchao
 * @date 2018年3月29日 上午10:55:45
 */
@Service
public class StudentMqService {
	@Autowired
	MQPublisherServer mQPublisherServer;

	public void sendMsg(ActivemqMsgBean activemqMsgBean) {
		mQPublisherServer.publish("student.jydenglish.topic", JSONObject.toJSONString(activemqMsgBean));
	}
}
