package com.ruiliang.appsrv.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.exception.SendCodeFailureException;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserVerifyService;
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
	
	@Autowired
	private UserVerifyService uvService;
	
	/**
	 * 登录接口
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	public JSONObject login(HttpServletRequest request){
		
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
		String req = (String)request.getAttribute("params");
		
		JSONObject object = JSONObject.parseObject(req);
		
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
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @return
	 */
	@RequestMapping("sendsmscode")
	public JSONObject sendSmsCode(HttpServletRequest request){
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
		String req = (String)request.getAttribute("params");
		
		JSONObject object = JSONObject.parseObject(req);
		
		String mobile = object.getString("mobile");
		Integer type = object.getInteger("type");
		if(StringUtils.isBlank(mobile) || null == type){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		//发送验证码
		try {
			uvService.sendCode(null, mobile, 1, type);
		} catch (SendCodeFailureException e) {
			LOG.error(e.getMessage(),e);
			reslut.put("state", -1);
			reslut.put("msg", "验证码发送失败");
			reslut.put("data", data);
			return reslut;
		}
		data.put("result", 0);
		data.put("msg", "发送成功");
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
	}
	
	
	/**
	 * 重置密码
	 * @param request
	 * @return
	 */
	@RequestMapping("resetpwd")
	public JSONObject resetPwd(HttpServletRequest request){
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
		String req = (String)request.getAttribute("params");
		
		JSONObject object = JSONObject.parseObject(req);
		
		String username = object.getString("username");
		String code = object.getString("code");
		String newpwd = object.getString("newpwd");
		//String repwd = object.getString("repwd");
		if(StringUtils.isBlank(username) || StringUtils.isBlank(code)||StringUtils.isBlank(newpwd)){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		//验证验证码
		boolean checkCode;
		try {
			checkCode = uvService.checkCode(username, code, 1, 1);
		} catch (SendCodeFailureException e) {
			LOG.error(e.getMessage(),e);
			reslut.put("state", -1);
			reslut.put("msg", "短信验证码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		if(checkCode){
			int i = uService.updateUserPwd(MD5Util.MD5Encode(newpwd), username);
			if(i != 1){
				reslut.put("state", -1);
				reslut.put("msg", "密码重置失败");
				reslut.put("data", data);
				return reslut;
			}
			data.put("result", 0);
			data.put("msg", "密码重置成功");
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "密码重置失败");
		reslut.put("data", data);
		return reslut;
	}
}
