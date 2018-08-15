package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 操作日志
 */
public class OperLog implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -8165119509811036080L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 操作用户ID
	 */
	private String uId;
	
	/**
	 * 1删除,2修改，3增加
	 */
	private Byte type;
	
	/**
	 * 操作时间
	 */
	private Date time;
	
	/**
	 * 操作详细内容
	 */
	private String content;

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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "OperLog [id=" + id + ", uId=" + uId + ", type=" + type
				+ ", time=" + time + ", content=" + content + "]";
	}
	
	
}
