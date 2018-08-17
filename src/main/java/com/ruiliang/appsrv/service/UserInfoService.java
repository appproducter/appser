package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.pojo.UserInfo;

/**
 * @author LinJian.Liu
 * 
 */
public interface UserInfoService {

	/**
	 * 管理后台添加管理员用户
	 * 
	 * @param userInfo
	 * @return
	 */
	UserInfo create(UserInfo userInfo);

	/**
	 * 客户端管理员添加用户
	 * 
	 * @param userInfo
	 * @return
	 */
	UserInfo create(String token, UserInfo userInfo);

	/**
	 * 登录
	 * 
	 * @param deviceid 登录设备
	 * @param name     可以是手机号或身份证号
	 * @param password 登录密码
	 * @return
	 */
	UserInfo login(String deviceid, String name, String password) throws LoginFailureException;

	Integer updateUpTimePim(Long pimtime);
	
	Integer updateUpTimeSms(Long smstime);
	
	Integer updateUpTimeCall(Long calltime);
	int updateAvatar(String token, String avatar);
}
