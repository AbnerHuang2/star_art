package com.star.config;

import com.star.interceptor.LoginRequiredInterceptor;
import com.star.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	LoginRequiredInterceptor loginRequiredInterceptor;
	
	@Autowired
	PassportInterceptor passportInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(passportInterceptor);
		//访问/msg/*的时候就需要登录了
		registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/msg/*");
	}
}
