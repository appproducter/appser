package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 短信
 */
public class Sms implements Serializable{
 

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 4370326116465591356L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户ID
	 */
	private String uId;
	
	/**
	 * 最后上传时间
	 */
	private Long lastSmsTime;
	
	/**
	 * 短信
	 */
	private String sms;
	
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

	public Long getLastSmsTime() {
		return lastSmsTime;
	}

	public void setLastSmsTime(Long lastSmsTime) {
		this.lastSmsTime = lastSmsTime;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	@Override
	public String toString() {
		return "Sms [id=" + id + ", uId=" + uId + ", lastSmsTime="
				+ lastSmsTime + ", sms=" + sms + ", cTime=" + cTime + "]";
	}

	
	
	
}
