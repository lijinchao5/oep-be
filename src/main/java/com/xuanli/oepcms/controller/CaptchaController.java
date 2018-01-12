package com.xuanli.oepcms.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xuanli.oepcms.util.MD5Util;
import com.xuanli.oepcms.util.RanNumUtil;
/**验证短信验证码，暂时未执行短信发送方法*/
public class CaptchaController {
	private static final String KEY = "abc123"; // KEY为自定义秘钥
	private String currentTime;
	
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> sendMsg(@RequestBody Map<String,Object> requestMap) {
	String phoneNumber = requestMap.get("phoneNumber").toString();
	String randomNum = RanNumUtil.createRandomNum(6);// 生成随机数
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MINUTE, 10);
	currentTime = sf.format(c.getTime());// 生成10分钟后时间，用户校验是否过期
	
//	sengMsg(); //此处执行发送短信验证码方法
	
	String hash =  MD5Util.encrypt(KEY + "@" + currentTime + "@" + randomNum);//生成MD5值
	Map<String, Object> resultMap = new HashMap<>();
	resultMap.put("hash", hash);
	resultMap.put("tamp", currentTime);
	return resultMap; //将hash值和tamp时间返回给前端
	}

	@RequestMapping(value = "/validateNum", method = RequestMethod.POST, headers = "Accept=application/json")
	public Map<String, Object> validateNum(@RequestBody Map<String, Object> requestMap) {
		//获取MD5值
		String requestHash = requestMap.get("hash").toString();
		//当前时间
		String tamp = requestMap.get("tamp").toString();
		String msgNum = requestMap.get("msgNum").toString();
		String hash = MD5Util.encrypt(KEY + "@" + tamp + "@" + msgNum);
		//比较 发送验证码时间与当前时间
		if (tamp.compareTo(currentTime) > 0) {
			//比较验证码是否相同
			if (hash.equalsIgnoreCase(requestHash)) {
				// 校验成功
			} else {
				// 验证码不正确，校验失败
			}
		} else {
			// 超时
		}
		return null;
	}
}
