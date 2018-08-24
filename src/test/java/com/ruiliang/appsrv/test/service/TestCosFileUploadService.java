package com.ruiliang.appsrv.test.service;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ruiliang.appsrv.upload.FileUploadService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCosFileUploadService {

	@Autowired
	@Qualifier("cosFileUploadService")
	private FileUploadService fileUploadService;

	@Test
	public void testUpload() {
		File f = new File("D:\\我的文档\\我的照片\\崽崽\\微信图片_20180112100131.jpg");
		
		
		try {
			FileInputStream fis = new FileInputStream(f);
			
			fileUploadService.copy2Chat(fis, "jpg", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

		/*try {
			fileUploadService.copy2Chat(f, "jpg", f.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
}
