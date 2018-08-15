package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.UserInfo;

/**
 * @author LinJian.Liu
 * 
 */
public interface UserInfoService {
	
	/**
	 * 根据用户名查询用户
	 * @param name
	 * @return
	 */
	UserInfo selectByName(String name);
	
}
