/**
 * @fileName:  CorsConfiguration.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月23日 上午11:19:12
 */ 
package com.xuanli.oepcms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/** 
 * @author  QiaoYu 
 */
@Configuration
public class MyCorsConfiguration implements EnvironmentAware{
	private static final Logger logger = LoggerFactory.getLogger(MyCorsConfiguration.class);

    public RelaxedPropertyResolver propertyResolver;
    /**
     * 初始化yml配置
     */
    @Override
    public void setEnvironment(Environment env) {
    	this.propertyResolver = new RelaxedPropertyResolver(env, "systemconfig.");
    }
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(new Long(1800));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        logger.info("配置跨域完成.....");
        return bean;
    }

}
