package com.ruiliang.appsrv.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONObject;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.service.UserTokenService;
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

	private static final String dateTimePath = DateUtil.getFormatDateString(new Date(), "yyyyMMdd") ;
	
	/***上传图片的保存地址***/
	//private static final String storagePath = "/images/user_img" ;
	private static final String storagePath = "E:/img" ;
	/***上传图片的保存地址***/
	//private static final String uploadPath = "/data"+ storagePath +"/"+ dateTimePath + "/" ;
	private static final String uploadPath =  storagePath +"/"+ dateTimePath + "/" ;
	/**支持上传的图片格式****/
	private static final String[] imgSuffix = {"gif","jpg","png","jpeg","bmp"} ;
	/***支持图片的最大大小***/
	private static long maxSize = 2048;  //2M
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Value("${appurl}")
	private String appurl;
	
	@Autowired
	private UserInfoService uService;
	
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
		String imgurl = uploadPath+userInfo.getUId()+"."+suffix;
	    BASE64Decoder decoder = new BASE64Decoder();
	    byte[] b;
		try {
			b = decoder.decodeBuffer(imgString);
			long size=(long)b.length/1024;
			if(size > maxSize){
				reslut.put("state", -1);
				reslut.put("msg", "图像大小超过限制");
				reslut.put("data", data);
				return reslut;
		    }
			for(int i = 0; i < b.length; ++i) {
			       if (b[i] < 0) {
			             b[i] += 256;
			         }
			    
			    OutputStream out = new FileOutputStream(imgurl);
			    out.write(b);
			    out.flush();
			    out.close();
			    
			}
		} catch (IOException e) {
			reslut.put("state", -1);
			reslut.put("msg", "图像上传失败");
			reslut.put("data", data);
			return reslut;
		}
		int avatar = uService.updateAvatar(userInfo.getUId(), imgurl);
		if(avatar != 1){
			reslut.put("state", -1);
			reslut.put("msg", "图像上传失败");
			reslut.put("data", data);
			return reslut;
		}
		data.put("imgurl", appurl+"/"+imgurl);
		data.put("result", 0);
		data.put("msg", "success");
		reslut.put("state", 0);
		reslut.put("msg", "图像更新成功");
		reslut.put("data", data);
		return reslut;
	}
}
