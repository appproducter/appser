package com.ruiliang.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruiliang.appsrv.util.MD5Util;

public class SignUtil {

	/**
	 * 生成签名
	 * 
	 * @param data
	 * @param apiKey
	 * @return
	 */
	public static String generateSign(Map<String, String> data, String apiKey) {
		String sign = "";

		// 将数据参数按首字母升序排列
		List<String> keys = new ArrayList<>(data.keySet());
		Collections.sort(keys);

		StringBuilder content = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String val = data.get(key);

			// 值为空的不参与签名
			if (val != null && !val.equals("null") && val.length() > 0) {
				content.append(buildKeyValue(key, val, false));
			}

		}

		content.append(apiKey);

		sign = MD5Util.MD5Encode(content.toString());

		return sign;
	}

	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
}
