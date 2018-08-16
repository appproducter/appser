package com.ruiliang.appsrv.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCustomerService {

	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testSave() {
		Customer c = new Customer();
		c.setcId("44030102");
		c.setName("测试客户");
		c.setApiKey("===");
		c.setcTime(new Date());
		c.setMgrQua(0);
		
		customerService.create(c);
		
	}
}
