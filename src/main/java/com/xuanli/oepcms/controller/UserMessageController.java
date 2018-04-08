/**
 * @fileName:  UserMessageController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月8日 下午9:41:40
 */ 
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.UserMessageEntity;
import com.xuanli.oepcms.service.UserMessageEntityService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@RestController
@RequestMapping(value = "/message/")
public class UserMessageController extends BaseController{
	@Autowired
	UserMessageEntityService userMessageEntityService;
	
	@RequestMapping(value = "getUserMessage.do", method = RequestMethod.POST)
	public RestResult<List<UserMessageEntity>> getUserMessage() {
		Long userId = getCurrentUser().getId();
		List<UserMessageEntity> userMessageEntities = userMessageEntityService.getUserMessageByUser(userId);
		userMessageEntityService.deleteMsgByUser(userId);
		return ok(userMessageEntities);
	}
}
