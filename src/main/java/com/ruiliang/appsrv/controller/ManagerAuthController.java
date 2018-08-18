package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;
import com.ruiliang.appsrv.util.MD5Util;


@RestController
@RequestMapping("api/mgr")
public class ManagerAuthController {

	private static final Logger LOG = LoggerFactory.getLogger(ActiveAppController.class);
	
	@Autowired
	private UserTokenService uService;
	
	@Autowired
	private UserInfoService uiService;
	
	@Autowired
	private CustomerService cService;
	
	@RequestMapping("adduser")
	public JSONObject addUser(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
			data.put("result", -1);
			data.put("msg", "添加失败");
			reslut.put("data",data);
			reslut.put("msg", "服务器错误");
			return reslut;
		}
		
		
		JSONObject object = JSONObject.parseObject(reportBuilder.toString());
		
		String da = object.getString("data");
		String token = object.getString("token");
		String sign = object.getString("sign");
		
		if(StringUtils.isBlank(da) || StringUtils.isBlank(token) || StringUtils.isBlank(sign)){
			
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		JSONObject userinfo = JSONObject.parseObject(us.getString("userinfo")); 
		UserInfo ui = JSON.toJavaObject(userinfo, UserInfo.class);
		
		if(StringUtils.isBlank(ui.getPassword()) || StringUtils.isBlank(ui.getName()) ||
			StringUtils.isBlank(ui.getIdCard()) || StringUtils.isBlank(ui.getMobile())){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		//creator
		ui.setCreator(userToken.getuId());
		//普通用户
		ui.setType((byte)0);
		//状态
		ui.setStatus((byte)0);
		ui.setLoginTimes(0);
		ui.setPassword(MD5Util.MD5Encode(ui.getPassword()));
		if(null == ui.getGender()){
			ui.setGender((byte)0);
		}
		
		uiService.create(token, ui);
		
		reslut.put("state", 0);
		reslut.put("msg", "success");
		data.put("result", "0");
		data.put("msg", "添加用户成功");
		reslut.put("data", data);
		return reslut;
		
	}
	
	@RequestMapping("updateuser")
	public JSONObject updateUser(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
			data.put("result", -1);
			data.put("msg", "添加失败");
			reslut.put("data",data);
			reslut.put("msg", "服务器错误");
			return reslut;
		}
		
		
		JSONObject object = JSONObject.parseObject(reportBuilder.toString());
		
		String da = object.getString("data");
		String token = object.getString("token");
		String sign = object.getString("sign");
		
		if(StringUtils.isBlank(da) || StringUtils.isBlank(token) || StringUtils.isBlank(sign)){
			
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		JSONObject userinfo = JSONObject.parseObject(us.getString("userinfo")); 
		UserInfo ui = JSON.toJavaObject(userinfo, UserInfo.class);
		
		if(StringUtils.isBlank(ui.getPassword()) || StringUtils.isBlank(ui.getName()) ||
			StringUtils.isBlank(ui.getIdCard()) || StringUtils.isBlank(ui.getMobile())){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		ui.setuId(userToken.getuId());
		int i = uiService.updateUser(ui);
		
		if(i == 1){
			reslut.put("state", 0);
			reslut.put("msg", "success");
			data.put("result", "0");
			data.put("msg", "更新用户成功");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "更新用户失败");
		reslut.put("data", data);
		return reslut;
		
	}
	
	@RequestMapping("auth_main")
	public JSONObject authMain(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
			data.put("result", -1);
			data.put("msg", "添加失败");
			reslut.put("data",data);
			reslut.put("msg", "服务器错误");
			return reslut;
		}
		
		
		JSONObject object = JSONObject.parseObject(reportBuilder.toString());
		
		String da = object.getString("data");
		String token = object.getString("token");
		String sign = object.getString("sign");
		
		if(StringUtils.isBlank(da) || StringUtils.isBlank(token) || StringUtils.isBlank(sign)){
			
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		
		String channel = (String) us.get("channel");
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		//授权
		/*uiService.updateUserAuth(type, uid)
		if(i == 1){
			reslut.put("state", 0);
			reslut.put("msg", "success");
			data.put("result", "0");
			data.put("msg", "更新用户成功");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "更新用户失败");
		reslut.put("data", data);
		return reslut;*/
		return null;
		
	}
}