package com.ruiliang.appsrv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	
	/**sss
	 * 查询用户短信
	 * @param uid
	 * @param time
	 * @return
	 */
	List<Sms> selectSmsByUid(@Param("uid") String uid ,@Param("time") Long time);
}
