package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.Pim;

/**
 * @author LinJian.Liu
 *
 */
public interface PimService {

	
	/**
	 * 保存通讯录
	 * @param pim
	 * @return
	 */
	Integer savePim(Pim pim);
	
	Pim selectPimByUid(String uid);
}
