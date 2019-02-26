package com.ruiliang.appsrv.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.ImGroup;

public interface ImGroupService {
	int dismissGroup(String git);
	int getGroupNum(String gid);
	ImGroup getGroupUser(String uid);
	List<ImGroup> listGroup(String uid);
	int deleteGroup(String gid,String uid);
	List<Map<String,Object>> listGroupUser(String uid);
	
	ImGroup selectGroupById(Integer id);
	/**
	 * 添加群
	 * 
	 * @param creator
	 * @param users
	 * @return
	 */
	ImGroup create(String creator, List<String> users) throws Exception;

	/**
	 * 设置群名称（创建者）
	 * 
	 * @param uid
	 * @param groupId
	 * @param groupName
	 */
	void setGroupName(String uid, String groupId, String groupName) throws Exception;

	/**
	 * 用户所有群列表
	 * 
	 * @param uid
	 * @return
	 */
	List<ImGroup> listByUser(String uid);

	/**
	 * 添加用户入群
	 * 
	 * @param group_id
	 * @param group_user
	 */
	void addGroupUser(String group_id, String group_user);

	/**
	 * 移除群组用户
	 * 
	 * @param group_id
	 * @param group_user
	 */
	void removeGroupUser(String group_id, String group_user);
}
