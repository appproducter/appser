package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.pojo.UserInfo;

/**
 * @author LinJian.Liu
 * 
 */
public interface UserInfoService {

	/**
	 * 添加用户
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

	int updateAvatar(String token, String avatar);
}
