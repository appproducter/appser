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
	UserInfo selectByNameAndPassword(@Param("name") String name,@Param("password") String password);

	/**
	 * 查询uid是否被占用
	 * 
	 * @param uid
	 * @return
	 */
	Integer selectByUserid(@Param("uid") String uid);
}
