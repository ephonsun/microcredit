package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.face.FaceSetting;
import banger.domain.html5.IntoFileInfo;
import banger.framework.util.FileUtil;
import banger.framework.util.HttpRequestClient;
import banger.framework.util.ThumbUtil;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.IFaceSettingService;
import banger.service.intf.IFileInfoService;
import banger.util.MultipartImageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panl on 2017/11/14
 */
@Controller
@RequestMapping("/base")
public class Base64FaceController extends BaseController {
	@Autowired
	private IFaceSettingService faceSettingService;
	@Autowired
	private IFileInfoService fileInfoService;

	@Resource
	private ILoanModule loanModule;

	/**
	 * 人脸比对
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/faceid/v2/verify")
	public ResponseEntity<String> getFaceVerify(@RequestParam("imag") String imag, @RequestParam("imagType") String imagType,
												@RequestParam("imagName") String imagName, @RequestParam("fileName") String fileName,
												HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

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
				if(imag!=null){
					image1 = createImageFile(this.getRootPath(), imagName,imag);
					//image1 = MultipartImageUtil.createImageFile(this.getRootPath(),image);
					id = uploadImg(imag,imagType,imagName);
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

		if(imag!=null){
			image1 = createImageFile(this.getRootPath(), imagName,imag);
			//image1 = MultipartImageUtil.createImageFile(this.getRootPath(),image);
			id = uploadImg(imag,imagType,imagName);
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
	private Integer	uploadImg(String base, String imageType, String imageName){
		Integer id = null;
		// 上传位置
		String path = loanModule.getLoanAddedProvider().getSavePath(
				LoanAddedFileType.IDIMAGE.savePath);
		// 缩略图
		String thumbPath = loanModule.getLoanAddedProvider()
				.getImageThumbPath();

		FileUtil.mkdirs(new String[]{path, thumbPath});
		// 获得原始文件名
		String srcName = imageName;
		// 新文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		String newFileName = dateString + srcName;
		String newFullFilename = path + newFileName;
		String imgFilePath = newFullFilename;//新生成的图片
		if (base != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				base = base.toString().substring(23);
				byte[] b = decoder.decodeBuffer(base);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						b[i] += 256;
					}
				}
				//String imgFilePath = newFullFilename + imageType;//新生成的图片
				OutputStream out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error("保存上传图片异常 error:" + e.getMessage(), e);
				e.printStackTrace();
			}
			String thumbFilename = thumbPath + newFileName;

			try {
				ThumbUtil.zoomImageScale(new File(imgFilePath),
						thumbFilename, 400);
			} catch (IOException e) {
				e.printStackTrace();
			}
			IntoFileInfo intoFileInfo = new IntoFileInfo();
			if (new File(imgFilePath).exists()) {
				Long fileSize = new File(imgFilePath).length();
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
//	private File createImageFile(MultipartFile multipartFile) throws IOException {
//		FileItem fileItem = ((CommonsMultipartFile)multipartFile).getFileItem();
//		String tempPath = FileUtil.contact(this.getRootPath(),"uploadTempImages");
//		if(!new File(tempPath).exists())new File(tempPath).mkdirs();
//		String newImageFilename = FileUtil.contact(tempPath,new Date().getTime()+"_"+multipartFile.getOriginalFilename());
//		File file = new File(newImageFilename);
//		multipartFile.transferTo(file);
//		return file;
//	}

	private FaceSetting getFaceSetting(){
		FaceSetting setting = faceSettingService.getSettingById(1);
		return setting;
	}

	private JSONObject recordFile(String base, String imageType, String imageName) {

		// 上传位置
		String path = loanModule.getLoanAddedProvider().getSavePath(
				LoanAddedFileType.IDIMAGE.savePath);
		// 缩略图
		String thumbPath = loanModule.getLoanAddedProvider()
				.getImageThumbPath();

		FileUtil.mkdirs(new String[]{path, thumbPath});
		// 获得原始文件名
		String srcName = imageName;
		// 新文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		String newFileName = dateString + srcName;
		String newFullFilename = path + newFileName;
		String imgFilePath = newFullFilename + imageType;//新生成的图片
		if (base != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				base = base.toString().substring(23);
				byte[] b = decoder.decodeBuffer(base);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						b[i] += 256;
					}
				}
				//String imgFilePath = newFullFilename + imageType;//新生成的图片
				OutputStream out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error("保存上传图片异常 error:" + e.getMessage(), e);
				e.printStackTrace();
			}
			String thumbFilename = thumbPath + newFileName;

			try {
				ThumbUtil.zoomImageScale(new File(imgFilePath), thumbFilename, 400);
			} catch (IOException e) {
				e.printStackTrace();
			}
			JSONObject json = new JSONObject();
			if (new File(imgFilePath).exists()) {
				Long fileSize = new File(imgFilePath).length();
				json.put("fileName", newFileName);
				json.put("filePath", path);
				json.put("fileSize", fileSize.intValue());
				json.put("fileSrcName", srcName);
				json.put("fileThumbImageName", newFileName);
				json.put("fileThumbImagePath", thumbPath);
				return json;
			}
			return null;
		}
		return null;
	}

	/**
	 * base64字符串转文件
	 *
	 * @param base64
	 * @return
	 */
	private File base64ToFile(String base64, String fileName, String imageType, String imageName) {
		// 上传位置
		String path = loanModule.getLoanAddedProvider().getSavePath(
				LoanAddedFileType.IDIMAGE.savePath);
		// 缩略图
		String thumbPath = loanModule.getLoanAddedProvider()
				.getImageThumbPath();

		FileUtil.mkdirs(new String[]{path, thumbPath});
		// 获得原始文件名
		String srcName = imageName;
		// 新文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		String newFileName = dateString + srcName;
		String newFullFilename = path + newFileName;
		String imgFilePath = newFullFilename;//新生成的图片
		File file = new File(imgFilePath);
		return file;
	}

	private File createImageFile(String path, String fileName, String base){
		String tempPath = FileUtil.contact(path, "uploadTempImages");
		if (!new File(tempPath).exists()) new File(tempPath).mkdirs();
		String newImageFilename = FileUtil.contact(tempPath, new Date().getTime() + "_" + fileName);
		File file = new File(newImageFilename);
		saveImage(base, newImageFilename);
		return file;
	}

	private void saveImage(String base, String newImageFilename) {
		if (base != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				base = base.toString().substring(23);
				byte[] b = decoder.decodeBuffer(base);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						b[i] += 256;
					}
				}
				//String imgFilePath = newFullFilename + imageType;//新生成的图片
				OutputStream out = new FileOutputStream(newImageFilename);
				out.write(b);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
