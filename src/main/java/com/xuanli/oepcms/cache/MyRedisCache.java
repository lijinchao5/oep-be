/**
 * @fileName:  RedisCache.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月29日 上午9:30:01
 */
package com.xuanli.oepcms.cache;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author QiaoYu
 */
@Service
public class MyRedisCache {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	public final Logger logger = Logger.getLogger(this.getClass());
	public void put(String key, String value) {
		logger.info("redis中添加["+key+"]---["+value+"]");
		redisTemplate.opsForValue().set(key, value, 30*60, TimeUnit.SECONDS);
	}

	public String get(String key) {
		logger.info("redis中获取["+key+"]");
		String value = redisTemplate.opsForValue().get(key);
		if (null == value || value.trim().equals("")) {
			return null;
		} else {
			redisTemplate.expire(key, 30*60, TimeUnit.SECONDS);
			return redisTemplate.opsForValue().get(key);
		}
	}
}
