package com.ruiliang.appsrv.service;

import java.util.List;

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
	
	List<Sms> selectSmsByUid(String uid,Long time);
}
