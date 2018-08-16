package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.DeviceInfoDAO;
import com.ruiliang.appsrv.pojo.DeviceInfo;
import com.ruiliang.appsrv.service.DeviceInfoService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class DeviceInfoServiceImpl implements DeviceInfoService{

	@Autowired
	private DeviceInfoDAO deDao;
	
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.DeviceInfoService#insertDeviceInfo(com.ruiliang.appsrv.pojo.DeviceInfo)
	 */
	@Override
	public Integer insertDeviceInfo(DeviceInfo di) {
		return deDao.insertDeviceInfo(di);
	}

}
