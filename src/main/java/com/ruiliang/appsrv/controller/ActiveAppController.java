package com.ruiliang.appsrv.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.DeviceInfo;
import com.ruiliang.appsrv.pojo.Version;
import com.ruiliang.appsrv.service.DeviceInfoService;
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
	public JSONObject appActive(String deviceid,Integer vercode,String verinfo,String brand,
			String model,String os,Integer hpi,Integer wpi,String imei,String imsi,Integer sysversion,
			String channel
			){
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
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
	public JSONObject inapp(String deviceid,Integer vercode,String verinfo,
			String channel
			){
		
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
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
