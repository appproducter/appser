package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.Sms;

/**
 * @author LinJian.Liu
 *
 */
public interface SmsLogService {

	
	/**
	 * 保存短信
	 * @param sm
	 * @return
	 */
	Integer saveSms(Sms sm);
}
