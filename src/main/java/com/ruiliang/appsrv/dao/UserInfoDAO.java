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
	 * @param name
	 * @return
	 */
	UserInfo selectByName(@Param("name") String name);
}
