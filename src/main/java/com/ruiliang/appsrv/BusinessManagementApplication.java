package com.ruiliang.appsrv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@SpringBootApplication
public class BusinessManagementApplication {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(BusinessManagementApplication.class, args);
	}
}
