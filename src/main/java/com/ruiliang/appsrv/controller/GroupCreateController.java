package com.ruiliang.appsrv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.ImGroup;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.ImGroupService;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;

@RestController
@RequestMapping("api/chat")
public class GroupCreateController {

	@Autowired
	private UserInfoService uService;
	
	@Autowired
	private UserTokenService utService;
	
	@Autowired
	private ImGroupService igService;
	
	@RequestMapping("creategrp")
	public JSONObject creategrp(HttpServletRequest request){
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
		String req = (String)request.getAttribute("params");
		JSONObject object = JSONObject.parseObject(req);
		
		String da = object.getString("data");
		String token = object.getString("token");
		String sign = object.getString("sign");
		
		if(StringUtils.isBlank(da) || StringUtils.isBlank(token) || StringUtils.isBlank(sign)){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		UserToken userToken = utService.findByToken(token);
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "token失效");
			reslut.put("data", data);
			return reslut;
		}
		JSONObject user = JSONObject.parseObject(da);
		String crtuid = (String)user.get("crtuid");
		String grpuids = (String)user.get("grpuids");
		String[] split = grpuids.split(",");
		List<String> list = new ArrayList<String>();
		for (String string : split) {
			list.add(string);
		}
		
		ImGroup create = null;
		try {
			 create = igService.create(crtuid, list);
		} catch (Exception e) {
			e.printStackTrace();
			reslut.put("state", -1);
			reslut.put("msg", e.getMessage());
			reslut.put("data", data);
			return reslut;
		}
		if(null != create){
			data.put("result", 0);
			data.put("msg", "创建成功");
			data.put("chatid", create.getGroupId());
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "群聊创建失败");
		reslut.put("data", data);
		return reslut;
	}
}
