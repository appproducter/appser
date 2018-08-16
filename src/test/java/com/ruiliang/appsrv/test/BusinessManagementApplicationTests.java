package com.ruiliang.appsrv.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ruiliang.appsrv.pojo.Customer;
import com.ruiliang.appsrv.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessManagementApplicationTests {

	@Autowired
	private CustomerService cs;
	
	@Test
	public void tests() {
		Customer c = cs.findByCid("44030101");
		System.out.println(c);
	}

}
