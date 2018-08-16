package com.ruiliang.appsrv.config;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserTokenService;

/**
 * @author LinJian.Liu
 *
 */
@Component
public class AppTokenInterceptor implements HandlerInterceptor{

	private static final Logger log = LoggerFactory.getLogger(AppTokenInterceptor.class);  
	  
	
	@Autowired
	private UserTokenService uService;
    /** 
     * 进入controller层之前拦截请求 
     * @param httpServletRequest 
     * @param httpServletResponse 
     * @param o 
     * @return 
     * @throws Exception 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {  
        log.info("---------------------开始进入请求地址拦截----------------------------");  
        
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/html; charset=utf-8");
        String token = httpServletRequest.getParameter("token");
        
        if(StringUtils.isBlank(token)){
        	PrintWriter printWriter = httpServletResponse.getWriter();
        	printWriter.write("{state:-1,message:\"无效的token,请重新登录\"}");  
            return false; 
        }
        //数据库是否存在token
        UserToken userToken = uService.findByToken(token);
        if(null == userToken){
        	PrintWriter printWriter = httpServletResponse.getWriter();
        	printWriter.write("{state:-1,message:\"无效的token,请重新登录\"}");  
            return false; 
        }
        return true;
        
    }  
  
    @Override  
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {  
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {  
    }  

}
