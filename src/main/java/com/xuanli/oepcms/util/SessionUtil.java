/**
 * @fileName:  SessionUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 上午9:48:16
 */
package com.xuanli.oepcms.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.xuanli.oepcms.contents.SystemContents;
import com.xuanli.oepcms.entity.UserEntity;

/**
 * @author QiaoYu
 */
public class SessionUtil {
	public static Logger logger = Logger.getLogger(SessionUtil.class);

	public static Object getSessionUser(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(SystemContents.CURRENT_USER);
		return obj;
	}

	public static void setSessionUser(HttpServletRequest request, UserEntity userEntity) {
		request.getSession().setAttribute(SystemContents.CURRENT_USER, userEntity);
	}
	
	public static Object getRandomNum(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(SystemContents.CURRENT_USER);
		return obj;
	}

	public static void setRandomNum(HttpServletRequest request, String num) {
		request.getSession().setAttribute(SystemContents.CURRENT_USER, num);
	}
}
