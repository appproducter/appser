package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu 登录日志
 */
public class LoginLog implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -1454303421162982227L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 用户ID
	 */
	private String uId;

	/**
	 * 公司编码
	 */
	private String cId;

	/**
	 * 设备号
	 */
	private String deviceid;

	/**
	 * 登录时间
	 */
	private Date loginTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "LoginLog [id=" + id + ", uId=" + uId + ", cId=" + cId + ", deviceid=" + deviceid + ", loginTime="
				+ loginTime + "]";
	}

}
