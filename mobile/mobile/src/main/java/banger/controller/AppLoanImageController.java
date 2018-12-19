package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanInfoAddedClass;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.ThumbUtil;
import banger.moduleIntf.ILoanApplyProvider;
import banger.moduleIntf.ILoanModule;
import banger.moduleIntf.IPermissionService;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanAddedService;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppLoanImageController extends BaseController {
	private static final long serialVersionUID = 636442151844922799L;

	@Resource
	private ILoanModule loanModule;

	@Autowired
	private IAppLoanAddedService appLoanAddedService;

	@Resource
	private IPermissionService permissionService;

	@Resource
	private ILoanApplyProvider iLoanApplyProvider;

	/**
	 * 一次上传多张图片
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/loanAddedImageUpload")
	public ResponseEntity<String> loanAddedImageUpload(@RequestParam(value = "file") MultipartFile[] files,
			HttpServletRequest request,HttpServletResponse response){
		
		//
		String loanId = this.getParameter("loanId");
		// 代款过程类型
		String loanProcessType = this.getParameter("loanProcessType");
		// 附件是，属于个体信息，还是企业信息，分类字段
		String ownerId = this.getParameter("ownerId");
		// 附件的种类，身份证，结婚证
		String classId = this.getParameter("classId");
		// 用户ID
		String userId = this.getParameter("userId");

		try {

			// 上传位置
			String path = loanModule.getLoanAddedProvider().getSavePath(
					LoanAddedFileType.IMAGE.savePath);
			// 缩略图
			String thumbPath = loanModule.getLoanAddedProvider()
					.getImageThumbPath();

			FileUtil.mkdirs(new String[]{path, thumbPath});

			List<LoanInfoAddedFiles> list = new ArrayList<LoanInfoAddedFiles>();
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
						LoanInfoAddedFiles info = new LoanInfoAddedFiles();
						info.setLoanId(Integer.parseInt(loanId));
						info.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
						info.setFileName(newFileName);
						info.setFileSrcName(srcName);
						info.setFileSize(fileSize.intValue());
						info.setFilePath(path);
						info.setCreateDate(new Date());
						info.setUpdateDate(new Date());
						info.setFileFix(fix);
						info.setFileType(LoanAddedFileType.IMAGE.type);
						if (StringUtil.isNotEmpty(ownerId))
							info.setOwnerId(Integer.parseInt(ownerId));
						if (StringUtil.isNotEmpty(classId))
							info.setClassId(Integer.parseInt(classId));
						if (StringUtil.isNotEmpty(loanProcessType))
							info.setLoanProcessType(loanProcessType);
						if (StringUtil.isNotEmpty(userId)) {
							info.setCreateUser(Integer.parseInt(userId));
							info.setUpdateUser(Integer.parseInt(userId));
						}

						info.setFileThumbImageName(newFileName);
						info.setFileThumbImagePath(thumbPath);

						loanModule.getLoanAddedProvider().saveLoanAddedFileInfo(
								info);
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
	private JSONArray getResultJsonArrayByList(List<LoanInfoAddedFiles> list){
		JSONArray ja = new JSONArray();
		for(LoanInfoAddedFiles fileInfo : list){
			JSONObject jo = new JSONObject();
			jo.put("id", fileInfo.getId());
			jo.put("fileName", fileInfo.getFileName());
			jo.put("srcFilename", fileInfo.getFileSrcName());
			ja.add(jo);
		}
		return ja;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/getLoanAddedFileCatalog")
	public ResponseEntity<String> getLoanAddedFileCatalog(HttpServletRequest request,HttpServletResponse response){
		List<LoanInfoAddedOwner> ownerList = loanModule.getLoanAddedProvider().getAllInfoAddedOwnerList();
		List<LoanInfoAddedClass> classList = loanModule.getLoanAddedProvider().getAllInfoAddedClassList();
		
		JSONArray root = new JSONArray();
		if(ownerList!=null && ownerList.size()>0){
			for(LoanInfoAddedOwner addOwner : ownerList){
				JSONObject joo = new JSONObject();
				joo.put("id", addOwner.getOwnerId());
				joo.put("display", addOwner.getOwnerName());
				
				JSONArray jac = new JSONArray();
				for(LoanInfoAddedClass addClass : classList){
					if(addClass.getOwnerId().equals(addOwner.getOwnerId())){
						JSONObject joc = new JSONObject();
						joc.put("id", addClass.getClassId());
						joc.put("display", addClass.getClassName());
						jac.add(joc);
					}
				}
				
				joo.put("children", jac);
				root.add(joo);
			}
		}
		
		return new ResponseEntity<String>(AppJsonResponse.success(root), HttpStatus.OK);
	}

	/**
	 * 修改贷款附件分类信息
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/saveLoanAddedFileCatalog")
	public ResponseEntity<String> saveLoanAddedFileCatalog(HttpServletRequest request,HttpServletResponse response){

		String reqJson = null;
		try {
			reqJson= HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("修改贷款附件分类信息接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String resultJson = appLoanAddedService.saveLoanAddedFileCatalog(reqObj);
				return new ResponseEntity<String>(resultJson, HttpStatus.OK);
			} catch (Exception e) {
				log.error("修改贷款附件分类信息接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error("修改贷款附件分类信息接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	/**
	 * 下载图片大图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/queryLoanAddedFileList")
	public ResponseEntity<String> queryLoanAddedFileList(HttpServletRequest request,HttpServletResponse response){
		//
		String loanId = this.getParameter("loanId");
		// 代款过程类型
		String loanProcessTypes = this.getParameter("loanProcessTypes");
		// 附件是，属于个体信息，还是企业信息，分类字段
		String ownerIds = this.getParameter("ownerIds");
		// 附件的种类，身份证，结婚证
		//String classId = this.getParameter("classId");
		String userId = this.getParameter("userId");
		Integer isDelete = 0;
		if(StringUtil.isNotEmpty(loanId) && StringUtil.isNotEmpty(userId)){
			Integer[] roleIds = permissionService.getRoleIdsByUserId(Integer.parseInt(userId));
			LoanApplyInfo loanApplyInfo = iLoanApplyProvider.getApplyInfoById(Integer.parseInt(loanId));
			for (int i = 0; i<roleIds.length;i++){
				if(GroupRolesEnum.MANAGER.getRoleId().equals(roleIds[i])){
					if("Apply".equals(loanApplyInfo.getLoanProcessType()) || "Investigate".equals(loanApplyInfo.getLoanProcessType())) {
						isDelete = 1;
					}
				}
			}
		}
		List<LoanInfoAddedOwner> ownerList = loanModule.getLoanAddedProvider().queryInfoAddedOwnerListByOwnerIds(ownerIds);
		
		List<LoanInfoAddedClass> classList = loanModule.getLoanAddedProvider().queryInfoAddedClassListByOwnerIds(ownerIds);
		Map<Integer,LoanInfoAddedClass> classMap = new HashMap<Integer,LoanInfoAddedClass>();
		for(LoanInfoAddedClass addClass : classList){
			classMap.put(addClass.getClassId(),addClass);
		}
		
		JSONArray root = new JSONArray();
		JSONObject other = new JSONObject();
		other.put("id", -1);
		other.put("display", "其他");
		
		Map<Integer,JSONArray> fileMap = new HashMap<Integer,JSONArray>();
		JSONArray jao = new JSONArray();
		if(StringUtil.isNotEmpty(loanId)){
			List<LoanInfoAddedFiles> fileList = loanModule.getLoanAddedProvider().getInfoAddedFilesListById(Integer.parseInt(loanId),loanProcessTypes);
			for(LoanInfoAddedFiles addFile : fileList){
				if(LoanAddedFileType.IMAGE.type.equals(addFile.getFileType())) {
					JSONObject jo = new JSONObject();
					jo.put("id", addFile.getId());
					jo.put("fileId",addFile.getFileId());
					jo.put("ownerId", addFile.getOwnerId());
					jo.put("classId", addFile.getClassId());
					jo.put("filename", addFile.getFileName());
					jo.put("thumbname", addFile.getFileThumbImageName());
					if (classMap.containsKey(addFile.getClassId())) {
						jo.put("display", classMap.get(addFile.getClassId()).getClassName());
					} else {
						jo.put("display", "");
					}
					if (addFile.getClassId() != null && addFile.getClassId().intValue() > 0) {
						if (!fileMap.containsKey(addFile.getClassId()))
							fileMap.put(addFile.getClassId(), new JSONArray());
						fileMap.get(addFile.getClassId()).add(jo);
					} else {
						jao.add(jo);
					}
				}
			}
		}
		
		other.put("files",jao);
		root.add(other);
		
		if(ownerList!=null && ownerList.size()>0){
			for(LoanInfoAddedOwner addOwner : ownerList){
				JSONObject joo = new JSONObject();
				joo.put("id", addOwner.getOwnerId());
				joo.put("display", addOwner.getOwnerName());
				
				JSONArray jac = new JSONArray();
				for(LoanInfoAddedClass addClass : classList){
					if(addClass.getOwnerId().equals(addOwner.getOwnerId())){
						JSONObject joc = new JSONObject();
						joc.put("id", addClass.getClassId());
						joc.put("display", addClass.getClassName());
						joc.put("files", fileMap.get(addClass.getClassId()));
						jac.add(joc);
					}
				}
				
				joo.put("children", jac);
				root.add(joo);
			}
		}
		
		return new ResponseEntity<String>(AppJsonResponse.success(root,isDelete), HttpStatus.OK);
		
	}
	
	/**
	 * 下载图片大图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadLoanAddedImage")
	public void downloadLoanAddedImage(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("id");
		String fileId = this.getParameter("fileId");
		LoanInfoAddedFiles info = null;
		if(StringUtil.isNotEmpty(id)){
			info = loanModule.getLoanAddedProvider().getLoanAddedFileInfoById(Integer.parseInt(id));
		}else if(StringUtil.isNotEmpty(fileId)){
			info = loanModule.getLoanAddedProvider().getLoanAddedFileInfoByFileId(fileId);
		}

		if(info!=null){
			String path = info.getFilePath();
			String filename = info.getFileName();
			String srcName = info.getFileSrcName();
			downloadImageFile(id,path,filename,srcName,response);
		}
	}
	
	/**
	 * 下载图片缩略图
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadLoanAddedThumbImage")
	public void downloadLoanAddedThumbImage(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("id");
		String fileId = this.getParameter("fileId");
		LoanInfoAddedFiles info = null;
		if(StringUtil.isNotEmpty(id)){
			info = loanModule.getLoanAddedProvider().getLoanAddedFileInfoById(Integer.parseInt(id));
		}else if(StringUtil.isNotEmpty(fileId)){
			info = loanModule.getLoanAddedProvider().getLoanAddedFileInfoByFileId(fileId);
		}

		if(info!=null){
			String path = info.getFileThumbImagePath();
			String filename = info.getFileThumbImageName();
			String srcName = info.getFileSrcName();
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
		String path = this.getRootPath();
		String imagePath = FileUtil.contact(path, "apk");
		String filename = FileUtil.contact(imagePath, "holdplace.png");
		return filename;
	}

}
