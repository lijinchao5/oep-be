package com.xuanli.oepcms.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.shiro.SysShiroService;
import com.xuanli.oepcms.util.MD5Util;
import com.xuanli.oepcms.vo.RestResult;


@RestController
public class LoginController extends BaseController{
	@Autowired
	private SysShiroService loginService;
	/**登陆*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public RestResult<String> login(@RequestParam("username")String username,@RequestParam("password")String password){
		password = MD5Util.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return RestResult.ok("登陆成功");
		} catch (AuthenticationException e) {
			return RestResult.failed(0, "用户名或密码错误");
		}
	}
}
