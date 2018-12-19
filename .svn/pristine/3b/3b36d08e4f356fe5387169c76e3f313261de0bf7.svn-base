package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.face.FaceSetting;
import banger.domain.html5.IntoFileInfo;
import banger.framework.util.FileUtil;
import banger.framework.util.HttpRequestClient;
import banger.framework.util.ThumbUtil;
import banger.service.intf.IFaceSettingService;
import banger.service.intf.IFileInfoService;
import banger.util.MultipartImageUtil;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 旷世接口调用
 * @author zhusw
 *
 */
@Controller
@RequestMapping("/api")
public class AppFaceController extends BaseController {

	@Autowired
	private IFaceSettingService faceSettingService;
   @Autowired
   private IFileInfoService fileInfoService;

	/**
	 * 人脸比对
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/faceid/v2/verify")
	public ResponseEntity<String> getFaceVerify(@RequestParam("image") MultipartFile image,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		File image1 = null;
		File image_ref1 = null;
		File image_ref2 = null;
		File image_ref3 = null;
		Integer id = null;
				
		try {
			if (request instanceof MultipartRequest) {
			MultipartRequest multRequest = (MultipartRequest) request;
			Map<String, MultipartFile> fileMap = multRequest.getFileMap();
//				if (fileMap.containsKey("image")) {
//					image1 = MultipartImageUtil.createImageFile(this.getRootPath(),image);
//			       id = uploadImg(fileMap.get("image"));
//				}
				if(image!=null){
					image1 = MultipartImageUtil.createImageFile(this.getRootPath(),image);
					id = uploadImg(fileMap.get("image"));
				}

				if (fileMap.containsKey("image_ref1")) {
					image_ref1 = MultipartImageUtil.createImageFile(this.getRootPath(),fileMap.get("image_ref1"));
				}
				if (fileMap.containsKey("image_ref2")) {
					image_ref2 = MultipartImageUtil.createImageFile(this.getRootPath(),fileMap.get("image_ref2"));
				}
				if (fileMap.containsKey("image_ref3")) {
					image_ref3 = MultipartImageUtil.createImageFile(this.getRootPath(),fileMap.get("image_ref3"));
				}
			}
		}catch (IOException e){
			return new ResponseEntity<String>("程序异常:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String comparison_type = this.getParameter("comparison_type");
		String idcard_name =java.net.URLDecoder.decode(this.getParameter("idcard_name"),"utf-8") ;
		String idcard_number = this.getParameter("idcard_number");
		String uuid = this.getParameter("uuid");

		FaceSetting setting = this.getFaceSetting();
		String url = setting.getFaceAppUrl();
		Map<String,Object> paramMap = new HashMap<String,Object>();

		if("1".equalsIgnoreCase(comparison_type)){		//有源比对
			paramMap.put("idcard_name",idcard_name);
			paramMap.put("idcard_number",idcard_number);

		}else if("0".equalsIgnoreCase(comparison_type)){
			paramMap.put("uuid",uuid);			//无源比时，唯一标识

			if(image_ref1!=null)paramMap.put("image_ref1",image_ref1);
			if(image_ref2!=null)paramMap.put("image_ref2",image_ref2);
			if(image_ref3!=null)paramMap.put("image_ref3",image_ref3);
		}

		if(image1!=null)paramMap.put("image",image1);

		paramMap.put("api_key",setting.getFaceAppKey());
		paramMap.put("api_secret",setting.getFaceAppSecret());
		paramMap.put("face_image_type","raw_image");
		paramMap.put("comparison_type",comparison_type);

		try {
			Date t1 = new Date();
			HttpRequestClient.Result result = HttpRequestClient.postMultipartData(url, paramMap);

			if(image1!=null)image1.delete();
			if(image_ref1!=null)image_ref1.delete();
			if(image_ref2!=null)image_ref2.delete();
			if(image_ref3!=null)image_ref3.delete();

			String s = result.getResponseBody().toString();

		
			return new ResponseEntity<String>(s.substring(0,s.length()-1)+",\"id\":"+id+"}", result.getStatus());
		}catch (IOException e){
			return new ResponseEntity<String>("程序异常:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	private Integer	uploadImg(MultipartFile image){
		Integer id = null;
		// 上传位置
		String path = "d:/bangerIntoRecord/Image/";
		// 缩略图
		String thumbPath = "d:/bangerIntoRecord/Image/Thumb/";

		FileUtil.mkdirs(new String[]{path, thumbPath});
// 获得原始文件名
		String srcName = image.getOriginalFilename();
// 新文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());

		String newFileName = dateString+srcName;
		String newFullFilename = path + newFileName;
		String fix = FileUtil.getFileFix(srcName);

		if (!image.isEmpty()) {
			String errorMessage = "";
			try {
				FileOutputStream fos = new FileOutputStream(newFullFilename);
				InputStream in = image.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) {
					fos.write(b);
				}
				fos.close();
				in.close();
			} catch (Exception e) {
				log.error("保存上传图片异常 error:" + e.getMessage(), e);
				errorMessage = e.getMessage();
			}
			String thumbFilename = thumbPath + newFileName;

			try {
				ThumbUtil.zoomImageScale(new File(newFullFilename),
						thumbFilename, 400);
			} catch (IOException e) {
				log.error("生成缩略图异常 error:" + e.getMessage(), e);
				errorMessage = e.getMessage();
			}
			IntoFileInfo intoFileInfo = new IntoFileInfo();
			if (new File(newFullFilename).exists()) {
				Long fileSize = new File(newFullFilename).length();
				intoFileInfo.setFileName(newFileName);
				intoFileInfo.setFilePath(path);
				intoFileInfo.setFileSize(fileSize.intValue());
				intoFileInfo.setFileSrcName(srcName);
				intoFileInfo.setImageType(3);
				intoFileInfo.setApplyId(0);
				intoFileInfo.setRequestId(0);
				intoFileInfo.setFileThumbImageName(newFileName);
				intoFileInfo.setFileThumbImagePath(thumbPath);
				intoFileInfo.setCreateTime(new Date());

			}
		 id = 	fileInfoService.insertFileInfoReturnId(intoFileInfo);
		}
		return id;
	}
	private File createImageFile(MultipartFile multipartFile) throws IOException {
		FileItem fileItem = ((CommonsMultipartFile)multipartFile).getFileItem();
		String tempPath = FileUtil.contact(this.getRootPath(),"uploadTempImages");
		if(!new File(tempPath).exists())new File(tempPath).mkdirs();
		String newImageFilename = FileUtil.contact(tempPath,new Date().getTime()+"_"+multipartFile.getOriginalFilename());
		File file = new File(newImageFilename);
		multipartFile.transferTo(file);
		return file;
	}

	private FaceSetting getFaceSetting(){
		FaceSetting setting = faceSettingService.getSettingById(1);
		return setting;
	}

	/**
	 * http://127.0.0.1:8080/api/testFace.html?comparison_type=0
	 * 测试人脸比较,当备注用
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/testFace")
	public ResponseEntity<String> testFace(HttpServletRequest request,HttpServletResponse response){
		String comparison_type = this.getParameter("comparison_type");

		FaceSetting setting = this.getFaceSetting();
		String url = setting.getFaceAppUrl();

		Map<String,Object> paramMap = new HashMap<String,Object>();

		if("1".equalsIgnoreCase(comparison_type)){		//有源比对
			String idcard_name = "朱胜伟";
			String idcard_number = "330501198011092614";

			paramMap.put("idcard_name",idcard_name);
			paramMap.put("idcard_number",idcard_number);

		}else if("0".equalsIgnoreCase(comparison_type)){
			String uuid = UUID.randomUUID().toString();
			paramMap.put("uuid",uuid);			//无源比时，唯一标识

			String imageFilename1 = "d:\\PIC\\1.jpg";
			File image_ref1 = new File(imageFilename1);
			paramMap.put("image_ref1",image_ref1);
		}

		String imageFilename = "d:\\PIC\\0.jpg";
		File image = new File(imageFilename);
		paramMap.put("image",image);

		paramMap.put("api_key",setting.getFaceAppKey());
		paramMap.put("api_secret",setting.getFaceAppSecret());
		paramMap.put("face_image_type","raw_image");
		paramMap.put("comparison_type",comparison_type);

		try {
			Date t1 = new Date();
			HttpRequestClient.Result result = HttpRequestClient.postMultipartData(url, paramMap);
			Date t2 = new Date();
			System.out.println(t2.getTime()-t1.getTime());
			System.out.println(result.getStatus());
			System.out.println(result.getResponseBody());
			return new ResponseEntity<String>(result.getResponseBody(), result.getStatus());
		}catch (IOException e){
			return new ResponseEntity<String>("程序异常:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
	
}
