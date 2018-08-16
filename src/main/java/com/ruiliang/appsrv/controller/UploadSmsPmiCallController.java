package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.OperLog;
import com.ruiliang.appsrv.pojo.Pim;
import com.ruiliang.appsrv.pojo.Sms;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.OperLogService;
import com.ruiliang.appsrv.service.PimService;
import com.ruiliang.appsrv.service.SmsService;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;

/**
 * @author LinJian.Liu
 * 上传短信通话记录
 */
@RestController
@RequestMapping("api/up")
public class UploadSmsPmiCallController {

	private static final Logger LOG = LoggerFactory.getLogger(UploadSmsPmiCallController.class);
	
	@Autowired
	private CustomerService cService;
	
	@Autowired
	private PimService pService;
	
	@Autowired
	private OperLogService oService;
	
	@Autowired
	private SmsService sService;
	
	@Autowired
	private UserTokenService uService;
	
	@Autowired
	private UserInfoService uiService;
	
	/**
	 * 上传通讯录
	 * @param channel
	 * @param token
	 * @param pim
	 * @return
	 */
	@RequestMapping("pim")
	public JSONObject uploadPim(HttpServletRequest request){
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
		
		String pim = object.getString("pim");
		String token = object.getString("token");
		String channel = object.getString("channel");
		
		if(StringUtils.isBlank(pim) || StringUtils.isBlank(token) || StringUtils.isBlank(channel)){
			
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
		}
		
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码有误");
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
		
		Pim pm = new Pim();
		pm.setPim(pim);
		pm.setuId(userToken.getuId());
		
		Integer savePim = pService.savePim(pm);
		
		if(savePim != 1){
			reslut.put("state", -1);
			reslut.put("msg", "上传失败");
			reslut.put("data", data);
			return reslut;
		}
		
		//记录操作日志
		OperLog ol = new OperLog();
		ol.setuId(userToken.getuId());
		ol.setContent("上传通讯录成功");
		ol.setType((byte)3);
		
		Integer log = oService.saveOperLog(ol);
		if(log != 1){
			LOG.warn("method (uploadPim) 保存操作日志失败");
		}
		
		//更新最后一次上传时间
		Integer i = uiService.updateUpTimePim(System.currentTimeMillis());
		
		if(i != 1){
			LOG.warn("method (uploadPim) 最后一次通讯录时间上传失败");
		}
		
		data.put("result", 0);
		data.put("msg", "上传成功");
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
	}
	
	/**
	 * 上传短信
	 * @param channel
	 * @param token
	 * @param sms
	 * @return
	 */
	@RequestMapping("sms")
	public JSONObject uploadSms(HttpServletRequest request){
		
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
		
		String sms = object.getString("sms");
		String token = object.getString("token");
		String channel = object.getString("channel");
		
		if(StringUtils.isBlank(sms) || StringUtils.isBlank(token) || StringUtils.isBlank(channel)){
			
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空"); 
			reslut.put("data", data);
			return reslut;
		}
		
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码有误");
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
		
		Sms sm = new Sms();
		//jsonArray to jsonObject
		/*JSONArray myJsonArray = JSONArray.parseArray(sms);
		int len = myJsonArray.size();
		for (int i = 0; i < len ; i++) {
			JSONObject object = myJsonArray.getJSONObject(i);
			sm.setSms(object.getString("smsdata"));
			sm.setuId(userToken.getuId());
			sm.setLastSmsTime(Long.valueOf(object.getString("t")));
			Integer saveSms = sService.saveSms(sm);
			if(saveSms != 1){
				LOG.warn("短信记录保存失败"+userToken.getuId());
			}
		}*/
		//更新最后一次上传时间
		
		
		
		
		//记录操作日志
		OperLog ol = new OperLog();
		ol.setuId(userToken.getuId());
		ol.setContent("上传通讯录成功");
		ol.setType((byte)3);
		
		Integer log = oService.saveOperLog(ol);
		if(log != 1){
			LOG.warn("method (uploadPim) 保存操作日志失败");
		}
		
		data.put("result", 0);
		data.put("msg", "上传成功");
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
	}
}
