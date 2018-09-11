package com.ruiliang.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ruiliang.appsrv.util.Base64;
import com.ruiliang.appsrv.util.MD5Util;

public class Image {

	    public static void main(String[] args) {
	    	String id = MD5Util.MD5Encode("123456");
	    	System.out.println(id);
	    	//  System.out.println(MD5Util.MD5Encode("23456")); // image to base64
	     //  MD5Util.MD5Encode("12345");
	        // base64StringToImage(getImageBinary()); // base64 to image
	    	//System.out.println(getImageStr(""));
	    }

	    public static String getImageStr(String filePath) {
	    	         InputStream inputStream = null;
	    	         byte[] data = null;
	    	         try {
	    	            inputStream = new FileInputStream("E:\\123.amr");
	    	             data = new byte[inputStream.available()];
	    	             inputStream.read(data);
	    	             inputStream.close();
	    	         } catch (IOException e) {
	    	             e.printStackTrace();
	    	         }
	    	         // 加密
	    	       return  Base64.encode(data);
	    	     }
}
