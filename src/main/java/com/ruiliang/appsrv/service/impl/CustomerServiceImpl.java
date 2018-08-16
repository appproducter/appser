package com.ruiliang.appsrv.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiliang.appsrv.dao.CustomerDAO;
import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.service.CustomerService;
import com.ruiliang.appsrv.util.Base64;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDao;

	private static Map<String, Customer> CUSTOMER_CACHE = new HashMap<String, Customer>();

	@Override
	@Transactional
	public void create(Customer customer) {
		customerDao.insert(customer);

		if (null == customer.getApiKey()) {
			customer.setApiKey(generateApiKey(customer.getcId()));
		}

		synchronized (CUSTOMER_CACHE) {
			CUSTOMER_CACHE.put(customer.getcId(), customer);
		}
	}

	private String generateApiKey(String cid) {
		return Base64.encode((cid + System.currentTimeMillis()).getBytes());
	}

	@Override
	@Transactional
	public void update(Customer customer) {
		customerDao.update(customer);

		synchronized (CUSTOMER_CACHE) {
			CUSTOMER_CACHE.put(customer.getcId(), customer);
		}
	}

	@Override
	public Customer selectCustomerByCid(String cid) {
		if (CUSTOMER_CACHE.containsKey(cid))
			return CUSTOMER_CACHE.get(cid);
		else {
			Customer cus = customerDao.selectCustomerByCid(cid);

			if (cus != null) {
				synchronized (CUSTOMER_CACHE) {
					CUSTOMER_CACHE.put(cid, cus);
				}
				return cus;
			}

			return null;
		}

	}

}
