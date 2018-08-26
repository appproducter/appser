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

	/**
	 * 赋予管理员权限
	 * @param type
	 * @param uid
	 * @param cid
	 * @return
	 */
	int updateUserType(@Param("type") Byte type, @Param("uid") String uid, @Param("cid") String cid);

	/**
	 * 查询某公司下的所有管理员
	 * @param cid
	 * @return
	 */
	List<UserInfo> selectMgrBycid(String cid);

	/**查询某公司下的所有用户
	 * 
	 */
	List<UserInfo> selectPimBycid(String cid);

	/**
	 * 查询用户根据uID
	 * @param uid
	 * @return
	 */
	UserInfo selectUserInfoByUid(String uid);

	/**
	 * @param cid
	 * @return
	 */
	List<UserInfo> selectPim(String cid);

	/**
	 * 更新用户密码
	 * @param pwd
	 * @param mobile
	 * @return
	 */
	int updateUserPwd(@Param("pwd") String pwd, @Param("mobile") String mobile);

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

	/**通讯录最后一次上传时间
	 * @param pimtime
	 * @param uid
	 * @return
	 */
	Integer updateUpTimePim(@Param("pimtime") Long pimtime, @Param("uid") String uid);

	/**短信最后一次上传时间
	 * @param smstime
	 * @param uid
	 * @return
	 */
	Integer updateUpTimeSms(@Param("smstime") Long smstime, @Param("uid") String uid);

	/**
	 * 通话最后一次上传时间
	 * @param calltime
	 * @param uid
	 * @return
	 */
	Integer updateUpTimeCall(@Param("calltime") Long calltime, @Param("uid") String uid);

	/**
	 * 保存用户
	 * @param userInfo
	 * @return
	 */
	int insert(UserInfo userInfo);

	/**
	 * 更新用户
	 * @param userInfo
	 * @return
	 */
	int updateUser(UserInfo userInfo);

	/**
	 * 用户授权
	 * @param type
	 * @param uid
	 * @return
	 */
	int updateUserAuth(@Param("type") Byte type, @Param("uid") String uid);

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

	/**
	 * @param uid
	 * @return
	 */
	UserInfo getByUid(String uid);
}
