package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.CustomerDAO;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.service.CustomerService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{

	
	@Autowired
	private CustomerDAO cDao;
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.CustomerService#selectCustomerByCid(java.lang.String)
	 */
	@Override
	public Customer selectCustomerByCid(String cid) {
		return cDao.selectCustomerByCid(cid);
	}

}
