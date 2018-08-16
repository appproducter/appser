package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Customer;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface CustomerDAO {
	
	void insert(Customer customer);
	
	void update(Customer customer);
	
	Customer selectCustomerByCid(String cid);
}
