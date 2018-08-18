package com.ruiliang.appsrv.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.util.UploadUtil;

/**
 * @author LinJian.Liu
 *
 */
@RestController
@RequestMapping("api/user")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("setavatar")
	@Deprecated
	public JSONObject setavatar(HttpServletRequest request){
		JSONObject obj = UploadUtil.uploadImg(request) ;
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
		//查看用户是否存在
		String imgUrl = null;
		
		//不存在
		
		
		if(obj == null){
			data.put("reslut", -1);
			data.put("msg", "fail");
			reslut.put("state", -1);
			reslut.put("msg", "图像上传失败");
			reslut.put("data", data);
			return reslut;
		}
		int error = obj.getIntValue("state") ;
		if(error!=0){
			data.put("reslut", -1);
			data.put("msg", "fail");
			reslut.put("state", -1);
			reslut.put("msg", "图像上传失败");
			reslut.put("data", data);
			return reslut;
		}
		imgUrl = obj.getString("url") ;
		System.out.println("vvvvvvvvv"+imgUrl);
		return null;
		
	}
	
}
