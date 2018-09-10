package com.ruiliang.appsrv.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.ImGroup;

@Mapper
public interface ImGroupDAO {

	int getGroupNum(String gid);
	
	/**
	 * 创建聊天群组
	 * @param group
	 * @return
	 */
	Integer create(ImGroup group);

	/**
	 * 组ID 用户ID 保存聊天
	 * @param groupId
	 * @param uid
	 */
	void saveGroupUser(@Param("groupId") String groupId, @Param("uid") String uid);

	/**
	 * 更新群聊名称
	 * @param group
	 */
	void setGroupName(ImGroup group);

	/**
	 * 根据用户ID 查询 群聊信息
	 * @param uid
	 * @return
	 */
	List<ImGroup> listByUid(String uid);

	/**
	 * 添加用户进入群聊
	 * @param uid
	 * @param groupId
	 */
	void addGroupUser(@Param("uid") String uid, @Param("groupId") String groupId);

	/**
	 * 移除用户出群聊
	 * @param uid
	 * @param groupId
	 */
	void removeGroupUser(@Param("uid") String uid, @Param("groupId") String groupId);

	/**
	 * @param id
	 * @return
	 */
	ImGroup selectGroupById(Integer id);
	
	List<ImGroup> listGroup(String uid);
	
	List<Map<String,Object>> listGroupUser(String uid);
}
