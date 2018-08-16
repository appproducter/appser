package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.UserInfoDAO;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.util.RandomUtil;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDAO uDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruiliang.appsrv.service.UserInfoService#selectByName(java.lang.String)
	 */
	@Override
	public UserInfo selectByName(String name) {
		return uDao.selectByName(name);
	}

	private String generateUserid() {
		String uid = null;
		while (true) {
			uid = RandomUtil.getRandomString(10);
			// 查询是否占用
			Integer n = uDao.selectByUserid(uid);
			if (null == n || 0 == n)
				break;
		}
		return uid;
	}
}
