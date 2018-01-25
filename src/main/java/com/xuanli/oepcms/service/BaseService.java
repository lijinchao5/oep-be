/**
 * @fileName:  BaseService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:43:13
 */ 
package com.xuanli.oepcms.service;

import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
public class BaseService {
	public <T> RestResult<T> ok(T result) {
		return RestResult.ok(result);
	}

	public <T> RestResult<T> failed(int code, String message, T result) {
		return RestResult.failed(code, message, result);
	}

	public <T> RestResult<T> failed(int code, String message) {
		return RestResult.failed(code, message);
	}
}