package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.VersionDAO;
import com.ruiliang.appsrv.pojo.Version;
import com.ruiliang.appsrv.service.VersionService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class VersionServiceImpl implements VersionService{

	@Autowired
	private VersionDAO vDao;
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.VersionService#selectVersion()
	 */
	@Override
	public Version selectVersion() {
		return vDao.selectVersion();
	}

}
