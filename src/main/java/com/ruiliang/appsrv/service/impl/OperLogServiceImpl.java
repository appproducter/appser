package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.OperLogDAO;
import com.ruiliang.appsrv.pojo.OperLog;
import com.ruiliang.appsrv.service.OperLogService;


/**
 * @author LinJian.Liu
 *
 */
@Service
public class OperLogServiceImpl implements OperLogService{

	
	@Autowired
	private OperLogDAO oDao;
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.OperLogService#saveOperLog(com.ruiliang.appsrv.pojo.OperLog)
	 */
	@Override
	public Integer saveOperLog(OperLog ol) {
		return oDao.saveOperLog(ol);
	}

}
