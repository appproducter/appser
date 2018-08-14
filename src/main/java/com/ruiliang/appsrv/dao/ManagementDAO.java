package com.ruiliang.appsrv.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface ManagementDAO {
	/**
	 * 保存用户
	 * @param obj
	 * @return
	 */
	Map<String,Object> getResult();
}
