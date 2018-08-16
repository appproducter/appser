package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ruiliang.appsrv.pojo.Customer;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface CustomerDAO {
	
	/**
	 * 查询客户
	 * @param cid
	 * @return
	 */
	Customer selectCustomerByCid(@Param("cid") String cid);
}
