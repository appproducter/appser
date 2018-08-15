package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 后台管理员类
 */
public class Manager implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 5626138093558247836L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 用户状态(0 正常 ，1 禁止登录)
	 */
	private Byte status;
	
	/**
	 * 创建时间
	 */
	private Date cTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", userName=" + userName + ", password="
				+ password + ", status=" + status + ", cTime=" + cTime + "]";
	}
	
	
}
