package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 通讯录
 */
public class Pim implements Serializable{
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -8139453580218415541L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户ID
	 */
	private String uId;
	
	
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
		return "Pim [id=" + id + ", uId=" + uId + ", pim=" + pim + ", cTime="
				+ cTime + "]";
	}


	
	
}
