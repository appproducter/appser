package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.LocDAO;
import com.ruiliang.appsrv.pojo.Loc;
import com.ruiliang.appsrv.service.LocService;

@Service
public class LocServiceImpl implements LocService{

	
	@Autowired
	private LocDAO lDao;
	
	@Override
	public Integer saveLoc(Loc clog) {
		return lDao.saveLoc(clog);
	}

}
