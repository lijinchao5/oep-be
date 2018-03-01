/**
 * @fileName:  WebSocketConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午10:58:59
 */ 
package com.xuanli.oepcms.config;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/** 
 * @author  QiaoYu 
 */
@Configuration  
public class WebSocketConfig {  
	private static final Logger logger = LogManager.getLogger(WebSocketConfig.class);
    //@Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
    	logger.debug("配置websocket");
        return new ServerEndpointExporter();  
    }  
  
}  