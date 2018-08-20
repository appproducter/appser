package com.ruiliang.appsrv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ruiliang.appsrv.dao.UserVerifyLogDAO;
import com.ruiliang.appsrv.exception.SendCodeFailureException;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.pojo.UserVerifyLog;
import com.ruiliang.appsrv.service.SmsService;
import com.ruiliang.appsrv.service.UserVerifyService;
import com.ruiliang.appsrv.util.CalendarUtil;
import com.ruiliang.appsrv.util.RandomUtil;

public class UserVerifyServiceImpl implements UserVerifyService {

	@Autowired
	private UserVerifyLogDAO userVerifyLogDao;

	@Qualifier("tencentSmsService")
	private SmsService smsService;

	@Override
	public void sendCode(UserInfo user, String dest, int type, int verifyType) throws SendCodeFailureException {
		// 频率校验
		// dest发送频率校验 调取今日短信发送记录
		String today = CalendarUtil.getFormatDateTime(new Date(), "yyyy-MM-dd");
		if (UserVerifyLog.TYPE_SMS == type) {
			List<UserVerifyLog> smslogs = userVerifyLogDao.loadSmsSendLogByDate(today, dest);
			if (!smsSendRole(smslogs))
				throw new SendCodeFailureException("短信发送太频繁, 请稍候再试");
		}

		UserVerifyLog verifyLog = new UserVerifyLog();
		verifyLog.setCode(RandomUtil.getRandomNum(6));
		verifyLog.setSendTime(new Date());
		verifyLog.setDest(dest);
		verifyLog.setStatus(UserVerifyLog.FLAG_DEFAULT);
		verifyLog.setType(type);
		verifyLog.setVerifyType(verifyType);
		verifyLog.setUserid(user.getUId());

		Long id = this.userVerifyLogDao.create(verifyLog);
		verifyLog.setId(id);
		try {
			switch (type) {
			case UserVerifyLog.TYPE_SMS:
				sendSmsCode(verifyLog);
				break;
			}

			updateStatus(verifyLog.getId(), UserVerifyLog.FLAG_SUCCESS);
		} catch (SendCodeFailureException e) {
			updateStatus(verifyLog.getId(), UserVerifyLog.FLAG_FAILURE);
			throw e;
		}
	}

	public void sendSmsCode(UserVerifyLog verifyLog) throws SendCodeFailureException {
		// String msg = "{\"num\":'" + verifyLog.getCode() + "'}";
		String msg = verifyLog.getCode();
		try {
			this.smsService.send(verifyLog.getType(), verifyLog.getDest(), msg);
		} catch (Exception e) {
			throw new SendCodeFailureException("发送出错");
		}

	}

	private boolean smsSendRole(List<UserVerifyLog> smslogs) {
		boolean b = true;

		if (null == smslogs || smslogs.isEmpty())
			return b;

		// 30秒内限制1条
		Date lastSendTime = smslogs.get(smslogs.size() - 1).getSendTime();
		if ((System.currentTimeMillis() - lastSendTime.getTime()) < 1000l * 30) {
			return false;
		}

		// 1小时内限制10条
		int count = getCountByHour(smslogs);
		if (count > 10) {
			return false;
		}

		// 1天内限制20条
		if (smslogs.size() > 20)
			return false;

		return b;
	}

	private int getCountByHour(List<UserVerifyLog> logs) {
		int n = 0;
		long now = System.currentTimeMillis();
		for (UserVerifyLog log : logs) {
			Date tmp = log.getSendTime();

			if (now - tmp.getTime() < 1000 * 60 * 60)
				n++;
		}

		return n;
	}

	@Override
	public boolean checkCode(String dest, String code, int type, int verifyType) throws SendCodeFailureException {
		UserVerifyLog verifyLog = this.userVerifyLogDao.findLastByDest(dest, type, verifyType);

		if (verifyLog == null)
			throw new SendCodeFailureException("记录不存在");

		if (!code.equalsIgnoreCase(verifyLog.getCode()))
			throw new SendCodeFailureException("验证码不正确");

		// TODO 修改使用标志 验证码输入正确、超过有效期
		userVerifyLogDao.updateUsedFlag(verifyLog.getId(), 1);

		switch (verifyLog.getType()) {
		case UserVerifyLog.TYPE_SMS:
			if (System.currentTimeMillis() - verifyLog.getSendTime().getTime() >= UserVerifyLog.TIME_OUT_SMS * 1000
					* 60l) {
				throw new SendCodeFailureException("验证码超过有效期");
			}

			break;
		case UserVerifyLog.TYPE_EMAIL:
			if (System.currentTimeMillis() - verifyLog.getSendTime().getTime() >= UserVerifyLog.TIME_OUT_EMAIL * 1000
					* 60l) {
				throw new SendCodeFailureException("验证码超过有效期");
			}

			break;
		}

		return true;
	}

	@Override
	public int updateStatus(Long id, int useFlag) {
		return userVerifyLogDao.updateUsedFlag(id, useFlag);
	}

}
