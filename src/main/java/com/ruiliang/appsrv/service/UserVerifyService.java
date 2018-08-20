package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.exception.SendCodeFailureException;
import com.ruiliang.appsrv.pojo.UserInfo;

public interface UserVerifyService {

	/**
	 * 验证类型：重置密码
	 */
	int VERIFY_TYPE_RESETPASSWD = 1;

	/**
	 * 
	 * @param user       当前用户
	 * @param dest       接收验证码目标
	 * @param type       发送方式
	 * @param verifyType 验证码类型
	 * @throws SendCodeFailureException
	 */
	void sendCode(UserInfo user, String dest, int type, int verifyType) throws SendCodeFailureException;

	/**
	 * 校验验证码
	 * 
	 * @param dest       接收验证码目标
	 * @param code       验证码
	 * @param type       发送方式
	 * @param verifyType 验证类型
	 * @return
	 * @throws SendCodeFailureException
	 */
	boolean checkCode(String dest, String code, int type, int verifyType) throws SendCodeFailureException;

	/**
	 * 更新验证码使用状态
	 * 
	 * @param id
	 * @param flag
	 */
	int updateStatus(Long id, int useFlag);
}
