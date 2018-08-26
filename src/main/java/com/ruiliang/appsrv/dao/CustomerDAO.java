package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Customer;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface CustomerDAO {
	
	/**
	 * 保存公司信息
	 * @param customer
	 * @return
	 */
	int insert(Customer customer);
	
	/**
	 * 更新公司信息
	 * @param customer
	 * @return
	 */
	int update(Customer customer);
	
	/**
	 * 根据公司编码查询公司
	 * @param cid
	 * @return
	 */
	Customer selectCustomerByCid(String cid);

}
