package com.transcode.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.transcode.server.service.TransCodeService;
import com.transcode.server.util.CommandUtil;
import com.transcode.server.util.FileUploadUtil;

public class TransCodeServiceImpl implements TransCodeService {

	@Override
	public Map<String,String> transferAndUpload(String transFile) {
		Map<String,String> info = new HashMap<String, String>();
		String outTempVideoFile = "/home/huangmiao/video/"+System.currentTimeMillis()+".mp4";
		String outTempPicFile = "/home/huangmiao/video/"+System.currentTimeMillis()+".jpeg";
		if(CommandUtil.executeCodecs(transFile, outTempVideoFile, outTempPicFile, "320*240")) {
			try {
				System.out.println("video url:"+FileUploadUtil.uploadFile(outTempVideoFile));
				System.out.println("pic url:"+FileUploadUtil.uploadFile(outTempPicFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
	public static void main(String[] args) {
		TransCodeServiceImpl transService = new TransCodeServiceImpl();
		transService.transferAndUpload("http://baby-file.b0.upaiyun.com/uploads/manage/videos/1-2-6-1-gqdb.mp4");
	}
}
