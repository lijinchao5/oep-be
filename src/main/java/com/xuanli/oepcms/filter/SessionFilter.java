/**
 * @fileName:  SessionFilter.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月17日 上午10:06:08
 */
package com.xuanli.oepcms.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSON;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.util.SessionUtil;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author QiaoYu
 */
@Order(1)
@WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月17日 上午10:06:44
	 */

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月17日 上午10:06:44
	 */

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 不过滤的uri
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String[] notFilter = new String[] { "login.do", "logout.do", "picture.do","teacher_regist.do","student_regist.do","mobileMessage" };
		String uri = request.getRequestURI();
		boolean doFilter = true;
		if (uri.indexOf(".do") != -1) {
			logger.info("SessionFilter拦截器[URI=" + uri + "]");
			for (String s : notFilter) {
				if (uri.indexOf(s) != -1) {
					doFilter = false;
					break;
				}
			}
			if (doFilter) {
				Object obj = SessionUtil.getSessionUser(request);
				logger.info("进入SessionFilter拦截器[Session=" + obj + "]");
				if (null == obj) {
					logger.info("进入SessionFilter拦截器[Session是空的进入AJAX请求!]");
					PrintWriter printWriter = response.getWriter();
					RestResult<String> restResult = new RestResult<String>();
					restResult.setCode(ExceptionCode.USER_NO_LOGIN);
					restResult.setResult("登陆超时");
					restResult.setMessage("登陆超时");
					printWriter.print(JSON.toJSONString(restResult));
					printWriter.flush();
					printWriter.close();
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @CreateUser:QiaoYu
	 * @CreateDate:2018年1月17日 上午10:06:44
	 */

	@Override
	public void destroy() {
	}

}
