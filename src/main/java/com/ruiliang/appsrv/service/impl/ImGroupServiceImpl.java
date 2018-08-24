package com.ruiliang.appsrv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiliang.appsrv.dao.ImGroupDAO;
import com.ruiliang.appsrv.dao.UserInfoDAO;
import com.ruiliang.appsrv.pojo.ImGroup;
import com.ruiliang.appsrv.pojo.UserInfo;
import com.ruiliang.appsrv.service.ImGroupService;
import com.ruiliang.appsrv.util.RandomUtil;

@Service
public class ImGroupServiceImpl implements ImGroupService {

	@Autowired
	private ImGroupDAO imGroupDao;

	@Autowired
	private UserInfoDAO userDao;

	@Override
	@Transactional
	public ImGroup create(String creator, List<String> users) throws Exception {
		if (null == users || users.isEmpty() || users.size() < 2)
			throw new Exception("请添加用户入群");
		// 加载用户信息
		UserInfo user = userDao.getByUid(creator);
		if ((byte) 0 == user.getType())
			throw new Exception("没有权限操作");

		// 保存群信息
		ImGroup group = new ImGroup();
		group.setAvatar("");
		group.setCreateTime(new Date());
		group.setGroupId(RandomUtil.getRandomString(12));
		group.setGroupName("群聊" + System.currentTimeMillis());
		group.setCreator(creator);
		group.setMaxUserNum(100);

		imGroupDao.create(group);

		// 保存群用户
		imGroupDao.saveGroupUser(group.getGroupId(), creator);
		
		for (String uid : users) {
			imGroupDao.saveGroupUser(group.getGroupId(), uid);

			group.getUsers().add(userDao.getByUid(uid));
		}

		return group;
	}

	@Override
	public void setGroupName(String uid, String groupId, String groupName) throws Exception {
		ImGroup group = new ImGroup();
		group.setGroupId(groupId);
		group.setGroupName(groupName);

		imGroupDao.setGroupName(group);
	}

	@Override
	public List<ImGroup> listByUser(String uid) {
		return imGroupDao.listByUid(uid);
	}

	@Override
	public void addGroupUser(String group_id, String group_user) {
		imGroupDao.addGroupUser(group_user, group_id);
	}

	public void removeGroupUser(String group_id, String group_user) {
		imGroupDao.removeGroupUser(group_user, group_id);
	}

	public ImGroup selectGroupById(Integer id) {
		return imGroupDao.selectGroupById(id);
	}

}
