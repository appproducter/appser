package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Pim;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface PimDAO {
	
	/**
	 * 保存通讯录
	 * @param pim
	 * @return
	 */
	Integer savePim(Pim pim);
	
	Pim selectPimByUid(String uid);
}
