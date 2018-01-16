/**
 * @fileName:  SendMobileMessageService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月16日 上午9:18:09
 */ 
package com.xuanli.oepcms.iface;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/** 
 * @author  QiaoYu 
 */
@Service
public class SendMobileMessageService {
	public final Logger logger = Logger.getLogger(this.getClass());
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 上午9:21:14
	 */
	public void sendMsg(String mobile, String msg) {
		logger.info("调用发送短信服务:["+mobile+"]:"+msg);
	}
	
}
