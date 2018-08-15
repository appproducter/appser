package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 通话记录
 */
public class Calllog implements Serializable{
 
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -1153470169677586318L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户ID
	 */
	private String uId;
	
	/**
	 * 通话记录上传时间
	 */
	private Long time;
	
	/**
	 * 通话记录
	 */
	private String calllog;
	
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

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getCalllog() {
		return calllog;
	}

	public void setCalllog(String calllog) {
		this.calllog = calllog;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	@Override
	public String toString() {
		return "Calllog [id=" + id + ", uId=" + uId + ", time=" + time
				+ ", calllog=" + calllog + ", cTime=" + cTime + "]";
	}
	
	
}
