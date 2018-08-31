package com.ruiliang.appsrv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.OperLog;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.OperLogService;
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
	
	@Autowired
	private OperLogService oService;
	
	@RequestMapping("adduser")
	public JSONObject addUser(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		String pwd = us.getString("password");
		String name = us.getString("name");
		String idcard = us.getString("idcard");
		String mobile = us.getString("mobile");
		String sex = us.getString("sex");
		String email = us.getString("email");
		String area = us.getString("area");
		String address = us.getString("address");
		String avatar = us.getString("avatar");
		if(StringUtils.isBlank(name) ||
			StringUtils.isBlank(idcard) || StringUtils.isBlank(mobile)){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		UserInfo ui = new UserInfo();
		ui.setName(name);
		ui.setPassword("123456");
		ui.setMobile(mobile);
		ui.setIdCard(idcard);
		if(StringUtils.isBlank(sex)){
			ui.setGender((byte)0);
		}
		ui.setEmail(email);
		ui.setArea(area);
		ui.setAddress(address);
		ui.setAvatar(avatar);
		//creator
		ui.setCreator(userToken.getuId());
		//普通用户
		ui.setType((byte)0);
		//状态
		ui.setStatus((byte)0);
		ui.setLoginTimes(0);
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
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		String uid = us.getString("uid");
		String pwd = us.getString("password");
		String name = us.getString("name");
		String idcard = us.getString("idcard");
		String mobile = us.getString("mobile");
		String sex = us.getString("sex");
		String email = us.getString("email");
		String area = us.getString("area");
		String address = us.getString("address");
		String avatar = us.getString("avatar");
		if(StringUtils.isBlank(uid)||StringUtils.isBlank(pwd) || StringUtils.isBlank(name) ||
			StringUtils.isBlank(idcard) || StringUtils.isBlank(mobile)){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		UserInfo ui = new UserInfo();
		ui.setName(name);
		ui.setPassword(pwd);
		ui.setMobile(mobile);
		ui.setIdCard(idcard);
		if(StringUtils.isBlank(sex)){
			ui.setGender((byte)0);
		}
		ui.setEmail(email);
		ui.setArea(area);
		ui.setAddress(address);
		ui.setAvatar(avatar);
		//creator
		ui.setCreator(userToken.getuId());
		//普通用户
		ui.setType((byte)0);
		//状态
		ui.setStatus((byte)0);
		ui.setuId(uid);
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
	
	/**
	 * 查看管理员列表
	 * @param request
	 * @return
	 */
	@RequestMapping("auth_main")
	public JSONObject authMain(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "token失效");
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
		
		//管理员列表
		List<UserInfo> bycid = uiService.selectMgrBycid(channel);
		//扩充管理员数量
		Customer customer = cService.selectCustomerByCid(channel);
		
		if(null == bycid || null == customer){
			reslut.put("state", -1);
			reslut.put("msg", "获取管理员列表失败");
			reslut.put("data", data);
			return reslut;
			
		}
		
		JSONArray array= JSONArray.parseArray(JSON.toJSONString(bycid));
		reslut.put("state", 0);
		reslut.put("msg", "success");
		data.put("exqua", customer.getMgrQua());
		data.put("managers", array);
		reslut.put("data", data);
		return reslut;
		
	}
	
	/**
	 * 添加管理者-->好友列表  角色只能是普通用户
	 * @param request
	 * @return
	 */
	@RequestMapping("list_pim")
	public JSONObject listPim(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "token失效");
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
		
		List<UserInfo> list = uiService.selectPim(channel);
		if(!list.isEmpty()){
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
			data.put("pims", array);
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "获取好友列表失败");
		reslut.put("data", data);
		return reslut;
		
	}
	
	/**
	 * 添加管理员
	 * @param request
	 * @return
	 */
	@RequestMapping("authorize")
	public JSONObject authorize(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "token失效");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		
		String channel = (String) us.get("channel");
		String uid = (String) us.get("uid");
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		if(!StringUtils.isBlank(uid)){
			String[] split = uid.split(",");
			for (String string : split) {
				int i = uiService.updateUserType((byte)1,string, channel);
				if(i != 1){
					reslut.put("state", -1);
					reslut.put("msg", "添加管理员失败");
					reslut.put("data", data);
					return reslut;
				}
			}
			//记录操作日志
			OperLog ol = new OperLog();
			ol.setuId(userToken.getuId());
			ol.setContent("添加管理员");
			ol.setType((byte)3);
			
			Integer log = oService.saveOperLog(ol);
			if(log != 1){
				LOG.warn("method (uploadPim) 保存操作日志失败");
			}
			reslut.put("state", 0);
			reslut.put("msg", "success");
			data.put("result", 0);
			data.put("msg", "设置成功");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "添加管理员失败");
		reslut.put("data", data);
		return reslut;
	}
	
	/**
	 * 删除管理权限
	 * @param request
	 * @return
	 */
	@RequestMapping("deletemanage")
	public JSONObject deleteManage(HttpServletRequest request){
		JSONObject reslut = new JSONObject();//结果集
		JSONObject data = new JSONObject();//数据
		
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
		
		//根据TOKEN查询UID
		UserToken userToken = uService.findByToken(token);
				
		if(null == userToken || userToken.getuId() == null){
			reslut.put("state", -1);
			reslut.put("msg", "token失效");
			reslut.put("data", data);
			return reslut;
		}
		
		JSONObject us = JSONObject.parseObject(da);
		
		String channel = (String) us.get("channel");
		String uid = (String) us.get("uid");
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		if(!StringUtils.isBlank(uid)){
			String[] split = uid.split(",");
			for (String string : split) {
				int i = uiService.updateUserType((byte)0,string, channel);
				if(i != 1){
					reslut.put("state", -1);
					reslut.put("msg", "删除管理权限失败");
					reslut.put("data", data);
					return reslut;
				}
			}
			
			//记录操作日志
			OperLog ol = new OperLog();
			ol.setuId(userToken.getuId());
			ol.setContent("删除管理权限");
			ol.setType((byte)3);
			
			Integer log = oService.saveOperLog(ol);
			if(log != 1){
				LOG.warn("method (uploadPim) 保存操作日志失败");
			}
			reslut.put("state", 0);
			reslut.put("msg", "success");
			data.put("result", 0);
			data.put("msg", "设置成功");
			reslut.put("data", data);
			return reslut;
		}
		reslut.put("state", -1);
		reslut.put("msg", "删除管理权限失败");
		reslut.put("data", data);
		return reslut;
	}
}