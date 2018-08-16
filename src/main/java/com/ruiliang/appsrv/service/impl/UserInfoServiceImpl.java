package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.UserInfoDAO;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.service.UserInfoService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoDAO uDao;
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.UserInfoService#selectByName(java.lang.String)
	 */
	@Override
	public UserInfo selectByNameAndPassword(String name,String password) {
		return uDao.selectByNameAndPassword(name,password);
	}

}
