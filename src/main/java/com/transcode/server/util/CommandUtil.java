package com.transcode.server.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandUtil {
	
	//运行命令
	public  static void runCmd(String command) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<INFO>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</INFO>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param infile infile输入文件(包括完整路径)
	 * @param outfile outfile输出文件
	 * @param ratio 分辨率
	 * @param videoFormat 格式
	 * @return
	 */
	public static boolean executeCodecs(String inFile, String outFile,
			String mediaPicFile,String ratio,String vcodec) {
		System.out.println("start convert executeCodecs");
		String videoCommend = "ffmpeg -i "
				+ inFile
				+ " -acodec libfaac -vcodec "+vcodec+" -bf 0 -g 25 -an -f mp4 -s "+ratio+" "
				+ outFile;
		String picCommend = "ffmpeg -ss 00:01:06 -i " + inFile
				+ " -f image2 -y " + mediaPicFile;
		System.out.println(videoCommend);
		runCmd(videoCommend);
		System.out.println("videoCommend success");
		runCmd(picCommend);
		System.out.println("picCommend success");
		return true;
	}
	
	public static void main(String[] args) {
		CommandUtil.executeCodecs("http://baby-file.b0.upaiyun.com/uploads/manage/videos/1-2-6-1-gqdb.mp4", "/home/huangmiao/video/"+System.currentTimeMillis()+".mp4", "/home/huangmiao/video/11.jpeg","320x240","libx264");
	}
}
