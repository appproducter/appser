package com.ruiliang.appsrv.service;

import java.util.List;

import com.ruiliang.appsrv.pojo.Loc;

/**
 * @author LinJian.Liu
 *
 */
public interface LocService {

	Integer saveLoc(Loc clog);
	
	List<Loc> selectLocByUid(String uid, long start, long end);
}
