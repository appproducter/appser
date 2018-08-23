package com.ruiliang.appsrv.service;

import java.util.List;

import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.pojo.UserInfo;

/**
 * @author LinJian.Liu
 * 
 */
public interface UserInfoService {

	int updateUserType(Byte type,String uid, String cid);
	
	List<UserInfo> selectMgrBycid(String cid);
	
	List<UserInfo> selectPimBycid(String cid);
	
	UserInfo selectUserInfoByUid(String uid);
	
	List<UserInfo> selectPim(String cid);
	
	int updateUserPwd(String pwd, String mobile);
	
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

	Integer updateUpTimePim(Long pimtime,String uid);
	
	Integer updateUpTimeSms(Long smstime,String uid);
	
	Integer updateUpTimeCall(Long calltime,String uid);
	int updateAvatar(String token, String avatar);
	
	int updateUserAuth(Byte type,String uid);
	
	int updateUser(UserInfo userInfo);
}
