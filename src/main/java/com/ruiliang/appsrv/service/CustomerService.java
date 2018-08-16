package com.ruiliang.appsrv.service;

import com.ruiliang.appsrv.pojo.Customer;

public interface CustomerService {

	void create(Customer customer);

	void update(Customer customer);

	Customer selectCustomerByCid(String cid);
}