package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Calllog;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface CalllogDAO {
	
	Integer saveCalllog(Calllog clog);
}
