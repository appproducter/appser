package com.ruiliang.appsrv.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.service.ManagementService;


/**
 * @author LinJian.Liu
 * 
 */
@RestController
@RequestMapping("api/ruiliang")
public class ManagementController {

	private static final Logger LOG = LoggerFactory.getLogger(ManagementController.class);
	
	@Autowired
	private ManagementService mService;
	
	
	/**
	 * 添加用户
	 * @param userName
	 * @param userAdd
	 * @return
	 */
	@RequestMapping("addUser")
	public JSONObject addUser(String userName,String userAdd){
		
		JSONObject jb = new JSONObject();
		
		if(StringUtils.isBlank(userName)){
			jb.put("code", -1);
			jb.put("msg", "用户名不能为空");
			return jb;
		}
		
		jb.put("code", 0000);
		jb.put("msg", "添加成功");
		return jb;
	}
}
