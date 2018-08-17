package com.ruiliang.appsrv.config;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.UserTokenService;
import com.ruiliang.util.SignUtil;

/**
 * @author LinJian.Liu
 *
 */
@Component
public class AppReqIntercetor implements HandlerInterceptor{

private static final Logger log = LoggerFactory.getLogger(AppTokenInterceptor.class);  
	  
	
	@Autowired
	private UserTokenService uService;
	
	@Autowired
	private CustomerService cService;
	
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
        
        
        //数据库是否存在token
        UserToken userToken = uService.findByToken(token);
        
        Customer customer = cService.selectCustomerByCid(userToken.getCid());
        
        if(null == customer || StringUtils.isBlank(customer.getApiKey())){
        	PrintWriter printWriter = httpServletResponse.getWriter();
        	printWriter.write("{state:-1,message:\"apikey不存在\"}");  
            return false; 
        }
        
        String apiKey = customer.getApiKey();
        
        StringBuilder reportBuilder = new StringBuilder();
		try{
			BufferedReader reader = httpServletRequest.getReader();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
		}catch(Exception e){
			PrintWriter printWriter = httpServletResponse.getWriter();
        	printWriter.write("{state:-1,message:\"服务器错误\"}");  
            return false;
		}
		JSONObject parseObject = JSONObject.parseObject(reportBuilder.toString());
		String csign = parseObject.getString("sign");
		String data = parseObject.getString("data");
		
        Map map = (Map)JSONObject.parse(data);
        //校验
        String ssign = SignUtil.generateSign(map, apiKey);
        
        if(!Objects.equals(csign, ssign)){
        	PrintWriter printWriter = httpServletResponse.getWriter();
        	printWriter.write("{state:-1,message:\"签名错误\"}");  
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
