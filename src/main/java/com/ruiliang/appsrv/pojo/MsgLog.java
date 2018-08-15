package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 短信日志
 */
public class MsgLog implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -2328706200144098764L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 短信标题
	 */
	private String title;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 发送时间
	 */
	private Date time;
	
	/**
	 * 创建时间
	 */
	private Date cTime;
	
	/**
	 * 类型   1：忘记密码
	 */
	private Byte type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MsgLog [id=" + id + ", title=" + title + ", content=" + content
				+ ", phone=" + phone + ", time=" + time + ", cTime=" + cTime
				+ ", type=" + type + "]";
	}
	
	
}
