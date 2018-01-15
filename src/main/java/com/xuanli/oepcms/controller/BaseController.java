package com.xuanli.oepcms.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.vo.RestResult;

public abstract class BaseController {
	@Autowired
	protected HttpServletRequest request;
	
	public final Logger logger = Logger.getLogger(this.getClass());
	

	public <T> RestResult<T> ok(T result) {
		return RestResult.ok(result);
	}

	public <T> RestResult<T> failed(int code, String message, T result) {
		return RestResult.failed(code, message, result);
	}

	public <T> RestResult<T> failed(int code, String message) {
		return RestResult.failed(code, message);
	}

	public UserEntity getCurrentUser() {
		Object obj = SessionUtil.getSessionUser(request);
		if (null == obj) {
			return null;
		} else {
			try {
				UserEntity userEntity = (UserEntity) obj;
				return userEntity;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public String getRandomNum() {
		Object obj = SessionUtil.getRandomNum(request);
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
