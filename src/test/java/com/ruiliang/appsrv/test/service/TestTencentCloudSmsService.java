package com.ruiliang.appsrv.test.service;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ruiliang.appsrv.exception.SmsSendFailureException;
import com.ruiliang.appsrv.service.SmsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTencentCloudSmsService {

	@Autowired
	@Qualifier("tencentSmsService")
	private SmsService smsService;

	@Test
	public void testSendCode() {
		try {
			JSONObject json = new JSONObject();
			json.put("name", "rocky");
			System.out.println(json.toString());

			smsService.send(SmsService.SMS_TYPE_RESET_PASSWD, "18575588430", new String[] { "332211" });
		} catch (SmsSendFailureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
