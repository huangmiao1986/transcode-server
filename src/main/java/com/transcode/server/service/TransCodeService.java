package com.transcode.server.service;

import java.util.Map;

/**
 * 
 * 
 * @author huangmiao
 * @version $Id: TransCodeService.java, v 0.1 2017年2月16日 上午11:02:18 huangmiao Exp $
 */
public interface TransCodeService {
	public Map<String,String> transferAndUpload(String transFile);
}
