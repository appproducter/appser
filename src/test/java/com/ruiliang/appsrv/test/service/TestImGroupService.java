package com.ruiliang.appsrv.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ruiliang.appsrv.service.ImGroupService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestImGroupService {

	@Autowired
	private ImGroupService groupService;
	
	@Test
	public void testSave() {
		String creator = "aaa";
		
		List<String> users = new ArrayList<String>();
		users.add("ccc");
		users.add("kga5kiquc7");
		
		try {
			groupService.create(creator, users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
