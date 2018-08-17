package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.CalllogDAO;
import com.ruiliang.appsrv.pojo.Calllog;
import com.ruiliang.appsrv.service.CalllogService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class CalllogServiceImpl implements CalllogService{

	@Autowired
	private CalllogDAO cDao;
	
	@Override
	public Integer saveCalllog(Calllog clog) {
		return cDao.saveCalllog(clog);
	}
	
}
