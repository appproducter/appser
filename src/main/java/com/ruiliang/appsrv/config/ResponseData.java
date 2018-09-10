package com.ruiliang.appsrv.config;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {
	
	private  String message;
    private  int code;
    private  Map<String, Object> data = new HashMap<String, Object>();
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
    
    
}
