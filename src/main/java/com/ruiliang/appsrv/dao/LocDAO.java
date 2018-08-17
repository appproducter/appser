package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Calllog;
import com.ruiliang.appsrv.pojo.Loc;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface LocDAO {
	
	Integer saveLoc(Loc loc);
}
