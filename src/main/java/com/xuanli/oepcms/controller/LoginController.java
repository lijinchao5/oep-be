package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.shiro.SysShiroService;
import com.xuanli.oepcms.vo.RestResult;


@RestController
@RequestMapping(value = "/users")
public class LoginController extends BaseController{
	@Autowired
	private SysShiroService loginService;
	/**登陆*/
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public RestResult<List<User>> login(@RequestParam("username")String username,@RequestParam("password")String password){
		loginService.login(username, password);
		return new RestResult<List<User>>();
	}


}
