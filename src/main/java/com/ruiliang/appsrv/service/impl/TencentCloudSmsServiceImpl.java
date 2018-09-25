package com.ruiliang.appsrv.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.jfinal.kit.PropKit;
import com.ruiliang.appsrv.exception.SmsSendFailureException;
import com.ruiliang.appsrv.pojo.UserVerifyLog;
import com.ruiliang.appsrv.service.SmsService;

@Service("tencentSmsService")
public class TencentCloudSmsServiceImpl implements SmsService {

	private static final Map<Integer, Integer> SMS_TEMPLATE_MAP = new HashMap<Integer, Integer>();
	static {
		SMS_TEMPLATE_MAP.put(UserVerifyLog.VERIFY_TYPE_FORGETPASSWD, 177653);
		SMS_TEMPLATE_MAP.put(UserVerifyLog.VERIFY_TYPE_REGNOTIFY, 200382);
	}

	@Override
	public void send(int type, String dest, String[] content) throws SmsSendFailureException {

		try {
			PropKit.use("application.properties");
			SmsSingleSender singleSender = new SmsSingleSender(Integer.parseInt(PropKit.get("tencloud.sms.appid")),
					PropKit.get("tencloud.sms.appkey"));
			SmsSingleSenderResult singleSenderResult = null;
			int tmplId = 177653;
			tmplId = SMS_TEMPLATE_MAP.get(type);

			ArrayList<String> params = new ArrayList<>();
			for (int i = 0; i < content.length; i++) {
				params.add(content[i]);
			}

			singleSenderResult = singleSender.sendWithParam("86", dest, tmplId, params, "", "", "");
		} catch (Exception e) {
			throw new SmsSendFailureException("短信发送出错: " + e.toString());
		}
	}

}
