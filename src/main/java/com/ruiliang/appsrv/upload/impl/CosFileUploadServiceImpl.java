package com.ruiliang.appsrv.upload.impl;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.ruiliang.appsrv.upload.FileUploadService;
import com.ruiliang.appsrv.upload.NFSConstants;
import com.ruiliang.appsrv.upload.util.FileUtil;
import com.ruiliang.appsrv.upload.util.NFSPathUtil;

@Service("cosFileUploadService")
public class CosFileUploadServiceImpl implements FileUploadService {

	@Value("${tencloud.cos.secretid}")
	private String apiSecretId;
	@Value("${tencloud.cos.secretkey}")
	private String apiSecretKey;
	@Value("${tencloud.cos.region}")
	private String cosRegion;
	@Value("${tencloud.cols.bucketname}")
	private String bucketName;

	private ExecutorService threadPool = Executors.newFixedThreadPool(32);

	@Override
	public String copy(File srcFile, String contentType, String srcFileName, int nfsType) throws Exception {
		String fileUrl = "";

		String tempname = FileUtil.htmlEncode(NFSPathUtil.getPath(nfsType));

		// 建立cos连接
		COSCredentials cred = new BasicCOSCredentials(apiSecretId, apiSecretKey);
		ClientConfig clientConfig = new ClientConfig(new Region(cosRegion));
		COSClient cosClient = new COSClient(cred, clientConfig);

		tempname += "/" + new Date().getTime();
		// 随机数
		char[] randoms = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		Random rand = new Random();
		tempname += randoms[rand.nextInt(randoms.length)] + randoms[rand.nextInt(randoms.length)]
				+ randoms[rand.nextInt(randoms.length)] + randoms[rand.nextInt(randoms.length)];

		tempname += "." + contentType;

		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, tempname, srcFile);

			TransferManager transferManager = new TransferManager(cosClient, threadPool);

			Upload upload = transferManager.upload(putObjectRequest);
			UploadResult uploadResult = upload.waitForUploadResult();

		} catch (Exception e) {
		} finally {

		}

		fileUrl = NFSConstants.NFS_SERVER_URL + tempname;

		return fileUrl;
	}

	@Override
	public String copy2Chat(File srcFile, String contentType, String srcFileName) throws Exception {
		int nfsType = NFSPathUtil.getNFSType(contentType);

		return copy(srcFile, contentType, srcFileName, nfsType);
	}

}
