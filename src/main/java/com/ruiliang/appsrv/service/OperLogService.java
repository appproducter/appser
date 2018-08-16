package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.OperLog;

/**
 * @author LinJian.Liu
 *
 */
public interface OperLogService {

	
	/**
	 * 保存操作日志
	 * @param ol
	 * @return
	 */
	Integer saveOperLog(OperLog ol);
}
