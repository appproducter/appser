package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Calllog;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.Loc;
import com.ruiliang.appsrv.pojo.Pim;
import com.ruiliang.appsrv.pojo.Sms;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.CalllogService;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.LocService;
import com.ruiliang.appsrv.service.OperLogService;
import com.ruiliang.appsrv.service.PimService;
import com.ruiliang.appsrv.service.SmsLogService;
import com.ruiliang.appsrv.service.UserTokenService;
import com.ruiliang.appsrv.util.DateUtil;


/**
 * @author LinJian.Liu
 * 查看手机 短信 通话记录 通讯录
 */
@RestController
@RequestMapping("api/mgr/show")
public class DownSmsPimCallController {

	private static final Logger LOG = LoggerFactory.getLogger(ActiveAppController.class);
	
	@Autowired
	private UserTokenService uService;
	
	@Autowired
	private CustomerService cService;
	
	@Autowired
	private CalllogService clService;
	
	@Autowired
	private SmsLogService slService;
	
	@Autowired
	private PimService pService;
	
	@Autowired
	private OperLogService opService;
	
	@Autowired
	private LocService lService;
	
	/**
	 * 通话记录
	 * @param request
	 * @return
	 */
	@RequestMapping("calllog")
	public JSONObject downCallLog(HttpServletRequest request){
		
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
		
		JSONObject object2 = JSONObject.parseObject(da);
		String channel = (String)object2.get("channel");
		String uid = (String)object2.get("uid");
		Customer cm = cService.selectCustomerByCid(channel);
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		
		List<Calllog> list = clService.selectCalllogByUid(uid);
		if(!list.isEmpty() && null !=list){
			List<Object> maps = new ArrayList<Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			for (Calllog calllog : list) {
				map.put("time", calllog.getTime());
				map.put("calllog", calllog.getCalllog());
				maps.add(map);
				
			}
			
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(maps));
			
			data.put("calllogdata", array);
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		
		reslut.put("state", -1);
		reslut.put("msg", "通话记录为空");
		reslut.put("data", data);
		return reslut;
		
		
	}
	
	/**
	 * 手机短信
	 * @param request
	 * @return
	 */
	@RequestMapping("sms")
	public JSONObject downSms(HttpServletRequest request){
		
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
		
		JSONObject object2 = JSONObject.parseObject(da);
		String channel = (String)object2.get("channel");
		String uid = (String)object2.get("uid");
		Long lastTime = object2.getLong("lastsmstime");
		Customer cm = cService.selectCustomerByCid(channel);
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		Sms sms = slService.selectSmsByUid(uid,lastTime);
		if(null != sms){
			List<Object> maps = new ArrayList<Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("time", new Date().getTime());
			map.put("sms", sms.getSms());
			maps.add(map);
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(maps));
			
			data.put("smsdata", array);
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		
		reslut.put("state", -1);
		reslut.put("msg", "短信记录为空");
		reslut.put("data", data);
		return reslut;
		
	}
	
	/**
	 * 手机通讯录
	 * @param request
	 * @return
	 */
	@RequestMapping("pim")
	public JSONObject downPim(HttpServletRequest request){
		
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
		
		JSONObject object2 = JSONObject.parseObject(da);
		String channel = (String)object2.get("channel");
		String uid = (String)object2.get("uid");
		Customer cm = cService.selectCustomerByCid(channel);
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		
		Pim pim = pService.selectPimByUid(uid);
		
		if(null != pim){
			Map<String,Object> map = new HashMap<String,Object>();
			//取值是取记录创建时间 还是user_info 表最后一次上传时间
			map.put("time", pim.getcTime().getTime());
			map.put("pim", pim.getPim());
			JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(map));
			
			data.put("pimdata", itemJSONObj);
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		
		reslut.put("state", -1);
		reslut.put("msg", "通讯录记录为空");
		reslut.put("data", data);
		return reslut;
		
	}
	
	/** 
	 * 地理位置
	 * @param request
	 * @return
	 */
	@RequestMapping("loc")
	public JSONObject downLoc(HttpServletRequest request){
		
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
		
		JSONObject object2 = JSONObject.parseObject(da);
		String channel = (String)object2.get("channel");
		String uid = (String)object2.get("uid");
		Integer day = object2.getIntValue("day");
		Customer cm = cService.selectCustomerByCid(channel);
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码错误");
			reslut.put("data", data);
			return reslut;
		}
		Long start = 0L;
		Long end = 0L;
		// 当天
		if(day == 1){
			 start = DateUtil.getStartTime().getTime();
			 end = DateUtil.getnowEndTime().getTime();
		}else if(day == 7){
			DateTime sttime = new DateTime();
			start = DateUtil.getStartTime().getTime();
			end = sttime.plusDays(day).toDate().getTime();	
		}else if(day == 30){
			DateTime sttime = new DateTime();
			start = DateUtil.getStartTime().getTime();
			end = sttime.plusDays(day).toDate().getTime();
		}else if(day == 180){
			DateTime sttime = new DateTime();
			start = DateUtil.getStartTime().getTime();
			end = sttime.plusDays(day).toDate().getTime();
		}else{
			//..
		}
		
		List<Loc> list = lService.selectLocByUid(uid, start, end);
		
		if(null != list){
			List<Object> maps = new ArrayList<Object>();
			
			for (Loc loc : list) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("time", loc.getTime());
				map.put("loc", loc.getLoc());
				maps.add(map);
				map = null;
			}
			
			JSONArray array= JSONArray.parseArray(JSON.toJSONString(maps));
			
			data.put("locdata", array);
			reslut.put("state", 0);
			reslut.put("msg", "success");
			reslut.put("data", data);
			return reslut;
		}
		
		reslut.put("state", -1);
		reslut.put("msg", "位置信息为空");
		reslut.put("data", data);
		return reslut;
		
	}
}
