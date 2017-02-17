package com.transcode.server.service;

import java.util.List;

import com.transcode.server.response.TransCodeResponse;

/**
 * 
 * 
 * @author huangmiao
 * @version $Id: TransCodeService.java, v 0.1 2017年2月16日 上午11:02:18 huangmiao Exp $
 */
public interface TransCodeService {
	public TransCodeResponse transferAndUpload(String projectPath,String account,List<String> transFile,String ratio) throws Exception;
}
