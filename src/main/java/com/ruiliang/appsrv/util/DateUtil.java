package com.ruiliang.appsrv.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author LinJian.Liu
 *
 */
public class DateUtil {
	
	public static String getFormatDateString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
}