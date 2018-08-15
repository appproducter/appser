package com.ruiliang.appsrv.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.service.UserInfoService;


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
	
	@RequestMapping("login")
	public JSONObject login(String deviceid,String channel,String username,String password){
		
		JSONObject reslut = new JSONObject();
		
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			reslut.put("state", -1);
			reslut.put("msg", "用户名或密码不能为空");
			reslut.put("data", null);
			return reslut;
		}
		
		
		return null;
		
	}
	
	
}
