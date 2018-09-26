package com.ruiliang.appsrv.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;
import com.ruiliang.appsrv.upload.FileUploadService;
import com.ruiliang.appsrv.util.Base64;
import com.ruiliang.appsrv.util.CharUtils;
import com.ruiliang.appsrv.util.DateUtil;

/**
 * @author LinJian.Liu
 *
 * 设置头像
 */
@RestController
@RequestMapping("api/user")
public class UserController {

	private  String dateTimePath = DateUtil.getFormatDateString(new Date(), "yyyyMMdd") ;
	
	@Value("${image.server}")
	private String imageServer ;
	/**支持上传的图片格式****/
	private static final String[] imgSuffix = {"gif","jpg","png","jpeg","bmp"} ;
	/***支持图片的最大大小***/
	private static long maxSize = 20480;  //2M
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Value("${appurl}")
	private String appurl;
	
	@Autowired
	private UserInfoService uService;
	
	@Autowired
	private FileUploadService fService;
	
	@Autowired
	private UserTokenService utService;
	
	@RequestMapping("setavatar")
	public JSONObject setavatar(HttpServletRequest request){
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
		if(null == userToken){
			reslut.put("state", -1);
			reslut.put("msg", "无效的token");
			reslut.put("data", data);
			return reslut;
		}
		UserInfo userInfo = uService.selectUserInfoByUid(userToken.getuId());
		
		if(null == userInfo){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		//获取图片字符串
		String imgString = JSONObject.parseObject(da).getString("avatar");
		
		//图片后缀
		String suffix = JSONObject.parseObject(da).getString("suffix");
		if(!CharUtils.contains(imgSuffix, suffix)){
			reslut.put("state", -1);
			reslut.put("msg", "图像格式错误");
			reslut.put("data", data);
			return reslut;
		}
		if(null == imgString){
			reslut.put("state", -1);
			reslut.put("msg", "图像为空");
			reslut.put("data", data);
			return reslut;
		}
		byte[]b = Base64.decode(imgString);
		InputStream	inputStream = new ByteArrayInputStream(b);
		String chat = "";
		try {
			 chat = fService.copy2Avatar(inputStream, suffix, "");
		} catch (Exception e) {
			reslut.put("state", -1);
			reslut.put("msg", "上传失败");
			reslut.put("data", data);
			return reslut;
		}
		
		int avatar = uService.updateAvatar(userInfo.getUId(), chat);
		if(avatar != 1){
			reslut.put("state", -1);
			reslut.put("msg", "图像更新失败");
			reslut.put("data", data);
			return reslut;
		}
		data.put("imgurl", chat);
		data.put("result", 0);
		data.put("msg", "success");
		reslut.put("state", 0);
		reslut.put("msg", "图像更新成功");
		reslut.put("data", data);
		return reslut;
	}
	
	@RequestMapping("getAvatarById")
	public JSONObject getAvatarById(HttpServletRequest request){
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
		if(null == userToken){
			reslut.put("state", -1);
			reslut.put("msg", "无效的token");
			reslut.put("data", data);
			return reslut;
		}
		UserInfo userInfo = uService.selectUserInfoByUid(userToken.getuId());
		
		if(null == userInfo){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		//
		String id = JSONObject.parseObject(da).getString("uid");
		
		if(StringUtils.isBlank(id)){
			reslut.put("state", -1);
			reslut.put("msg", "获取图像失败");
			reslut.put("data", data);
			return reslut;
		}else{
			Map<String,String> m = uService.selectAvatar(id);
			if(null == m || m.isEmpty()){
				reslut.put("state", -1);
				reslut.put("msg", "图像为空");
				reslut.put("data", data);
				return reslut;
			}
			data.put("imgurl", m.get("avatar"));
			data.put("result", 0);
			data.put("msg", "success");
			reslut.put("state", 0);
			reslut.put("msg", "获取图像成功");
			reslut.put("data", data);
			return reslut;
		}
		
	}
	
	/*@RequestMapping("setavatar")
	public JSONObject setavatar(HttpServletRequest request){
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
		if(null == userToken){
			reslut.put("state", -1);
			reslut.put("msg", "无效的token");
			reslut.put("data", data);
			return reslut;
		}
		UserInfo userInfo = uService.selectUserInfoByUid(userToken.getuId());
		
		if(null == userInfo){
			reslut.put("state", -1);
			reslut.put("msg", "用户不存在");
			reslut.put("data", data);
			return reslut;
		}
		
		//获取图片字符串
		String imgString = JSONObject.parseObject(da).getString("avatar");
		
		int avatar = uService.updateAvatar(userInfo.getUId(), imgString);
		if(avatar != 1){
			reslut.put("state", -1);
			reslut.put("msg", "图像上传失败");
			reslut.put("data", data);
			return reslut;
		}
		data.put("imgurl", imgString);
		data.put("result", 0);
		data.put("msg", "success");
		reslut.put("state", 0);
		reslut.put("msg", "图像更新成功");
		reslut.put("data", data);
		return reslut;
	}*/
}
