package com.ruiliang.appsrv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.SmsDAO;
import com.ruiliang.appsrv.pojo.Sms;
import com.ruiliang.appsrv.service.SmsLogService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class SmsLogServiceImpl implements SmsLogService{

	
	@Autowired
	private SmsDAO sDao;
	
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.SmsService#saveSms(com.ruiliang.appsrv.pojo.Sms)
	 */
	@Override
	public Integer saveSms(Sms sm) {
		return sDao.saveSms(sm);
	}


	@Override
	public List<Sms> selectSmsByUid(String uid,Long time) {
		return sDao.selectSmsByUid(uid,time);
	}

}
