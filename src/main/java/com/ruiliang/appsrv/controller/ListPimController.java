package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;
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
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;

/**
 * @author LinJian.Liu
 * 主界面  通讯录
 */
@RestController
@RequestMapping("api/list")
public class ListPimController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActiveAppController.class);
	
	@Autowired
	private UserInfoService uService;
	
	@Autowired
	private UserTokenService utService;
	
	/**
	 * 好友列表
	 * @param request
	 * @return
	 */
	@RequestMapping("friend")
	public JSONObject listFriends(HttpServletRequest request){
		
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
		String uid = (String)user.get("uid");
		UserInfo userInfo = uService.selectUserInfoByUid(uid);
		if(null != userInfo){
			//普通用户 只能看到所属公司管理员
			if(userInfo.getType() == 0){
				List<UserInfo> bycid = uService.selectMgrBycid(userInfo.getCid());
				JSONArray array= JSONArray.parseArray(JSON.toJSONString(bycid));
				reslut.put("state", 0);
				reslut.put("msg", "success");
				data.put("pims", array);
				reslut.put("data", data);
				return reslut;
			}
			//管理员 能看到所属公司的 用户以及管理员
			if(userInfo.getType() == 1){
				List<UserInfo> bymgr = uService.selectPimBycid(userInfo.getCid());
				JSONArray array= JSONArray.parseArray(JSON.toJSONString(bymgr));
				reslut.put("state", 0);
				reslut.put("msg", "success");
				data.put("pims", array);
				reslut.put("data", data);
				return reslut;
				
			}
		}
		reslut.put("state", -1);
		reslut.put("msg", "查看好友列表失败");
		reslut.put("data", data);
		return reslut;
	}
}
