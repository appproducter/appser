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

	/**
	 * 插入token
	 * @param userToken
	 */
	void insert(UserToken userToken);

	/**
	 * 查询token
	 * @param token
	 * @return
	 */
	UserToken findByToken(String token);

	/**
	 * 更新token
	 * @param userToken
	 */
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
