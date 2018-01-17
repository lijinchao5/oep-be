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

@RestController()
// @RequestMapping(value = "/", method = RequestMethod.POST)
@RequestMapping(value = "/**.do")
public class LoginController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	DefaultKaptcha kaptcha;

	@RequestMapping(value = "login")
	public RestResult<String> login(String username, String password, String randomStr) {
		try {
			if (StringUtil.isNotEmpty(randomStr) && randomStr.equalsIgnoreCase(getRandomNum())) {
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

	@RequestMapping(value = "picture", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
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
