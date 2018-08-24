package com.ruiliang.appsrv.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImGroup {

	private Integer id;
	private String groupId;
	private String groupName;
	private String avatar;
	private String creator;
	private int maxUserNum;
	private Date createTime;

	private List<UserInfo> users = new ArrayList<UserInfo>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getMaxUserNum() {
		return maxUserNum;
	}

	public void setMaxUserNum(int maxUserNum) {
		this.maxUserNum = maxUserNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	public List<UserInfo> getUsers() {
		return users;
	}

}
