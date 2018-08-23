package com.ruiliang.appsrv.upload;

import java.io.File;

public interface FileUploadService {

	/**
	 * 上传至腾讯云存储
	 * 
	 * @param srcFile     源文件
	 * @param contentType 文件类型
	 * @param srcFileName 文件名
	 * @param nfsType     文件分类
	 * @return
	 * @throws Exception
	 */
	String copy(File srcFile, String contentType, String srcFileName, int nfsType) throws Exception;

	String copy2Chat(File srcFile, String contentType, String srcFileName) throws Exception;
}
