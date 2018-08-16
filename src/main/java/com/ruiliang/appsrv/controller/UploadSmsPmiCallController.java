package com.ruiliang.appsrv.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.OperLog;
import com.ruiliang.appsrv.pojo.Pim;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.OperLogService;
import com.ruiliang.appsrv.service.PimService;

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
	
	/**
	 * 上传通讯录
	 * @param channel
	 * @param token
	 * @param pim
	 * @return
	 */
	@RequestMapping("pim")
	public JSONObject uploadPim(String channel,String token,String pim){
		
		JSONObject reslut = new JSONObject();
		JSONObject data = new JSONObject();
		
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
		
		Pim pm = new Pim();
		pm.setPim(pim);
		pm.setuId("");
		
		Integer savePim = pService.savePim(pm);
		
		if(savePim != 1){
			reslut.put("state", -1);
			reslut.put("msg", "上传失败");
			reslut.put("data", data);
			return reslut;
		}
		
		//记录操作日志
		OperLog ol = new OperLog();
		ol.setuId("");
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
