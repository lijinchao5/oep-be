package com.xuanli.oepcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = { "classpath:kaptcha.xml" })
@ServletComponentScan
public class OepCmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(OepCmsApplication.class, args);
	}
}
