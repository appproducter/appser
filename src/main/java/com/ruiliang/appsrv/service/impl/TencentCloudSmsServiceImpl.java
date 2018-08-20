package com.ruiliang.appsrv.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ruiliang.appsrv.exception.SmsSendFailureException;
import com.ruiliang.appsrv.service.SmsService;

@Service("tencentSmsService")
public class TencentCloudSmsServiceImpl implements SmsService {

	@Value("tencloud.sms.appid")
	private String appid;
	@Value("tencloud.sms.appkey")
	private String appkey;

	@Override
	public void send(int type, String dest, String content) throws SmsSendFailureException {
		SmsSingleSender singleSender = new SmsSingleSender(Integer.parseInt(appid), appkey);
		SmsSingleSenderResult singleSenderResult = null;
		int tmplId = 177653;
		ArrayList<String> params = new ArrayList<>();
		params.add(content);
		try {
			singleSenderResult = singleSender.sendWithParam("86", dest, tmplId, params, "", "", "");
		} catch (Exception e) {
			throw new SmsSendFailureException("短信发送出错: " + e.toString());
		}
	}

}
