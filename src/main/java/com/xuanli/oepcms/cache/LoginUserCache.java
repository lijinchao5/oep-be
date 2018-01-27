/**
 * @fileName:  LoginUserCache.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月27日 下午2:22:20
 */ 
package com.xuanli.oepcms.cache;

import java.util.HashMap;
import java.util.Map;

import com.xuanli.oepcms.entity.UserEntity;

/** 
 * @author  QiaoYu 
 */
public class LoginUserCache {
	private static LoginUserCache instance;

	private Map<String, UserEntity> userMap = new HashMap<String, UserEntity>();

	private LoginUserCache() {
	}

	public static synchronized LoginUserCache getInstance() {
		if (null == instance) {
			instance = new LoginUserCache();
		}
		return instance;
	}

	public Map<String, UserEntity> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, UserEntity> userMap) {
		this.userMap = userMap;
	}

}
