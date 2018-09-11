package com.ruiliang.appsrv.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;
import com.ruiliang.appsrv.upload.FileUploadService;
import com.ruiliang.appsrv.util.Base64;

@RestController
@RequestMapping("api/chat/upload")
public class MediaUpController {

	@Autowired
	private UserInfoService uService;
	
	@Autowired
	private UserTokenService utService;
	
	@Autowired
	private FileUploadService fService;
	
	@RequestMapping("mediamsg")
	public JSONObject upLoadMeadia(HttpServletRequest request){
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
		
		
		//文件流
		String filedata = JSONObject.parseObject(da).getString("filedata");
		//文件后缀
		String suffix = JSONObject.parseObject(da).getString("suffix");
		
		if(StringUtils.isBlank(filedata) || StringUtils.isBlank(suffix)){
			reslut.put("state", -1);
			reslut.put("msg", "参数不能为空");
			reslut.put("data", data);
			return reslut;
			
		}
		byte[]b = Base64.decode(filedata);
		InputStream	inputStream = new ByteArrayInputStream(b);
			    
		String chat = "";
		try {
			 chat = fService.copy2Chat(inputStream, suffix, "");
		} catch (Exception e) {
			reslut.put("state", -1);
			reslut.put("msg", "上传失败");
			reslut.put("data", data);
			return reslut;
		}
		data.put("url", chat);
		reslut.put("state", 0);
		reslut.put("msg", "success");
		reslut.put("data", data);
		return reslut;
		
	}
}
