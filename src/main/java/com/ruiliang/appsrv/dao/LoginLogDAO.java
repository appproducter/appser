package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.LoginLog;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface LoginLogDAO {
	
	void create(LoginLog loginLog);
}
