package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;


@RestController()
//@RequestMapping(value = "/", method = RequestMethod.POST)
@RequestMapping(value = "/")
public class LoginController extends BaseController{
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "login.do")
	public RestResult<String> login(String username,String password,String randomStr){
		try {
//			if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(getRandomNum())) {
			if (true) {
				//验证通过
				String result = userService.login(username,password,request);
				if (StringUtil.isEmpty(result)) {
					return failed(99999, "未知错误,请联系管理员.");
				}else {
					if (result.equals("1")) {
						logger.debug("登陆成功.");
						return ok("登陆成功.");
					}else if (result.equals("2")) {
						//用户名//或者密码错误
						return failed(1002, "用户名或者密码错误.");
					}else {
						return failed(99999, "未知错误,请联系管理员.");
					}
				}
			}else {
				return failed(1001, "验证码错误.");
			}
		} catch (Exception e) {
			logger.error("登陆异常,请联系管理员.",e);
			e.printStackTrace();
			return failed(99999, e.getMessage());
		}
		
		
	}
}
