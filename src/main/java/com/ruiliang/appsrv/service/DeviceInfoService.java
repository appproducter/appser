package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.DeviceInfo;

/**
 * @author LinJian.Liu
 *
 */
public interface DeviceInfoService {

	/**
	 * 添加设备信息
	 * @param di
	 * @return
	 */
	Integer insertDeviceInfo(DeviceInfo di);
}
