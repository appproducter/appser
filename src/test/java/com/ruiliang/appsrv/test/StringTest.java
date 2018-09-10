package com.ruiliang.appsrv.test;

import com.ruiliang.appsrv.util.MD5Util;
import com.ruiliang.appsrv.util.RandomUtil;

public class StringTest {

	public static void main(String[] args) {
		System.out.println(RandomUtil.getRandomString(12));
		
		System.out.println(MD5Util.MD5Encode("123456"));
		System.out.println(MD5Util.MD5Encode(MD5Util.MD5Encode("123456")));
	}
}
