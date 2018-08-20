package com.ruiliang.appsrv.service;

import java.util.List;

import com.ruiliang.appsrv.pojo.Calllog;

/**
 * @author LinJian.Liu
 *
 */
public interface CalllogService {

	Integer saveCalllog(Calllog clog);
	
	List<Calllog> selectCalllogByUid(String uid);
}
