package com.ruiliang.appsrv.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ruiliang.appsrv.pojo.Customer;

/**
 * @author LinJian.Liu
 *
 */
@Mapper
public interface CustomerDAO {
	
	int insert(Customer customer);
	
	int update(Customer customer);
	
	Customer selectCustomerByCid(String cid);

}
