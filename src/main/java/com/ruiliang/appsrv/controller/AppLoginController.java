package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.util.MD5Util;


/**
 * @author LinJian.Liu
 * 登录接口
 */
@RestController
@RequestMapping("api")
public class AppLoginController {

	private static final Logger LOG = LoggerFactory.getLogger(AppLoginController.class);
	
	@Autowired
	private UserInfoService uService;
	
	@Autowired
	private CustomerService cService;
	
	@RequestMapping("login")
	public JSONObject login(HttpServletRequest request){
		
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
		StringBuilder reportBuilder = new StringBuilder();
		try{
			BufferedReader reader = request.getReader();
			String tempStr = "";
			while ((tempStr = reader.readLine()) != null) {
				reportBuilder.append(tempStr);
			}
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			reslut.put("state", -1);
			data.put("flag", 0);
			reslut.put("data",data);
			reslut.put("msg", "服务器错误");
			return reslut;
		}
		
		JSONObject object = JSONObject.parseObject(reportBuilder.toString());
		
		String deviceid = object.getString("deviceid");
		String username = object.getString("username");
		String channel = object.getString("channel");
		String password = object.getString("password");
		if(StringUtils.isBlank(deviceid) || StringUtils.isBlank(username) || StringUtils.isBlank(channel) || StringUtils.isBlank(password)){
			
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		UserInfo login = null;
		
		try {
			 login = uService.login(deviceid, username, password);
		} catch (LoginFailureException e) {
			LOG.error(e.getMessage(),e);
			reslut.put("state", -1);
			reslut.put("msg", "登录失败");
			reslut.put("data", data);                 
			return reslut;
		}
		if(null != login.getToken()){
			data.put("token", login.getToken());
			data.put("userinfo", JSONObject.toJSON(login));
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		data.put("token", "");
		data.put("userinfo", "");
		reslut.put("state", -1);
		reslut.put("msg", "登录失败");
		reslut.put("data", data);
		return reslut;
		
	}
	
	
}
