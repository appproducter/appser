package com.ruiliang.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ruiliang.appsrv.util.Base64;
import com.ruiliang.appsrv.util.MD5Util;

public class Image {

	    public static void main(String[] args) {
	    	System.out.println(1721/1024);
	    	String id = MD5Util.MD5Encode("123456");
	    	System.out.println(id);
	    	//System.out.println(getImageStr(""));
	    }

	    public static String getImageStr(String filePath) {
	    	         InputStream inputStream = null;
	    	         byte[] data = null;
	    	         try {
	    	            inputStream = new FileInputStream("E:\\11.png");
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
