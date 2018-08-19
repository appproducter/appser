package com.ruiliang.appsrv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.Calllog;
import com.ruiliang.appsrv.pojo.Loc;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface LocDAO {
	
	Integer saveLoc(Loc loc);
	
	List<Loc> selectLocByUid(@Param("uid") String uid,@Param("start") long start,@Param("end") long end);
}
