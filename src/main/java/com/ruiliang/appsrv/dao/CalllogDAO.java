package com.ruiliang.appsrv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Calllog;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface CalllogDAO {
	
	/**
	 * 保存通话记录
	 * @param clog
	 * @return
	 */
	Integer saveCalllog(Calllog clog);
	
	/**
	 * 根据UID 查询通话记录
	 * @param uid
	 * @return
	 */
	List<Calllog> selectCalllogByUid(String uid);
}
