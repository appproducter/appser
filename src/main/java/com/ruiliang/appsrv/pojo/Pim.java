package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 通讯录
 */
public class Pim implements Serializable{
 

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 3413012554319021995L;

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
	private Long time;
	
	/**
	 * 通讯录
	 */
	private String pim;
	
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

	public String getPim() {
		return pim;
	}

	public void setPim(String pim) {
		this.pim = pim;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	@Override
	public String toString() {
		return "Pim [id=" + id + ", uId=" + uId + ", time=" + time + ", pim="
				+ pim + ", cTime=" + cTime + "]";
	}

	
	
}
