package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Version;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface VersionDAO {
	
	/**
	 * 查询版本
	 * @return
	 */
	Version selectVersion(String channel);
}
