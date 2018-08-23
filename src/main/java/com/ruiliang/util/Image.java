package com.ruiliang.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Image {
	    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	    public static void main(String[] args) {
	        System.out.println(getImageStr(null)); // image to base64
	       // base64StringToImage(getImageBinary()); // base64 to image
	    }

	    public static String getImageStr(String filePath) {
	    	         InputStream inputStream = null;
	    	         byte[] data = null;
	    	         try {
	    	            inputStream = new FileInputStream("E:\\11.jpg");
	    	             data = new byte[inputStream.available()];
	    	             inputStream.read(data);
	    	             inputStream.close();
	    	         } catch (IOException e) {
	    	             e.printStackTrace();
	    	         }
	    	         // 加密
	    	         BASE64Encoder encoder = new BASE64Encoder();
	    	         return encoder.encode(data);
	    	     }

	    public static boolean generateImage(String imgStr, String filename) {
	    	          
	    	          if (imgStr == null) {
	    	             return false;
	    	         }
	    	         BASE64Decoder decoder = new BASE64Decoder();
	    	         try {
	    	             // 解密
	    	             byte[] b = decoder.decodeBuffer(imgStr);
	    	             // 处理数据
	    	             for(int i = 0; i < b.length; ++i) {
	    	                 if (b[i] < 0) {
	    	                     b[i] += 256;
	    	                 }
	    	             }
	    	             OutputStream out = new FileOutputStream("E:/"+filename);
	    	             out.write(b);
	    	             out.flush();
	    	             out.close();
	    	             return true;
	    	         } catch (IOException e) {
	    	             // TODO Auto-generated catch block
	    	             e.printStackTrace();
	    	         }
	    	         return false;
	    	         
	    	     }
	    	     
}
