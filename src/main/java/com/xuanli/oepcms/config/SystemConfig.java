/**
 * @fileName:  SystemConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月19日 上午9:51:54
 */
package com.xuanli.oepcms.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.stereotype.Component;

/**
 * @author QiaoYu
 */
@Component
public class SystemConfig {
	private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);
	@Autowired
	private DatabaseConfig databaseConfig;

	private RelaxedPropertyResolver propertyResolver;

	public String FILE_BASEPATH = "";

	@PostConstruct
	public void initMethod() {
		propertyResolver = databaseConfig.propertyResolver;
		FILE_BASEPATH = propertyResolver.getProperty("file.base-path");
		logger.debug("初始化完成系统配置信息...");
	}
}
