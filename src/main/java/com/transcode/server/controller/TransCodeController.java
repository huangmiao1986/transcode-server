package com.transcode.server.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.transcode.server.response.TransCodeResponse;
import com.transcode.server.service.TransCodeService;
import com.transcode.server.util.FileUploadUtil;
import com.transcode.server.util.JsonUtil;
import com.transcode.server.util.StringUtil;

@Controller
public class TransCodeController {
	
	final String[] allowedVideo = new String[]{"mp4"};
	
	@Resource
	private TransCodeService transCodeService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "action/transcodeurl", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String transCodeForUrl(HttpServletRequest request,
			@RequestParam(value="account",required = true) String account,
			@RequestParam(value="ratio",required = true) String ratio,
			@RequestParam(value="vcodec",required = true) String vcodec,
			@RequestBody String body
			)  throws ServletException, IOException{
		 	try {
		 		String projectPath = request.getSession().getServletContext().getRealPath("");
		 		if(StringUtil.isNotBlank(body)) {
		 			List<String> videoUrls = (List<String>) JsonUtil.fromJson(body, new TypeToken<List<String>>(){}.getType());
			 		if(videoUrls != null && videoUrls.size() > 0) {//在线视频地址
			 			return JsonUtil.toJson(transCodeService.transferAndUpload(projectPath,account,videoUrls,ratio,vcodec));
			 		}
		 		}
			} catch(FileUploadException e){
				e.printStackTrace();
				return JsonUtil.toJson(new TransCodeResponse("1","transCode fail"));
			}catch (Exception e) {
				e.printStackTrace();
				return JsonUtil.toJson(new TransCodeResponse("1","transCode fail"));
			}
		 	
		 	return JsonUtil.toJson(new TransCodeResponse("1","transCode fail"));
	}
	
	@RequestMapping(value = "action/transcodefile", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String transCodeForFile(HttpServletRequest request,
			@RequestParam(value="account",required = true) String account,
			@RequestParam(value="ratio",required = true) String ratio,
			@RequestParam(value="vcodec",required = true) String vcodec
			)  throws ServletException, IOException{
		 	try {
		 		String projectPath = request.getSession().getServletContext().getRealPath("");
	 			String tempPath = FileUploadUtil.createPath(projectPath, "upload/temp/sourceVideos/"+account);
	 			List<String> fileList = writeFileToTemp(request,tempPath,allowedVideo);
	 			if(fileList != null && fileList.size() > 0) {
	 				return JsonUtil.toJson(transCodeService.transferAndUpload(projectPath,account,fileList,ratio,vcodec));
	 			}
			} catch(FileUploadException e){
				e.printStackTrace();
				return JsonUtil.toJson(new TransCodeResponse("1","transCode fail"));
			}catch (Exception e) {
				e.printStackTrace();
				return JsonUtil.toJson(new TransCodeResponse("1","transCode fail"));
			}
		 	
		 	return JsonUtil.toJson(new TransCodeResponse("1","transCode fail"));
	}
	
	/**
	 * 写入临时文件
	 * @param request
	 * @param allowedType
	 * @param maxSize
	 * @param userId
	 * @throws Exception
	 * @throws FileUploadException
	 */
	@SuppressWarnings("unchecked")
	private List<String> writeFileToTemp(HttpServletRequest request,String tempPath,String[] allowedType) throws Exception,FileUploadException {
		System.out.println("文件写入本地开始====");
		List<String> fileList = new ArrayList<String>();
		 //获得磁盘文件条目工厂。  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。  
        factory.setRepository(new File(tempPath));
        //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
        factory.setSizeThreshold(1024*1024);  
        //上传处理工具类（高水平API上传处理？）  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        upload.setSizeMax(-1);
        //调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。  
        List<FileItem> list = (List<FileItem>)upload.parseRequest(request);  
        for(FileItem item:list){
            //获取表单属性名字。  
            String name = item.getFieldName();  
            //如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
            System.out.println("isFormField:"+item.isFormField());
            if(item.isFormField()){  
                //获取用户具体输入的字符串，  
                String value = item.getString();  
                request.setAttribute(name, value);  
            }  
            //如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
            else{   
                //获取路径名  
                String value = item.getName();  
                //取到最后一个反斜杠。  
                int start = value.lastIndexOf("\\");  
                //截取上传文件的 字符串名字。+1是去掉反斜杠。  
                String filename = value.substring(start+1);  
                boolean allowedFlag = false;
                String fileFormat = filename.substring(filename.lastIndexOf(".") + 1);
                System.out.println("filename:"+filename);
                for (String allowed : allowedType)
                {
                    if (allowed.equals(fileFormat)) {
                    	allowedFlag = true;
                        break;
                    }
                }
                request.setAttribute(name, filename);  
                /*写到文件中*/  
                if(allowedFlag) {
                	System.out.println("获取文件总量的容量:"+ item.getSize());
                	item.write(new File(tempPath+File.separator+filename));
                	
                	fileList.add(tempPath+File.separator+filename);
	            }  
	        }  
        }
        return fileList;
	}
}
