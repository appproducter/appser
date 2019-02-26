
package com.ruiliang.appsrv.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

@Component
public class SingleUserLoginInterceptor  extends HandlerInterceptorAdapter{
	
	@Autowired
	private StringRedisTemplate redis ;

	private static final Logger log = LoggerFactory.getLogger(SingleUserLoginInterceptor.class);  
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("单点登录拦截器----------url"+request.getRequestURI());
		
		String name = (String)request.getAttribute("params");
		JSONObject parseObject = JSONObject.parseObject(name);
		String uId = parseObject.getString("username");
		String requestDeviceImei = parseObject.getString("deviceid");
		if(uId==null || uId.equals("")){
			return true ;
		}
		String deviceImei = null;
		try {
			deviceImei = (String) redis.opsForValue().get(uId);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("redis读异常");
			return true ;
		}
		if(requestDeviceImei==null || requestDeviceImei.equals("")){
			
			return true ;
		}
		if(deviceImei==null || deviceImei.equals("")){
			if(uId!=null){
				try {
					redis.opsForValue().set(uId, requestDeviceImei);
				} catch (Exception e) {
					log.error(e.getMessage());
					log.error("redis写异常");
				}
			}
			return true ;
		}
		
		if(deviceImei.equals(requestDeviceImei)){
			return true ;
		}
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		response.getWriter().write("请重新登录");
		return false ;
	}
	
}
