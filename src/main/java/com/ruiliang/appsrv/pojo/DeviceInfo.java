package com.ruiliang.appsrv.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LinJian.Liu
 * 设备类
 */
public class DeviceInfo implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -7181597670754592752L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 设备ID
	 */
	private String deviceId;
	
	/**
	 * 版本号
	 */
	private Integer verCode;
	
	/**
	 * 版本信息
	 */
	private String verInfo;
	
	/**
	 * 终端品牌
	 */
	private String brand;
	
	/**
	 * 型号
	 */
	private String model;
	
	/**
	 * 终端操作系统
	 */
	private String os;
	
	/**
	 * 屏幕高度
	 */
	private Integer hpi;
	
	/**
	 * 屏幕宽度
	 */
	private Integer wpi;
	
	/**
	 * 手机串号
	 */
	private String imei;
	
	/**
	 * SIM卡
	 */
	private String imsi;
	
	/**
	 * 操作系统版本
	 */
	private String sysVersion;
	
	/**
	 * 公司编码
	 */
	private String channel;
	
	/**
	 * 操作时间
	 */
	private Date cTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getVerCode() {
		return verCode;
	}

	public void setVerCode(Integer verCode) {
		this.verCode = verCode;
	}

	public String getVerInfo() {
		return verInfo;
	}

	public void setVerInfo(String verInfo) {
		this.verInfo = verInfo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Integer getHpi() {
		return hpi;
	}

	public void setHpi(Integer hpi) {
		this.hpi = hpi;
	}

	public Integer getWpi() {
		return wpi;
	}

	public void setWpi(Integer wpi) {
		this.wpi = wpi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	@Override
	public String toString() {
		return "DeviceInfo [id=" + id + ", deviceId="
				+ deviceId + ", verCode=" + verCode + ", verInfo=" + verInfo
				+ ", brand=" + brand + ", model=" + model + ", os=" + os
				+ ", hpi=" + hpi + ", wpi=" + wpi + ", imei=" + imei
				+ ", imsi=" + imsi + ", sysVersion=" + sysVersion
				+ ", channel=" + channel + ", cTime=" + cTime + "]";
	}
	
	
}
