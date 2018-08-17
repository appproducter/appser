package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.UserTokenDAO;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserTokenService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class UserTokenServiceImpl implements UserTokenService{

	@Autowired
	private UserTokenDAO uDao;
	
	@Override
	public void insert(UserToken userToken) {
		uDao.insert(userToken);
	}

	@Override
	public UserToken findByToken(String token) {
		return uDao.findByToken(token);
	}

	@Override
	public void update(UserToken userToken) {
		uDao.update(userToken);
	}

	@Override
	public UserToken selectTokenByUid(String uid) {
		return uDao.selectTokenByUid(uid);
	}

	@Override
	public int updateTokenByUid(String token, String uid) {
		return uDao.updateTokenByUid(token, uid);
	}

}
