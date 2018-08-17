package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.DeviceInfo;
import com.ruiliang.appsrv.pojo.OperLog;
import com.ruiliang.appsrv.pojo.Version;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.DeviceInfoService;
import com.ruiliang.appsrv.service.OperLogService;
import com.ruiliang.appsrv.service.VersionService;

/**
 * @author LinJian.Liu
 * APP激活
 */
@RestController
@RequestMapping("api")
public class ActiveAppController {

	private static final Logger LOG = LoggerFactory.getLogger(ActiveAppController.class);
	
	@Autowired
	private DeviceInfoService dService;
	
	@Autowired
	private VersionService vService;
	
	@Autowired
	private CustomerService cService;
	
	@Autowired
	private OperLogService oService;
	
	/**
	 * APP激活
	 * @param deviceid
	 * @param vercode
	 * @param verinfo
	 * @param brand
	 * @param model
	 * @param os
	 * @param hpi
	 * @param wpi
	 * @param imei
	 * @param imsi
	 * @param sysversion
	 * @param channel
	 * @return
	 */
	@RequestMapping("active")
	public JSONObject appActive(HttpServletRequest request){
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
			data.put("flag", 0);
			reslut.put("data",data);
			reslut.put("msg", "服务器错误");
			return reslut;
		}
		
		
		JSONObject object = JSONObject.parseObject(reportBuilder.toString());
		
		String deviceid = object.getString("deviceid");
		Integer  vercode = object.getInteger("vercode");
		String verinfo = object.getString("verinfo");
		Integer sysversion = object.getInteger("sysversion");
		String model = object.getString("model");
		String os = object.getString("os");
		String channel = object.getString("channel");
		Integer hpi = object.getInteger("hpi");
		Integer wpi = object.getInteger("wpi");
		String brand = object.getString("brand");
		String imei = object.getString("imei");
		String imsi = object.getString("imsi");
		
		if(StringUtils.isBlank(deviceid) || null == vercode || StringUtils.isBlank(verinfo)
				|| StringUtils.isBlank(model) || StringUtils.isBlank(os) || null == hpi || null == wpi
				|| null == sysversion || StringUtils.isBlank(channel)
				){
			reslut.put("state", -1);
			data.put("flag", 0);
			reslut.put("data",data);
			reslut.put("msg", "参数不能为空");
			return reslut;
		}
		
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码有误");
			reslut.put("data", data);
		}
		
		DeviceInfo di = new DeviceInfo();
		di.setBrand(brand);
		di.setChannel(channel);
		di.setDeviceId(deviceid);
		di.setHpi(hpi);
		di.setImei(imei);
		di.setImsi(imsi);
		di.setModel(model);
		di.setOs(os);
		di.setSysVersion(sysversion);
		di.setVerCode(vercode);
		di.setVerInfo(verinfo);
		di.setWpi(wpi);
		Integer info = dService.insertDeviceInfo(di);
		
		if(info != 1){
			reslut.put("state", -1);
			data.put("flag", 0);
			reslut.put("data",data);
			reslut.put("msg", "更新数据失败");
			return reslut;
		}
		
		//记录操作日志，由于没有UID 则存储设备ID
		OperLog ol = new OperLog();
		ol.setuId(deviceid);
		ol.setContent("激活APP成功");
		ol.setType((byte)3);
				
		Integer log = oService.saveOperLog(ol);
		if(log != 1){
			LOG.warn("method (appActive) 保存操作日志失败");
		}
		
		data.put("flag", 1);
		reslut.put("state", 0);
		reslut.put("data", data);
		reslut.put("msg", "success");
		return reslut;
		
	}
	
	/**
	 * 检查版本
	 * @param deviceid
	 * @param vercode
	 * @param verinfo
	 * @param channel
	 * @return
	 */
	@RequestMapping("inapp")
	public JSONObject inapp(HttpServletRequest request){
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
			data.put("flag", 0);
			reslut.put("data",data);
			reslut.put("msg", "服务器错误");
			return reslut;
		}
		
		JSONObject object = JSONObject.parseObject(reportBuilder.toString());
		
		String deviceid = object.getString("deviceid");
		String channel = object.getString("channel");
		String verinfo = object.getString("verinfo");
		Integer vercode = object.getInteger("vercode");
		
		if(StringUtils.isBlank(deviceid) || StringUtils.isBlank(channel) ||
				StringUtils.isBlank(verinfo) || null == vercode
				){
			reslut.put("state", -1);
			data.put("updateflag", 0);
			data.put("downurl","");
			data.put("updateinfo","");
			reslut.put("data",data);
			reslut.put("msg", "参数不能为空");
			return reslut;
		}
		Customer cm = cService.selectCustomerByCid(channel);
		
		if(null == cm){
			reslut.put("state", -1);
			reslut.put("msg", "公司编码有误");
			reslut.put("data", data);
		}
		//查询更新信息
		Version version = vService.selectVersion();
		//没有更新内容
		if(null == version || null == version.getCode() || version.getCode() <= vercode){
			reslut.put("state", 0);
			data.put("updateflag", 0);
			data.put("downurl","");
			data.put("updateinfo","");
			reslut.put("data",data);
			reslut.put("msg", "success");
			return reslut;
		}
		//客户端版本号低
		if(version.getCode() > vercode){
			reslut.put("state", 0);
			data.put("updateflag", version.getForceFlag());
			data.put("downurl",version.getDownUrl());
			data.put("updateinfo",version.getUpdateInfo());
			reslut.put("data",data);
			reslut.put("msg", "success");
		}
		return reslut;
	}
	
}
