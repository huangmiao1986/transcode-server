package com.transcode.server.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
//import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import main.java.com.upyun.FormUploader;
import main.java.com.upyun.Params;
import main.java.com.upyun.Result;

public class FileUploadUtil {
	private static Type type = new TypeToken<Map<String, String>>() {}.getType();
	private static final String BUCKET_NAME = "baby-file";
	private static final String APIKEY = "lJX+c+o84zZqqMbwAmFjjczYVF0=";
	
	public static String createPath(String projectPath,String path) {
		String destPath = projectPath;
		String[] dir = path.split("/");
		for (int i = 0; i < dir.length; i++) {
			destPath += File.separator + dir[i];
			File file = new File(destPath);

			if (!file.exists() && !file.isDirectory()) {
				System.out.println("目录不存在:" + destPath);
				// 如果文件夹不存在则创建
				file.mkdirs();
			} else {
				System.out.println("目录存在");
			}
		}
		return destPath;
	}
	
	public static String uploadFile(String filePath) throws FileNotFoundException, IOException {
		File file = new File(filePath);
		// 取大小
//		Double aa = (double) (file.length() / 1000.0 / 1000.0);
//		BigDecimal bd = new BigDecimal(aa);
//		String size = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + " M";

		final Map<String, Object> paramsMap = new HashMap<String, Object>();

		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf("."));

		paramsMap.put(Params.SAVE_KEY, "/uploads/test/{year}{mon}{day}/{random32}" + suffix);
		FormUploader uploader = new FormUploader(BUCKET_NAME, APIKEY, null);
		Result result = uploader.upload(paramsMap, input2byte(new FileInputStream(file)));
		int code = result.getCode();
		if (code == 200 && result.isSucceed() == true) {
			String msg = result.getMsg();
			@SuppressWarnings("unchecked")
			Map<String, String> msgJson = (Map<String, String>) JsonUtil.fromJson(msg, type);
			String url = "http://baby-file.b0.upaiyun.com"+ msgJson.get("url");
			return url;
		} else {
			System.out.println("upload fail:"+result.getMsg());
			return "";
		}
	}
	
	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
	
}
