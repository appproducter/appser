package com.ruiliang.appsrv.pojo;

import java.io.Serializable;


/**
 * @author LinJian.Liu
 * 用户类
 */
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2145634543715700757L;

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户地址
	 */
	private String userAdd;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAdd() {
		return userAdd;
	}

	public void setUserAdd(String userAdd) {
		this.userAdd = userAdd;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userAdd=" + userAdd + "]";
	}
	
	
	
}
