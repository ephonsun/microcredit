package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.face.FaceSetting;
import banger.domain.html5.IntoFileInfo;
import banger.framework.util.FileUtil;
import banger.framework.util.HttpRequestClient;
import banger.framework.util.StringUtil;
import banger.framework.util.ThumbUtil;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.IFaceSettingService;
import banger.service.intf.IFileInfoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *base64字符串的身份验证
 */
@Controller
@RequestMapping("/base")
public class Base64OcrController extends BaseController {
	@Autowired
	private IFaceSettingService faceSettingService;
	@Autowired
	private IFileInfoService fileInfoService;

	@Resource
	private ILoanModule loanModule;

	/**
	 * 身份验证
	 */
	@NoLoginInterceptor
	@RequestMapping("/faceid/v1/ocridcard")
	public ResponseEntity<String> getFaceOcrIdcard(@RequestParam("imag") String imag, @RequestParam("imagType") String imagType,
												   @RequestParam("imagName") String imagName, @RequestParam("fileName") String fileName, @RequestParam("imag2") String imag2,
												   @RequestParam("imag2Type") String imag2Type, @RequestParam("imag2Name") String imag2Name,
												   HttpServletRequest request, HttpServletResponse response) {

		Integer id1 = null;
		Integer id2 = null;
		File image = null;
		//String path = "d:/bangerIntoRecord/Images/";

		try {
			if (imag != null && imag2 != null) {
				JSONObject json1 = recordFile(imag, imagType, imagName);
				id1 = setIntoFileInfo(json1, 1);
				JSONObject json2 = recordFile(imag2, imag2Type, imag2Name);
				id2 = setIntoFileInfo(json2, 2);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("程序异常:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String legality = this.getParameter("legality");

		FaceSetting setting = this.getFaceSetting();
		String url = setting.getFaceOcrUrl();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("api_key", setting.getFaceOcrKey());
		paramMap.put("api_secret", setting.getFaceOcrSecret());

		if (StringUtil.isNotEmpty(legality))
			paramMap.put("legality", legality);

		//image = base64ToFile(imag,fileName,imagType,imagName);
		image = createImageFile(this.getRootPath(), imagName,imag);
		if (image != null) paramMap.put("image", image);

		try {
			Date t1 = new Date();
			HttpRequestClient.Result result = HttpRequestClient.postMultipartData(url, paramMap);
			Date t2 = new Date();
			System.out.println(t2.getTime() - t1.getTime());
			System.out.println(result.getStatus());
			System.out.println(result.getResponseBody());

			if (image != null) image.delete();
			String s = result.getResponseBody().toString();
			System.out.println(s.substring(0, s.length() - 1) + ",\"id1\":" + id1 + ",\"id2\":" + id2 + "}");
			return new ResponseEntity<String>(s.substring(0, s.length() - 1) + ",\"id1\":" + id1 + ",\"id2\":" + id2 + "}", result.getStatus());
		} catch (IOException e) {
			return new ResponseEntity<String>("程序异常:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@NoLoginInterceptor
	@RequestMapping("/upLoadFile")
	@ResponseBody
	public void upLoadFile(@RequestParam("imag") String imag, @RequestParam("imagType") String imagType,
						   @RequestParam("imagName") String imagName, @RequestParam("fileName") String fileName, @RequestParam("imag2") String imag2,
						   @RequestParam("imag2Type") String imag2Type, @RequestParam("imag2Name") String imag2Name,
						   HttpServletRequest request, HttpServletResponse response){
		JSONObject json=new JSONObject();
		try {
			if(imag != null && imag2 != null){
				Integer id1=null;
				Integer id2=null;
				JSONObject json1 = recordFile(imag, imagType, imagName);
				id1 = setIntoFileInfo(json1, 1);
				JSONObject json2 = recordFile(imag2, imag2Type, imag2Name);
				id2 = setIntoFileInfo(json2, 2);
				json.put("id1",id1);
				json.put("id2",id2);
				renderJson(true,"",json);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(false,"",null);
	}

	private Integer setIntoFileInfo(JSONObject json, Integer imageType) {
		IntoFileInfo file = new IntoFileInfo();
		file.setCreateTime(new Date());
		file.setFileName(json.optString("fileName"));
		file.setFilePath(json.optString("filePath"));
		file.setFileSize(Integer.parseInt(json.optString("fileSize")));
		file.setFileSrcName(json.getString("fileSrcName"));
		file.setFileThumbImageName(json.optString("fileThumbImageName"));
		file.setFileThumbImagePath(json.optString("fileThumbImagePath"));
		file.setImageType(imageType);
		return fileInfoService.insertFileInfoReturnId(file);
	}

	private FaceSetting getFaceSetting() {
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
