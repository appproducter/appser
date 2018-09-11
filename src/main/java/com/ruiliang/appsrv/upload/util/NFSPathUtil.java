package com.ruiliang.appsrv.upload.util;

import java.util.Date;

import com.ruiliang.appsrv.upload.NFSConstants;
import com.ruiliang.appsrv.util.CalendarUtil;

public class NFSPathUtil {

	public static String getPath(int NFSType) {
		StringBuilder sb = new StringBuilder();

		sb.append(NFSConstants.NFS_CHAT_ROOT_PATH);
		sb.append("/");
		String ym = CalendarUtil.format(new Date(), "yyyyMM");

		switch (NFSType) {
		case NFSConstants.NFS_TYPE_CHAT_IMAGE:
			sb.append("image");
			break;
		case NFSConstants.NFS_TYPE_CHAT_VOICE:
			sb.append("voice");
			break;
		case NFSConstants.NFS_TYPE_CHAT_VEDIO:
			sb.append("video");
			break;
		default:
			sb.append("unknow");
			break;
		}

		sb.append("/").append(ym);

		return sb.toString();
	}

	public static int getNFSType(String fileType) {
		if(NFSConstants.NFS_TYPE_IMAGE.contains(fileType)){
			return NFSConstants.NFS_TYPE_CHAT_IMAGE;
		}
		if(NFSConstants.NFS_TYPE_VOICE.contains(fileType)){
			return NFSConstants.NFS_TYPE_CHAT_VOICE;
		}
		if(NFSConstants.NFS_TYPE_VIDEO.contains(fileType)){
			return NFSConstants.NFS_TYPE_CHAT_VEDIO;
		}
		return NFSConstants.NFS_TYPE_CHAT_IMAGE;
	}
}
