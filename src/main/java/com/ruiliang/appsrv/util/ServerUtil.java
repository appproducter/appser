package com.ruiliang.appsrv.util;

public class ServerUtil {
	
	public static String encryptToken(String uid, String customerid, long time) {
		return MD5Util.MD5Encode(MD5Util.MD5Encode(uid + "_" + customerid + "_" + time));
	}
}
