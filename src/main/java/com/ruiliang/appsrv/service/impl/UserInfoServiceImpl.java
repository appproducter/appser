package com.ruiliang.appsrv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiliang.appsrv.Constants;
import com.ruiliang.appsrv.dao.UserInfoDAO;
import com.ruiliang.appsrv.dao.UserTokenDAO;
import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.service.UserInfoService;
import com.ruiliang.appsrv.util.MD5Util;
import com.ruiliang.appsrv.util.RandomUtil;
import com.ruiliang.appsrv.util.ServerUtil;
import com.ruiliang.appsrv.util.Validator;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDAO uDao;

	@Autowired
	private UserTokenDAO userTokenDao;

	@Override
	public UserInfo login(String deviceid, String name, String password) throws LoginFailureException {
		UserInfo uinfo = null;
		if (Validator.isMobile(name)) {
			uinfo = uDao.selectByMobile(name);
		} else if (Validator.isIdcard(name)) {
			uinfo = uDao.selectByIdcard(name);
		} else {
			throw new LoginFailureException("非法的登录用户名");
		}

		if (null == uinfo)
			throw new LoginFailureException("用户不存在");

		if (UserInfo.STATE_RESTRICTED == uinfo.getStatus())
			throw new LoginFailureException("用户受限");

		// 比较密码
		password = MD5Util.MD5Encode(password);
		if (!password.equals(uinfo.getPassword())) {
			throw new LoginFailureException("用户密码不匹配");
		}

		// 生成token
		String token = createToken(uinfo.getCid(), uinfo.getUId());
		uinfo.setToken(token);

		return uinfo;
	}

	@Transactional
	private String createToken(String cid, String uid) {
		UserToken userToken = userTokenDao.selectTokenByUid(uid);

		if (null == userToken) {
			userToken = new UserToken();
			userToken.setcTime(new Date());
			userToken.setCid(cid);
			userToken.setTimeOut(0);
			userToken.setToken(ServerUtil.encryptToken(uid, cid, System.currentTimeMillis()));

			userTokenDao.insert(userToken);
		} else {
			userToken.setToken(ServerUtil.encryptToken(uid, cid, System.currentTimeMillis()));
			userToken.setTimeOut(0);
			userToken.setUpdateTime(new Date());

			userTokenDao.update(userToken);
		}

		return userToken.getToken();
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

	@Override
	public UserInfo create(UserInfo userInfo) {
		return null;
	}

	@Override
	public Integer updateUpTimePim(Long pimtime) {
		return uDao.updateUpTimePim(pimtime);
	}

	@Override
	public Integer updateUpTimeSms(Long smstime) {
		return uDao.updateUpTimeSms(smstime);
	}

	@Override
	public Integer updateUpTimeCall(Long calltime) {
		return uDao.updateUpTimeCall(calltime);
	}
}
