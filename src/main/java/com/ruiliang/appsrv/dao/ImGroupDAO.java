package com.ruiliang.appsrv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.ImGroup;

@Mapper
public interface ImGroupDAO {

	ImGroup create(ImGroup group);

	void saveGroupUser(@Param("groupId") String groupId, @Param("uid") String uid);

	void setGroupName(ImGroup group);

	List<ImGroup> listByUid(String uid);
	
	void addGroupUser(@Param("uid") String uid, @Param("groupId") String groupId);

	void removeGroupUser(@Param("uid") String uid, @Param("groupId") String groupId);
}
