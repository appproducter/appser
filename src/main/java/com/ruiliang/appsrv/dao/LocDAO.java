package com.ruiliang.appsrv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.Loc;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface LocDAO {
	
	/**
	 * 保存地理位置信息
	 * @param loc
	 * @return
	 */
	Integer saveLoc(Loc loc);
	
	/**
	 * 根据时间 1天7 天30天 180天 查询地址位置信息
	 * @param uid
	 * @param start
	 * @param end
	 * @return
	 */
	List<Loc> selectLocByUid(@Param("uid") String uid,@Param("start") long start,@Param("end") long end);
}
