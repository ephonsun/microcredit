package banger.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banger.common.annotation.NoTokenAnnotation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.response.AppJsonResponse;

/**
 * 安卓静态资源下载
 * @author zhusw
 *
 */
@Controller
@RequestMapping("/api")
public class AppAsertController extends BaseController {
	private static final long serialVersionUID = -831366525909070939L;
	private String startImagePath = "/apk";
	
	/**
	 * 下载图片缩略图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/getStartImageList")
	@NoTokenAnnotation
	public ResponseEntity<String> getStartImageList(HttpServletRequest request,HttpServletResponse response){
		JSONArray ja = new JSONArray();
		String path = this.getRootPath();
		String imagePath = FileUtil.contact(path, startImagePath);
		String filename = "start.jpg";
		String imageFilename = FileUtil.contact(imagePath, filename);
		File file = new File(imageFilename);
		if (file.exists()) {
			JSONObject jo = new JSONObject();
			jo.put("filename",filename);
			jo.put("version", file.lastModified()+"");
			ja.add(jo);
		}
		return new ResponseEntity<String>(AppJsonResponse.success(ja), HttpStatus.OK);
	}
	
	/**
	 * http://192.168.1.27:8080/phone/api/v1/downloadStartImage.html?filename=start.jpg
	 * 下载图片缩略图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/downloadStartImage")
	@NoTokenAnnotation
	public void downloadStartImage(HttpServletRequest request,HttpServletResponse response){
		String filename = this.getParameter("filename");
		String path = this.getRootPath();
		String imagePath = FileUtil.contact(path, startImagePath);
		downloadImageFile(imagePath,filename,response);
	}

	private void downloadImageFile(String path,String filename,HttpServletResponse response){
		if(StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)){
			String imageFilename = FileUtil.contact(path, filename);
			File file = new File(imageFilename);
			if (file.exists()) {
				FileInputStream stream = null;
				OutputStream output = null;
				try{
					long fileSize = file.length();
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/force-download");// 设置强制下载不打开
					String imageName = URLEncoder.encode(filename,"UTF-8");
					response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
					response.addHeader("Content-Length", fileSize+"");
					
					stream = new FileInputStream(file);
					output = response.getOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while((len = stream.read(bytes))!=-1){
						output.write(bytes, 0, len);
						output.flush();
					}
				}catch(IOException e){
					log.error("下截图片异常 error:"+e.getMessage(),e);
				}finally{
					if(stream!=null){
						try{stream.close();}catch(Exception e){}
					}
					if(output!=null){
						try{output.close();}catch(Exception e){}
					}
				}
			}
		}
	}
	
}
