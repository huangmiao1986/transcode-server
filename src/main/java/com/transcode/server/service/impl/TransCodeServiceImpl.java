package com.transcode.server.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transcode.server.response.TransCodeNode;
import com.transcode.server.response.TransCodeResponse;
import com.transcode.server.service.TransCodeService;
import com.transcode.server.util.CommandUtil;
import com.transcode.server.util.FileUploadUtil;

@Service("transCodeService")
public class TransCodeServiceImpl implements TransCodeService {

	@Override
	public TransCodeResponse transferAndUpload(String projectPath,String account,String[] transFiles,String ratio) throws Exception{
		TransCodeResponse response = new TransCodeResponse("0", "");
		if(transFiles != null && transFiles.length > 0) {
			List<TransCodeNode> list = new ArrayList<TransCodeNode>();
			String desPath = FileUploadUtil.createPath(projectPath, "trans/temp"+File.separator+account);
			for(String transFile:transFiles) {
				String outTempVideoFile = desPath+File.separator+System.currentTimeMillis()+".mp4";
				String outTempPicFile = desPath+File.separator+System.currentTimeMillis()+".jpeg";
				if(CommandUtil.executeCodecs(transFile, outTempVideoFile, outTempPicFile, ratio)) {
					String videoUrl = FileUploadUtil.uploadFile(outTempVideoFile);
					String picUrl = FileUploadUtil.uploadFile(outTempPicFile);
					TransCodeNode node = new TransCodeNode();
					node.setVideo_url(videoUrl);
					node.setPic_url(picUrl);
					list.add(node);
					response.setList(list);
					
					System.out.println("video url:"+videoUrl);
					System.out.println("pic url:"+picUrl);
				}
			}
		}
		return response;
	}
	public static void main(String[] args) {
		TransCodeServiceImpl transService = new TransCodeServiceImpl();
		try {
			String[] transFiles = {"http://baby-file.b0.upaiyun.com/uploads/manage/videos/1-2-6-1-gqdb.mp4"};
			transService.transferAndUpload("/home/huangmiao","12345",transFiles,"320*240");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
