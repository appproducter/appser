package com.ruiliang.appsrv.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ruiliang.appsrv.dao.UserInfoDAO;
import com.ruiliang.appsrv.dao.UserTokenDAO;
import com.ruiliang.appsrv.dao.UserVerifyLogDAO;
import com.ruiliang.appsrv.exception.LoginFailureException;
import com.ruiliang.appsrv.exception.SendCodeFailureException;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserToken;
import com.ruiliang.appsrv.pojo.UserVerifyLog;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.service.SmsService;
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

	@Autowired
	@Qualifier("tencentSmsService")
	private SmsService smsService;

	@Autowired
	private UserVerifyLogDAO userVerifyLogDao;

	@Override
	public UserInfo login(String deviceid, String name, String password) throws LoginFailureException {
		UserInfo uinfo = null;
		//if (Validator.isMobile(name)) {
		//	uinfo = uDao.selectByMobile(name);
		//} else if (Validator.isIdcard(name)) {
		//	uinfo = uDao.selectByIdcard(name);
		//} else {
		//	throw new LoginFailureException("非法的登录用户名");
		//}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(name)){
			uinfo = uDao.selectByMobile(name);
		}else{
			throw new LoginFailureException("非法的登录用户名");
		}
		if(null == uinfo){
			uinfo = uDao.selectByIdcard(name);
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

		// 更新登录时间次数
		uDao.updateLoginTimes(uinfo.getUId());

		return uinfo;
	}

	private String createToken(String cid, String uid) {
		UserToken userToken = userTokenDao.selectTokenByUid(uid);

		if (null == userToken) {
			userToken = new UserToken();
			userToken.setcTime(new Date());
			userToken.setCid(cid);
			userToken.setTimeOut(0);
			userToken.setuId(uid);
			userToken.setToken(ServerUtil.encryptToken(uid, cid, System.currentTimeMillis()));

			userTokenDao.insert(userToken);
		} else {
			userToken.setToken(ServerUtil.encryptToken(uid, cid, System.currentTimeMillis()));
			userToken.setTimeOut(0);
			userToken.setUpdateTime(new Date());
			userToken.setuId(uid);
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
	public UserInfo create(String token, UserInfo userInfo) {
		// 查询token
		UserToken userToken = userTokenDao.findByToken(token);
		if (null == userToken)
			throw new RuntimeException("非法操作");

		// 查询手机和身份证是否存在
		UserInfo tmp = uDao.selectByMobile(userInfo.getMobile());
		UserInfo tmp2 = uDao.selectByIdcard(userInfo.getIdCard());
		if (tmp != null)
			throw new RuntimeException("手机号已存在");
		if (tmp2 != null)
			throw new RuntimeException("身份证已存在");

		userInfo.setCid(userToken.getCid());
		if (null == userInfo.getUId()) {
			String uid = generateUserid();
			if (StringUtils.isEmpty(uid))
				throw new RuntimeException("uid系统出错");
			userInfo.setUId(uid);
		}

		if (null == userInfo.getcTime())
			userInfo.setcTime(new Date());

		// 密码MD5
		userInfo.setPassword(MD5Util.MD5Encode(userInfo.getPassword()));

		uDao.insert(userInfo);

		// 添加成功 发送短信
		try {
			UserVerifyLog verifyLog = new UserVerifyLog();
			verifyLog.setCode(RandomUtil.getRandomNum(6));
			verifyLog.setSendTime(new Date());
			verifyLog.setDest(userInfo.getMobile());
			verifyLog.setStatus(UserVerifyLog.FLAG_DEFAULT);
			verifyLog.setType(UserVerifyLog.TYPE_SMS);
			verifyLog.setVerifyType(UserVerifyLog.VERIFY_TYPE_REGNOTIFY);
			verifyLog.setUserid(userInfo.getUId());
			verifyLog.setUseFlag(1);

			this.userVerifyLogDao.create(verifyLog);

			this.smsService.send(UserVerifyLog.VERIFY_TYPE_REGNOTIFY, userInfo.getMobile(),
					new String[] { userInfo.getMobile(), "000000" });
		} catch (Exception e) {
			e.printStackTrace();
			// throw new SendCodeFailureException("发送出错");
		}

		return userInfo;
	}

	@Override
	public int updateAvatar(String token, String avatar) {

		return uDao.updateUserAvatar(token, avatar);
	}

	public Integer updateUpTimePim(Long pimtime, String uid) {
		return uDao.updateUpTimePim(pimtime, uid);
	}

	@Override
	public Integer updateUpTimeSms(Long smstime, String uid) {
		return uDao.updateUpTimeSms(smstime, uid);
	}

	@Override
	public Integer updateUpTimeCall(Long calltime, String uid) {
		return uDao.updateUpTimeCall(calltime, uid);
	}

	@Override
	public int updateUser(UserInfo userInfo) {
		return uDao.updateUser(userInfo);
	}

	@Override
	public int updateUserAuth(Byte type, String uid) {
		return uDao.updateUserAuth(type, uid);
	}

	@Override
	public List<UserInfo> selectMgrBycid(String cid) {
		return uDao.selectMgrBycid(cid);
	}

	@Override
	public List<UserInfo> selectPimBycid(String cid) {
		return uDao.selectPimBycid(cid);
	}

	@Override
	public int updateUserType(Byte type, String uid, String cid) {
		return uDao.updateUserType(type, uid, cid);
	}

	@Override
	public UserInfo selectUserInfoByUid(String uid) {
		return uDao.selectUserInfoByUid(uid);
	}

	@Override
	public List<UserInfo> selectPim(String cid) {
		return uDao.selectPim(cid);
	}

	@Override
	public int updateUserPwd(String pwd, String mobile) {
		return uDao.updateUserPwd(pwd, mobile);
	}

	@Override
	public List<UserInfo> selectUByIds(List<String> Ids) {
		return uDao.selectUByIds(Ids);
	}

	@Override
	public Map<String, String> selectAvatar(String id) {
		return uDao.selectAvatar(id);
	}

	@Override
	public void deleteUser(String[] ids,String cid) throws Exception{
		// TODO Auto-generated method stub
		uDao.deleteUser(ids,cid);
	}
}
