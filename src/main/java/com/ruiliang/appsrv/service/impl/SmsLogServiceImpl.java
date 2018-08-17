package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.SmsDAO;
import com.ruiliang.appsrv.pojo.Sms;
import com.ruiliang.appsrv.service.SmsService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class SmsServiceImpl implements SmsService{

	
	@Autowired
	private SmsDAO sDao;
	
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.SmsService#saveSms(com.ruiliang.appsrv.pojo.Sms)
	 */
	@Override
	public Integer saveSms(Sms sm) {
		return sDao.saveSms(sm);
	}

}
