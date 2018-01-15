/**
 * @fileName:  MobileMessageService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午4:02:15
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UserEntity;

/**
 * @author QiaoYu
 */
@Service
public class MobileMessageService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	UserService userService;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午4:22:05
	 */
	public String registMsg(String mobile, String randomNum) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		List<UserEntity> userEntities = userService.selectUserEntity(userEntity);
		if (null != userEntities && userEntities.size() <= 0) {
			// 查找短信模板
			// 发送短信代码
			logger.debug("给手机号码:" + mobile + "发送短信,验证码为:" + randomNum);
			return "1";
		} else {
			// 手机号码已经存在
			return "2";
		}
	}

}
