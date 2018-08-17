package com.ruiliang.appsrv.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;


/**
 * @author LinJian.Liu
 *
 */
public class UploadUtil {  

	private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);
	
	private static final String dateTimePath = DateUtil.getFormatDateString(new Date(), "yyyyMMdd") ;
	
	/***上传图片的保存地址***/
	//private static final String storagePath = "/images/user_img" ;
	private static final String storagePath = "E:\\img" ;
	/***上传图片的保存地址***/
	//private static final String uploadPath = "/data"+ storagePath +"/"+ dateTimePath + "/" ;
	private static final String uploadPath =  storagePath +"/"+ dateTimePath + "/" ;
	/**支持上传的图片格式****/
	private static final String[] imgSuffix = {".gif",".jpg",".png",".jpeg",".bmp"} ;
	/***支持图片的最大大小***/
	private static long maxSize = 2048;  //2M
	
	public static JSONObject uploadImg(HttpServletRequest request ){
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		JSONObject obj =new JSONObject();

		//判断 request 是否有文件上传,即多部分请求
		if(!multipartResolver.isMultipart(request)){
			obj.put("message","上传失败,请重新上传！");
			obj.put("state", -1);
			return obj;
		}
		// 单个上传文件请求
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		// 取得上传文件
		MultipartFile file = multiRequest.getFile(multiRequest.getFileNames().next());
		if (file == null) {
			obj.put("message","上传失败,请重新上传！");
			obj.put("state", -1);
			return obj;
		}

		// 取得当前上传文件的文件名称
		String myFileName = file.getOriginalFilename().toLowerCase(); 
		// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
		if ( org.apache.commons.lang3.StringUtils.isBlank(myFileName.trim())) {
			obj.put("message","上传失败,请重新上传！");
			obj.put("state", -1);
			return obj;
		}	 
		/****根据后缀名判断图片类型****/
		String suffix = myFileName.substring(myFileName.lastIndexOf(".")) ;
		
		if(!CharUtils.contains(imgSuffix, suffix)){
			obj.put("message","图片格式不支持");
			obj.put("state",-1);
			return obj;
		}
		/*****文件类型不是图片返回出错信息****/
		if(!file.getContentType().startsWith("image")){
			obj.put("error","上传的非正规图片格式");
			obj.put("state",-1);
			return obj;
		}
		long size=(long)file.getSize()/1024;
		if(size > maxSize){
	    	obj.put("message", "文件大小超过限制");
	    	obj.put("error", 1);
	    	return obj;
	    }
	    String url = storagePath + "/" + dateTimePath + "/" ;
		// 重命名上传后的文件名
		String fileName = Calendar.getInstance().getTimeInMillis() + suffix ;
		try {
			// 定义上传路径
			String path = uploadPath ;
			File f = new File(path);
			if(!f.exists()){
				f.mkdirs();
			}
			path += fileName; 
			File localFile = new File(path);
			file.transferTo(localFile);
			obj.put("url",url+fileName);
		} catch (Exception e) {
			logger.error("上传文件出错！", e);
			obj.put("message","上传失败,请重新上传！");
			obj.put("state", -1);
			return obj;
		}
		obj.put("msg","success");
		obj.put("state",0);
		return obj ;
	}
}
