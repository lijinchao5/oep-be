/**
 * @fileName:  MobileMessage.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午3:48:51
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.service.MobileMessageService;
import com.xuanli.oepcms.util.RanNumUtil;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController
@RequestMapping(value = "/mobileMessage")
public class MobileMessageController extends BaseController {
	@Autowired
	MobileMessageService mobileMessageService;
	
	/**
	 * @Description:  TODO 注册
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午3:56:32
	 */
	@RequestMapping(value = "/registMsg")
	public RestResult<String> registMsg(String mobile,String randomStr){
		if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(getMobileRandomNum())) {
			if (!StringUtil.isMobile(mobile)) {
				return failed(9003, "手机号码错误.");
			}
			try {
				String randomNum = RanNumUtil.createRandomNum(6);
				String result = mobileMessageService.registMsg(mobile,randomNum);
				if (StringUtil.isEmpty(result) || result.equals("1")) {
					//发送短信成功
					SessionUtil.setMobileMessageRandomNum(request, randomNum);
					return ok("发送短信成功!");
				}else if(result.equals("2")) {
					//手机号码已经存在
					return failed(9003, "手机号码已经存在.");
				}else {
					return failed(99999, "发送短信未知错误.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("发送短信异常.",e);
				return failed(9002, "发送短信异常.");
			}
		}else {
			logger.error("发送短信--->验证码错误.");
			return failed(9001, "验证码错误.");
		}
	}
	/**
	 * @Description:  TODO 忘记密码
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午3:56:23
	 */
	@RequestMapping(value = "/forgetPassword")
	public RestResult<String> forgetPassword(String mobile,String randomStr){
		
		
		return null;
	}
	/**
	 * @Description:  TODO 使用手机号登陆
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午3:56:14
	 */
	@RequestMapping(value = "/loginMsg")
	public RestResult<String> loginMsg(String mobile,String randomStr){
		
		
		return null;
	}
	
	/**
	 * @Description:  TODO 私有方法,验证手机图片验证码
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午4:13:15
	 */
	private String getMobileRandomNum() {
		Object obj = SessionUtil.getMobileRandomNum(request);
		if (null == obj) {
			return null;
		} else {
			try {
				String num = (String) obj;
				return num;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
