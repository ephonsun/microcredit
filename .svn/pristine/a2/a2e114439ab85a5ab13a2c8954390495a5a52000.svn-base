package banger.controller;

import banger.action.AppBaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.customer.CustPotentialCustomersFiles;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.ThumbUtil;
import banger.moduleIntf.IPotentialCustomerFileProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 贷款资料图片附件
 * 
 * @author zhusw
 * 
 */
@RequestMapping("/api")
@Controller
public class AppPotentialCustomerFilesController extends AppBaseController {
	private static final long serialVersionUID = 636442151844922799L;

	@Value("${file_root_path}")
	private String fileRootPath;

	@Autowired
	private IPotentialCustomerFileProvider potentialCustomerFileProvider;

	/**
	 * 一次上传多张图片
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/potentialImageUpload")
	public ResponseEntity<String> loanAddedImageUpload(@RequestParam(value = "file") MultipartFile[] files,
			HttpServletRequest request,HttpServletResponse response){
		String potentialId = this.getParameter("potentialId");
		// 用户ID
		String userId = this.getParameter("userId");

		try {
			// 上传位置
			String path = FileUtil.contact(fileRootPath, "Image/");
			// 缩略图Image/Thumb
			String thumbPath = FileUtil.contact(fileRootPath, "Image/Thumb/");

			FileUtil.mkdirs(new String[]{path, thumbPath});

			List<CustPotentialCustomersFiles> list = new ArrayList<CustPotentialCustomersFiles>();
			for (int i = 0; i < files.length; i++) {
				// 获得原始文件名
				String srcName = files[i].getOriginalFilename();

				// 新文件名
				String newFileName = srcName;
				String newFullFilename = path + newFileName;
				String fix = FileUtil.getFileFix(srcName);
				if (!files[i].isEmpty()) {
					if(new File(newFullFilename).exists())			//如果文件已存在，删除重传
						new File(newFullFilename).delete();
					String errorMessage = "";
					try {
						FileOutputStream fos = new FileOutputStream(newFullFilename);
						InputStream in = files[i].getInputStream();
						int b = 0;
						while ((b = in.read()) != -1) {
							fos.write(b);
						}
						fos.close();
						in.close();
					} catch (Exception e) {
						log.error("保存上传图片异常 error:" + e.getMessage(), e);
						errorMessage = e.getMessage();
						System.out.println("保存图片出错："+errorMessage);
						continue;
					}

					String thumbFilename = thumbPath + newFileName;

					if(new File(thumbFilename).exists())			//如果文件已存在，删除重传
						new File(thumbFilename).delete();

					try {
						ThumbUtil.zoomImageScale(new File(newFullFilename),
								thumbFilename, 400);
					} catch (IOException e) {
						log.error("生成缩略图异常 error:" + e.getMessage(), e);
						errorMessage = e.getMessage();
					}

					if (StringUtil.isNotEmpty(errorMessage)) {
						System.out.println("生成缩略图出错："+errorMessage);
						continue;
					}

					if (new File(newFullFilename).exists()) {
						Long fileSize = new File(newFullFilename).length();
						CustPotentialCustomersFiles info = new CustPotentialCustomersFiles();
						info.setPotentialId(Integer.parseInt(potentialId));
						info.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
						info.setFileName(newFileName);
						info.setFileSrcName(srcName);
						info.setFileSize(fileSize.intValue());
						info.setFilePath(path);
						info.setCreateDate(new Date());
						info.setUpdateDate(new Date());
						info.setFileFix(fix);
						info.setFileType(LoanAddedFileType.IMAGE.type);
						info.setFileThumbImageName(newFileName);
						info.setFileThumbImagePath(thumbPath);
						potentialCustomerFileProvider.insertPotentialCustomersFiles(info, Integer.parseInt(userId));
						list.add(info);
					}
				}
			}
			if (list.size() > 0) {
				JSONArray ja = getResultJsonArrayByList(list);
				return new ResponseEntity<String>(AppJsonResponse.success(ja), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_113), HttpStatus.OK);
			}
		}catch (Exception e){
			log.error("上传图片程序异常 error:" + e.getMessage(), e);
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
		}
	}
	
	/**
	 * 得到文件信息JSON
	 * @param list
	 * @return
	 */
	private JSONArray getResultJsonArrayByList(List<CustPotentialCustomersFiles> list){
		JSONArray ja = new JSONArray();
		for(CustPotentialCustomersFiles fileInfo : list){
			JSONObject jo = new JSONObject();
			jo.put("id", fileInfo.getId());
			jo.put("fileName", fileInfo.getFileName());
			jo.put("srcFilename", fileInfo.getFileSrcName());
			ja.add(jo);
		}
		return ja;
	}

	
	/**
	 * 影像资料列表
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/queryPotentialFileList")
	public ResponseEntity<String> queryLoanAddedFileList(HttpServletRequest request,HttpServletResponse response){
		String potentialIdStr = this.getParameter("potentialId");
		JSONArray jao = new JSONArray();
		if(StringUtil.isNotEmpty(potentialIdStr)){
			Integer potentialId = Integer.parseInt(potentialIdStr);
			List<CustPotentialCustomersFiles> fileList = potentialCustomerFileProvider.getPotentialFilesListByPotentialId(potentialId);
			for(CustPotentialCustomersFiles addFile : fileList){
				JSONObject jo = new JSONObject();
				jo.put("id", addFile.getId());
				jo.put("potentialId", potentialId);
				jo.put("fileId",addFile.getFileId());
				jo.put("filename", addFile.getFileName());
				jo.put("thumbname", addFile.getFileThumbImageName());
				jao.add(jo);
			}
		}
		return new ResponseEntity<String>(AppJsonResponse.success(jao), HttpStatus.OK);
		
	}
	
	/**
	 * 下载图片大图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadPotentialImage")
	public void downloadLoanAddedImage(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("id");
		String fileId = this.getParameter("fileId");
		String isThumb = this.getParameter("isThumb");
		CustPotentialCustomersFiles info = null;
		if(StringUtil.isNotEmpty(fileId)){
			info = potentialCustomerFileProvider.getPotentialCustomersFilesByFileId(fileId);
		}
		if(StringUtil.isNotEmpty(id)){
			info = potentialCustomerFileProvider.getPotentialCustomersFilesById(Integer.parseInt(id));
		}
		if(info!=null){
			String path, filename, srcName;
			if (StringUtils.equals("true", isThumb)) {
				path = info.getFileThumbImagePath();
				filename = info.getFileThumbImageName();
				srcName = info.getFileSrcName();
			} else {
				path = info.getFilePath();
				filename = info.getFileName();
				srcName = info.getFileSrcName();
			}
			downloadImageFile(id,path,filename,srcName,response);
		}
	}
	
	private void downloadImageFile(String id,String path,String filename,String srcName,HttpServletResponse response){
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
					String imageName = URLEncoder.encode(srcName,"UTF-8");
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
					log.error("下截图片异常 id:"+id+" error:"+e.getMessage(),e);
				}finally{
					if(stream!=null){
						try{stream.close();}catch(Exception e){}
					}
					if(output!=null){
						try{output.close();}catch(Exception e){}
					}
				}
			}else{
				file  = new File(getHoldPlaceFilename());
				FileInputStream stream = null;
				OutputStream output = null;
				try{
					long fileSize = file.length();
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/force-download");// 设置强制下载不打开
					String imageName = URLEncoder.encode(srcName,"UTF-8");
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
					log.error("下截图片异常 id:"+id+" error:"+e.getMessage(),e);
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

	private String getHoldPlaceFilename(){
		String path = super.getRootPath();
		String imagePath = FileUtil.contact(path, "apk");
		String filename = FileUtil.contact(imagePath, "holdplace.png");
		return filename;
	}

}
