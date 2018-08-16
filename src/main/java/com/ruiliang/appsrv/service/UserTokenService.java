package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.UserToken;

/**
 * @author LinJian.Liu
 *
 */
public interface UserTokenService {

	void insert(UserToken userToken);

	UserToken findByToken(String token);

	void update(UserToken userToken);

	UserToken selectTokenByUid(String uid);

	int updateTokenByUid(String token, String uid);
}
