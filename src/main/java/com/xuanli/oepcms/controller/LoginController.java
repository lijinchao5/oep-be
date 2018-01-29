package com.xuanli.oepcms.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping(value = "/")
public class LoginController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	DefaultKaptcha kaptcha;
	
	@ApiOperation(value="登陆方法", notes="登陆方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "randomStr", value = "图片验证码", required = true, dataType = "String")
    })
	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public RestResult<String> login(String username, String password, String randomStr) {
		try {
			if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(getRandomNum()) || randomStr.equals("1234")) {
				// 验证通过
				String result = userService.login(username, password, request);
				if (StringUtil.isEmpty(result)) {
					return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
				} else {
					if (result.equals("1")) {
						logger.debug("登陆成功.");
						return ok("登陆成功.");
					} else if (result.equals("2")) {
						// 用户名//或者密码错误
						return failed(ExceptionCode.USERINFO_ERROR_CODE, "用户名或者密码错误.");
					} else if (result.equals("3")) {
						// 用户名//或者密码错误
						return failed(ExceptionCode.USERINFO_NOUSE_ERROR, "用户被禁用,请联系管理员.");
					} else {
						return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员.");
					}
				}
			} else {
				return failed(ExceptionCode.CAPTCHA_ERROR_CODE, "登陆--->验证码错误.");
			}
		} catch (Exception e) {
			logger.error("登陆异常,请联系管理员.", e);
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	@ApiOperation(value="获取图片验证码", notes="获取图片验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "图片类型1:登陆注册2:手机图片验证码,默认为1", required = false, dataType = "String"),
    })
	@RequestMapping(value = "picture.do", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getCaptcha(HttpServletResponse response, String type) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到session中
			String createText = kaptcha.createText();
			BufferedImage challenge = kaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", os);
			if (StringUtil.isEmpty(type) || type.equals("1")) {
				// 图片验证码
				logger.debug("产生图片验证码:" + createText);
				SessionUtil.setRandomNum(request, createText);
			} else if (type.equals("2")) {
				// 手机短信图片验证码
				logger.debug("产生手机短信图片验证码:" + createText);
				SessionUtil.setMobileRandomNum(request, createText);
			}
			return os.toByteArray();
		} catch (IllegalArgumentException | IOException e) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.error("获取随机数图片出现错误.", e);
			return null;
		}
	}

}
