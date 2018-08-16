package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu 通话记录
 */
public class UserToken implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 2540948814197983039L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 用户ID
	 */
	private String uId;

	/**
	 * token
	 */
	private String token;

	/**
	 * 公司编码
	 */
	private String cid;

	/**
	 * 超时时间
	 */
	private Integer timeOut;

	/**
	 * 创建时间
	 */
	private Date cTime;
	private Date updateTime;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "UserToken [id=" + id + ", uId=" + uId + ", token=" + token + ", cid=" + cid + ", timeOut=" + timeOut
				+ ", cTime=" + cTime + "]";
	}

}
