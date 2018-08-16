package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.UserToken;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface UserTokenDAO {

	void insert(UserToken userToken);

	UserToken findByToken(String token);

	void update(UserToken userToken);

	/**
	 * @return
	 */
	UserToken selectTokenByUid(@Param("uid") String uid);

	/**
	 * @param token
	 * @param uid
	 * @return
	 */
	int updateTokenByUid(@Param("token") String token, @Param("uid") String uid);
}
