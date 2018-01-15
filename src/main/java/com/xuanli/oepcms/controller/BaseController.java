package com.xuanli.oepcms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.vo.RestResult;

public abstract class BaseController {
	@Autowired
	private HttpServletRequest request;

	public <T> RestResult<T> ok(T result) {
		return RestResult.ok(result);
	}

	public <T> RestResult<T> failed(int code, String message, T result) {
		return RestResult.failed(code, message, result);
	}

	public <T> RestResult<T> failed(int code, String message) {
		return RestResult.failed(code, message);
	}

	public User getCurrentUser() {
		Object obj = SessionUtil.getSessionUser(request);
		if (null == obj) {
			return null;
		} else {
			try {
				User user = (User) obj;
				return user;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
