package com.transcode.server.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUploadUtil {
	
	private static String URL_STR = "http://bak.putao.fs.kwwwy.com/trans/%s";
	
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
	
	public static String uploadFile(String filePath){
		File file = new File(filePath);
		String curlCommend = "curl -T "+filePath+" "+String.format(URL_STR, file.getName());
		CommandUtil.runCmd(curlCommend);
		file.delete();
		return String.format(URL_STR, file.getName());
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
