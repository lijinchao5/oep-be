/**
 * 
 */
package com.xuanli.oepcms.activemq.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.activemq.bean.ActivemqMsgBean;
import com.xuanli.oepcms.activemq.publish.MQPublisherServer;
import com.xuanli.oepcms.entity.UserMessageEntity;
import com.xuanli.oepcms.service.UserMessageEntityService;

/**
 * @author lijinchao
 * @date 2018年3月29日 上午10:55:45
 */
@Service
public class StudentMqService {
	@Autowired
	MQPublisherServer mQPublisherServer;
	@Autowired
	UserMessageEntityService userMessageEntityService;

	public void sendMsg(ActivemqMsgBean activemqMsgBean) {
		
		String users = activemqMsgBean.getUsers();
		
		List<UserMessageEntity> userMessageEntities = new ArrayList<UserMessageEntity>();
		
		String userIds[] = users.split(",");
		for (int i = 0; i < userIds.length; i++) {
			UserMessageEntity ume = new UserMessageEntity();
			ume.setText(activemqMsgBean.getMsg());
			if ("all".equals(userIds[i])) {
				continue;
			}
			ume.setUserId(Long.parseLong(userIds[i]));
			ume.setFlags(false);
			ume.setType("1");
			userMessageEntities.add(ume);
			userMessageEntityService.insertUserMessageEntity(ume);
		}
		mQPublisherServer.publish("student.jydenglish.topic", JSONObject.toJSONString(activemqMsgBean));
	}
}
