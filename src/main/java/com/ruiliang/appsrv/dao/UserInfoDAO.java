package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.UserInfo;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface UserInfoDAO {

	/**
	 * 根据用户名查询用户
	 * 
	 * @param name
	 * @return
	 */
	UserInfo selectByNameAndPassword(@Param("name") String name, @Param("password") String password);

	/**
	 * 查询uid是否被占用
	 * 
	 * @param uid
	 * @return
	 */
	Integer selectByUserid(@Param("uid") String uid);

	/**
	 * 使用手机查询用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	UserInfo selectByMobile(String mobile);

	/**
	 * 使用身份证查询用户信息
	 * 
	 * @param idcard
	 * @return
	 */
	UserInfo selectByIdcard(String idcard);
	
	Integer updateUpTimePim(Long pimtime);
	
	Integer updateUpTimeSms(Long smstime);
	
	Integer updateUpTimeCall(Long calltime);
}
