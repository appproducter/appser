package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.Version;

/**
 * @author LinJian.Liu
 *
 */
public interface VersionService {
	
	/**
	 * 查询版本
	 * @return
	 */
	Version selectVersion(String channel);
}
