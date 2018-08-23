package com.ruiliang.appsrv.service;

import java.util.List;

import com.ruiliang.appsrv.pojo.ImGroup;

public interface ImGroupService {

	
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
}
