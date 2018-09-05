package com.ruiliang.appsrv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.ImGroup;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.CustomerService;
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
	
	@Autowired
	private CustomerService cs;
	
	
	/**
	 * 创建群聊
	 * @param request
	 * @return
	 */
	@RequestMapping("group/create")
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
		String userid = (String)user.get("userid");
		String[] split = userid.split(",");
		List<String> list = new ArrayList<String>();
		for (String string : split) {
			list.add(string);
		}
		
		ImGroup create = null;
		try {
			 create = igService.create(userToken.getuId(), list);
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
			data.put("groupdata", create);
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
	
	/**
	 * 移除群组用户
	 * @param request
	 * @return
	 */
	@RequestMapping("group/removeuser")
	public JSONObject removeuser(HttpServletRequest request){
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
		String grpid = (String)user.get("group_id");
		String uid = (String)user.get("uid");
		
		igService.removeGroupUser(grpid, uid);
		
		data.put("result", 0);
		data.put("msg", "移除群组用户成功");
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
	}
	
	/**
	 * 添加组成员
	 * @param request
	 * @return
	 */
	@RequestMapping("group/adduser")
	public JSONObject adduser(HttpServletRequest request){
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
		String grpid = (String)user.get("group_id");
		String uid = (String)user.get("uid");
		
		igService.addGroupUser(grpid, uid);
		
		data.put("result", 0);
		data.put("msg", "添加群组成员成功");
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
	}
	
	/**
	 * 修改群组名称
	 * @param request
	 * @return
	 */
	@RequestMapping("group/setname")
	public JSONObject setname(HttpServletRequest request){
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
		String grpid = (String)user.get("group_id");
		String grpname = (String)user.get("group_name");
		
		try {
			igService.setGroupName(userToken.getuId(), grpid, grpname);
		} catch (Exception e) {
			reslut.put("state", -1);
			reslut.put("msg", "修改群名称失败");
			reslut.put("data", data);
			return reslut;
		}
		
		data.put("result", 0);
		data.put("msg", "修改群名称成功");
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
	}
	
	@RequestMapping("group/list")
	public JSONObject listGroup(HttpServletRequest request){
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
		String grpid = (String)user.get("uid");
		String grpname = (String)user.get("channel");
		
		Customer cm = cs.selectCustomerByCid(grpname);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		List<ImGroup> group = igService.listGroup(grpid);
		List<String> uids = new ArrayList<String>();
		List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
		if(null != group && group.size() > 0){
			for (ImGroup im : group) {
				Map<String,Object> m = new HashMap<String,Object>();
				List<Map<String, Object>> list = igService.listGroupUser(im.getGroupId());
				if(null != list && list.size() > 0){
					for (Map<String, Object> map : list) {
						uids.add((String)map.get("uid"));
					}
				}
				List<UserInfo> ids = uService.selectUByIds(uids);
				m.put("chatgrpid", im.getGroupId());
				m.put("chatgrpname", im.getGroupName());
				m.put("chatusernum", igService.getGroupNum(im.getGroupId()));
				m.put("users", ids);
				maps.add(m);
				//JSONArray ja = JSONArray.parseArray(JSON.toJSONString(ids));
				m = null;
			}
			
			JSONArray ja = JSONArray.parseArray(JSON.toJSONString(maps));
			
			//data.put("chatgrpdata", ja);
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", ja);
			return reslut;
		}else{
			reslut.put("state", -1);
			reslut.put("msg", "群列表为空");
			reslut.put("data", data);
			return reslut;
		}
		
		
		
	}
}
