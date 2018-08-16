package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Sms;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface SmsDAO {
	
	/**
	 * 保存短信
	 * @param sm
	 * @return
	 */
	Integer saveSms(Sms sm);
}
