package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.Customer;

/**
 * @author LinJian.Liu
 *
 */
public interface CustomerService {

	/**
	 * 查询客户
	 * @param cid
	 * @return
	 */
	Customer selectCustomerByCid(String cid);
}
