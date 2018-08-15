package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 版本
 */
public class Version implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -3315748534240003037L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 平台 1安卓 2IOS
	 */
	private Byte platForm;
	
	/**
	 * 版本号
	 */
	private Integer code;
	
	/**
	 * 版本信息
	 */
	private String info;
	
	/**
	 * APK 路径
	 */
	private String downUrl;
	
	/**
	 * 更新内容信息
	 */
	private String updateInfo;
	
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

	public Byte getPlatForm() {
		return platForm;
	}

	public void setPlatForm(Byte platForm) {
		this.platForm = platForm;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	@Override
	public String toString() {
		return "Version [id=" + id + ", platForm=" + platForm + ", code="
				+ code + ", info=" + info + ", downUrl=" + downUrl
				+ ", updateInfo=" + updateInfo + ", cTime=" + cTime + "]";
	}
	
	
}
