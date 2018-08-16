package com.ruiliang.appsrv.util;

public class Validator {

	/**
	 * 手机号码正则表达式
	 */
	public static final String REG_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9])|(16[0-9]))\\d{8}$";

	/**
	 * 身份证正则表达式
	 */
	public static final String REG_IDCARD = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

	public static boolean isMobile(String s) {
		return s.matches(REG_MOBILE);
	}

	public static boolean isIdcard(String s) {
		return s.matches(REG_IDCARD);
	}
	
	public static void main(String[] args) {
		String s = "18575588430";
		String s2 = "430423198504125819";
		System.out.println(s + " 是手机号码: " + Validator.isMobile(s));
		System.out.println(s + " 是身份 证号码: " + Validator.isIdcard(s));
	}
}
