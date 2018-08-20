package com.ruiliang.appsrv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.UserInfo;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface UserInfoDAO {

	int updateUserType(@Param("type") Byte type,@Param("uid") String uid,@Param("cid") String cid);
	
	List<UserInfo> selectMgrBycid(String cid);
	
	List<UserInfo> selectPimBycid(String cid);
	
	UserInfo selectUserInfoByUid(String uid);
	
	List<UserInfo> selectPim(String cid);
	/**
	 * 根据用户名查询用户
	 * 
	 * @param name
	 * @return
	 */
	UserInfo selectByNameAndPassword(@Param("name") String name, @Param("password") String password);

	/**
	 * 查询uid是否被占用
	 * 
	 * @param uid
	 * @return
	 */
	Integer selectByUserid(@Param("uid") String uid);

	/**
	 * 使用手机查询用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	UserInfo selectByMobile(String mobile);

	/**
	 * 使用身份证查询用户信息
	 * 
	 * @param idcard
	 * @return
	 */
	UserInfo selectByIdcard(String idcard);
	
	Integer updateUpTimePim(@Param("pimtime") Long pimtime,@Param("uid") String uid);
	
	Integer updateUpTimeSms(@Param("smstime") Long smstime,@Param("uid") String uid);
	
	Integer updateUpTimeCall(@Param("calltime") Long calltime,@Param("uid") String uid);

	int insert(UserInfo userInfo); 
	
	int updateUser(UserInfo userInfo);
	
	int updateUserAuth(@Param("type") Byte type,@Param("uid") String uid);

	/**
	 * 更新登录时间及次数
	 * 
	 * @param uid
	 * @return
	 */
	int updateLoginTimes(String uid);

	/**
	 * 更新头像
	 * 
	 * @param uid
	 * @param avatar
	 * @return
	 */
	int updateUserAvatar(@Param("uid") String uid, @Param("avatar") String avatar);
}
