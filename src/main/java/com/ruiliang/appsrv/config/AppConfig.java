package com.ruiliang.appsrv.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer{
	
	@Autowired
	private AppTokenInterceptor ai;
	
	
	@Autowired
	private AppReqIntercetor ac;
	
	@Autowired
	private AppAllParamIntercetor api;
	
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCorsMappings(CorsRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFormatters(FormatterRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry arg0) {
		arg0.addInterceptor(api).addPathPatterns("/api/**");
		arg0.addInterceptor(ai).addPathPatterns("/api/list/friend","/api/mgr/show/pim",
				"/api/mgr/show/sms","/api/mgr/show/callog","/api/mgr/show/loc",
				"/api/mgr/deletemanage","/api/mgr/authorize","/api/mgr/list_pim",
				"/api/mgr/auth_main","/api/mgr/updateuser","/api/mgr/adduser",
				"/api/user/setavatar","/api/up/loc","/api/up/calllog",
				"/api/up/sms","/api/up/pim","/api/show/chatgrp","/api/chat/recv",
				"/api/chat/create","/api/chat/creategrp","/api/chat/send","/api/chat/upload/mediamsg"
				);
		arg0.addInterceptor(ac).addPathPatterns("/api/user/setavatar",
				"/api/mgr/adduser","/api/mgr/updateuser","/api/mgr/auth_main",
				"/api/mgr/list_pim","/api/mgr/authorize","/api/mgr/deletemanage",
				"/api/mgr/show/loc","/api/mgr/show/callog","/api/mgr/show/sms",
				"/api/mgr/show/pim","/api/list/friend","/api/show/chatgrp",
				"/api/chat/recv","/api/chat/create","/api/chat/creategrp",
				"/api/chat/send","/api/chat/upload/mediamsg"
				);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry res) {
		
	}

	@Override
	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendHandlerExceptionResolvers(
			List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

}
