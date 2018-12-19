package banger.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.enumerate.LoanCreditCheckEnum;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.framework.util.IdCardUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.customer.CustCreditCheckFile;
import banger.domain.customer.CustCustomerCreditCheck;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.moduleIntf.ILoanModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.HttpParseUtil;

/**
 * 客户征信调查申请
 * @author ling
 *
 */
@Controller
@RequestMapping("/api")
public class AppCustomerCreditCheckController extends BaseController{

	private static final long serialVersionUID = 4913822165796724272L;
	
	@Autowired
	private ICustomerModuleIntf customerModuleIntf;
	
	@Autowired
	private ILoanModule loanModule;
	
	@Value("${file_root_path}")  
    private String fileRootPath;
	
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping(value = "/v1/creditCheckApply", method = RequestMethod.POST)
	public ResponseEntity<String> creditCheckApply(@RequestParam(value = "file") MultipartFile[] files,
			HttpServletRequest request,HttpServletResponse response){
		 
		try {
			/*String data=this.getParameter("data");
			if(StringUtils.isEmpty(data)){
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			
			data=data.replaceAll("\\", "");
			JSONObject reqObj = JSONObject.fromObject(data);*/

			String useridstr=this.getParameter("userId");
			String customerName=this.getParameter("customerName");
			String cardNo=this.getParameter("cardNo");
			String customerType=this.getParameter("customerType");
			String fileViewNames=this.getParameter("fileViewNames");
			String loanIdstr=this.getParameter("loanId");
			/*Integer userId=reqObj.getInt("userId");
			Integer loanId=reqObj.getInt("loanId");
			String customerName=reqObj.getString("customerName");
			String cardNo=reqObj.getString("cardNo");
			String customerType=reqObj.getString("customerType");
			String fileViewNames=reqObj.getString("fileViewNames");*/
			
			if(StringUtils.isEmpty(useridstr)|| 
					StringUtils.isEmpty(customerName) || StringUtils.isEmpty(cardNo)
					|| StringUtils.isEmpty(fileViewNames) ||StringUtils.isEmpty(loanIdstr)){
				log.error("征信调查申请接口，customerId、userid参数为空，userid="+useridstr+
						",customerName="+customerName+",cardNo="+cardNo+",loanId="+loanIdstr);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}

			String[] fileViewNameArray=fileViewNames.split(",");
			if(fileViewNameArray.length!=files.length){
				log.error("征信调查申请接口:fileViewNameArray{");
				for (String string : fileViewNameArray) {
					log.error(string+",");
				}
				log.error("}");
				log.error("文件名:{");
				for (MultipartFile file : files) {
					log.error(file.getOriginalFilename()+",");
				}
				log.error("}");
				log.error("征信调查申请接口，图片名称参数fileViewNames，提交图片数量不足3张");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
			
			if(files==null || files.length<3){
				log.error("征信调查申请接口，提交图片数量不足3张");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120,"征信调查图片不足3张"), HttpStatus.OK);
			}
			Integer userId=Integer.valueOf(useridstr);
			CustCustomerCreditCheck customerCreditCheck=new CustCustomerCreditCheck();
			customerCreditCheck.setApplyUserId(userId);
			customerCreditCheck.setApplyTime(new Date());
			customerCreditCheck.setCheckStatus(1);
			customerCreditCheck.setCardNo(cardNo);
			customerCreditCheck.setCustomerName(customerName);
			customerCreditCheck.setCustomerType(customerType);
			customerCreditCheck.setLoanId(Integer.valueOf(loanIdstr));
			customerModuleIntf.customerCreditCheckApply(customerCreditCheck, userId);
			
			String saveFilePath=FileUtil.contact(fileRootPath, "creditFile");
			String timePath = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			saveFilePath = FileUtil.contact(saveFilePath, timePath);
			/*String smallFilePath=FileUtil.contact(saveFilePath, "small");
			
			File smallFile=new File(smallFilePath);
			if (!smallFile.exists()) { //目录不存在，则创建相应的目录
				smallFile.mkdirs();
            }*/
			
			List<CustCreditCheckFile> filesList=new ArrayList<CustCreditCheckFile>();
			int i=0;
			for (MultipartFile multipartFile : files) {//身份证正面照片   身份证反面照片   用户授权书
				String viewFileName=multipartFile.getOriginalFilename();
				Integer fileSize=new Long(multipartFile.getSize()).intValue();
				String saveFileName=uploadCreditFile(multipartFile,saveFilePath);
				if(saveFileName==null){
					continue;
				}
				//生成缩略图
				/*try {
					ThumbUtil.zoomImageScale(new File(saveFilePath+saveFileName),
							smallFilePath+saveFileName, 400);
				} catch (IOException e) {
					log.error("生成缩略图异常 error:"+e.getMessage(),e);					
				}*/
				
				CustCreditCheckFile creditCheckFile=new CustCreditCheckFile();

				Integer n = viewFileName.indexOf("_");
				if(n>0){
					String fileType = viewFileName.substring(0,n);
					String fileTypeName = LoanCreditCheckEnum.getNameByType(fileType);
					creditCheckFile.setFileViewName(fileTypeName);
				}else{
					creditCheckFile.setFileViewName("");
				}

				creditCheckFile.setFileNameOld(viewFileName);
				creditCheckFile.setFileName(saveFileName);
				creditCheckFile.setFilePath(saveFilePath);
				creditCheckFile.setFileType(1);

				//creditCheckFile.setSmallFileName(saveFileName);
				//creditCheckFile.setSmallFilePath(smallFilePath);
				creditCheckFile.setFileSize(fileSize);
				creditCheckFile.setCustCreditCheckId(customerCreditCheck.getCustomerCreditCheckId());
				creditCheckFile.setIsDel(0);
				filesList.add(creditCheckFile);

				//征信照片备份到贷款资料里
				LoanInfoAddedFiles info = new LoanInfoAddedFiles();
				info.setLoanId(Integer.parseInt(loanIdstr));
				info.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
				info.setFileName(saveFileName);
				info.setFileSrcName(viewFileName);
				info.setFileSize(fileSize.intValue());
				info.setFilePath(saveFilePath+File.separator);
				info.setCreateDate(new Date());
				info.setUpdateDate(new Date());
				info.setFileFix(FileUtil.getFileFix(viewFileName));
				info.setFileType(LoanAddedFileType.IMAGE.type);
				String ownerId="";
				String classId="";
				if("借款人".equals(customerType)){
					ownerId="2";
					classId="6";
				}else if("担保人".equals(customerType)){
					ownerId="3";
					classId="25";
				}
				String loanProcessType = loanModule.getLoanApplyProvider().getApplyInfoById(Integer.valueOf(loanIdstr)).getLoanProcessType();
				if (StringUtil.isNotEmpty(ownerId))
					info.setOwnerId(Integer.parseInt(ownerId));
				if (StringUtil.isNotEmpty(classId))
					info.setClassId(Integer.parseInt(classId));
				if (StringUtil.isNotEmpty(loanProcessType))
					info.setLoanProcessType(loanProcessType);
				if (StringUtil.isNotEmpty(useridstr)) {
					info.setCreateUser(Integer.parseInt(useridstr));
					info.setUpdateUser(Integer.parseInt(useridstr));
				}

				info.setFileThumbImageName(saveFileName);
				info.setFileThumbImagePath(saveFilePath+File.separator);

				loanModule.getLoanAddedProvider().saveLoanAddedFileInfo(
						info);
				i++;
			}
			customerModuleIntf.saveCreditCheckFile(filesList, userId);
						
			return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("征信调查申请接口异常,",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	private String uploadCreditFile(MultipartFile file,String filePath) throws IOException{
		if(file!= null){
			String fileName = file.getOriginalFilename();								
			try {      
	            //String oldName = getUUID() + fileName.substring(fileName.lastIndexOf("."));
	            String oldName = fileName;
	            String saveFilename = FileUtil.contact(filePath, oldName);
	            // 检查文件是否存在
	            File f = new File(filePath);
	            File obj = new File(saveFilename);
	            if (!f.exists()) { //目录不存在，则创建相应的目录
	                f.mkdirs();
	                if (!obj.exists()) {
	                    obj.createNewFile();//就是在这个点抛出异常
	                }
	            }
	            //将文件上传到服务器
				FileUtils.copyToFile(file.getInputStream(),obj);
	            return oldName;
	        } catch (IOException e) {
	            log.error("", e);
	        }			
		}
		return null;
	}

	
    private String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }
    
	@NoLoginInterceptor
	@RequestMapping("/v1/queryCustomerCreditCheck")
	public ResponseEntity<String> queryCustomerCreditCheck(HttpServletRequest request,HttpServletResponse response){		
		try {
			String loanIdstr=this.getParameter("loanId");
			if(StringUtils.isBlank(loanIdstr)){
				log.error("查询贷款征信调查列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			
			Integer loanId=Integer.valueOf(loanIdstr);
			
			JSONArray resultArray=new JSONArray();
			List<CustCustomerCreditCheck> list=customerModuleIntf.getCustomerCreditCheckByLoanId(loanId);
			for (CustCustomerCreditCheck customerCreditCheck:list) { 								
				Map<String, Object> condition=new HashMap<String, Object>();
				condition.put("customerCreditCheckId", customerCreditCheck.getCustomerCreditCheckId());
				//condition.put("fileType", 1);
				List<CustCreditCheckFile> files=customerModuleIntf.queryCustCreditCheckFileList(condition);
				JSONObject checkJson=new JSONObject();
				checkJson.put("customerName", customerCreditCheck.getCustomerName());
				checkJson.put("cardNo", customerCreditCheck.getCardNo());
				/*
				if(StringUtil.isNotEmpty(customerCreditCheck.getCardNo())){
					checkJson.put("cardNoX", IdCardUtil.getIdentifyNumX(customerCreditCheck.getCardNo(),1));
				}else{
					checkJson.put("cardNoX", customerCreditCheck.getCardNo());
				}
				*/
				checkJson.put("cardNoX", customerCreditCheck.getCardNo());   //去掉脱敏
				checkJson.put("customerType", customerCreditCheck.getCustomerType());
				checkJson.put("checkStatus", customerCreditCheck.getCheckStatus());
				if(customerCreditCheck.getCheckStatus().equals(1)){
					checkJson.put("checkStatusName","等待结果");
				}else if(customerCreditCheck.getCheckStatus().equals(2)){
					checkJson.put("checkStatusName","已调查");
				}else if(customerCreditCheck.getCheckStatus().equals(3)){
					checkJson.put("checkStatusName","已拒绝");
				}else{
					checkJson.put("checkStatusName","");
				}

				checkJson.put("remark",customerCreditCheck.getCheckRemark());
				
				JSONArray fileArray=new JSONArray();
				for (CustCreditCheckFile  file : files) {
					if(file.getFileType()!=null && file.getFileType().intValue()==1) {
						JSONObject fileJson = new JSONObject();
						fileJson.put("creditCheckFileId", file.getCreditCheckFileId());
						fileJson.put("fileViewName", file.getFileViewName());
						fileJson.put("fileName", file.getFileNameOld());
						fileArray.add(fileJson);
					}
				}
				checkJson.put("files",fileArray);
				//附件文档
				JSONArray docFileArray=new JSONArray();
				for (CustCreditCheckFile  file : files) {
					if (file.getFileType() != null && file.getFileType().intValue() == 2) {
						JSONObject docFileJson = new JSONObject();
						docFileJson.put("addedFileName",file.getFileNameOld());
						docFileJson.put("addedFileId",file.getCreditCheckFileId());
						docFileArray.add(docFileJson);
					}
				}
				checkJson.put("docFiles",docFileArray);

				resultArray.add(checkJson);
			}
			
			return new ResponseEntity<String>(AppJsonResponse.success(resultArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询征信调查接口异常|",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryWaitCheckCust", method = RequestMethod.POST)
	public ResponseEntity<String> queryWaitCheckCust(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询待调查客户列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
			Integer loanId=reqObj.getInt("loanId");
			String loanProcessType=reqObj.getString("loanProcessType");
			if(loanId==null || 
					StringUtils.isEmpty(loanProcessType)){
				log.error("征信调查申请接口，loanId、loanProcessType参数为空，loanId="+loanId+
						",loanProcessType="+loanProcessType);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			
			List<CustCustomerCreditCheck> list=customerModuleIntf.getCustomerCreditCheckByLoanId(loanId);
			Map<String,String> map=new HashMap<String,String>();
			for (CustCustomerCreditCheck custCustomerCreditCheck : list) {
				map.put(custCustomerCreditCheck.getCardNo(),custCustomerCreditCheck.getCardNo());
			}

			//申请和分配时，取申请的，其他取调查的
			LoanProcessTypeEnum processType = LoanProcessTypeEnum.valueOfType(loanProcessType);
			if(processType.equals(LoanProcessTypeEnum.APPLY) || processType.equals(LoanProcessTypeEnum.ALLOT)){
				loanProcessType = LoanProcessTypeEnum.APPLY.type;
			}else{
				loanProcessType = LoanProcessTypeEnum.INVESTIGATE.type;
			}

			List<CustCustomerCreditCheck> persons=loanModule.getAllLoanPersonByLoanId(loanId, loanProcessType);
			JSONArray resultArray=new JSONArray();
			
			for (CustCustomerCreditCheck cust : persons) {
				//if(map.get(cust.getCardNo())==null){//还没有申请征信调查
					JSONObject checkJson=new JSONObject();
					checkJson.put("customerName", cust.getCustomerName());
					checkJson.put("cardNo", cust.getCardNo());
					/*
					if(StringUtil.isNotEmpty(cust.getCardNo())) {
						checkJson.put("cardNoX", IdCardUtil.getIdentifyNumX(cust.getCardNo(),1));
					}else{
						checkJson.put("cardNoX", cust.getCardNo());
					}
					*/

					checkJson.put("cardNoX", cust.getCardNo());		//去掉脱敏
					checkJson.put("customerType", cust.getCustomerType());
					resultArray.add(checkJson);
				//}
			}
			
			return new ResponseEntity<String>(AppJsonResponse.success(resultArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询待调查客户接口异常|",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
    
	/**
	 * 下载征信图片
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadCreditCheckImage")
	public void downloadCreditCheckImage(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("creditCheckFileId");
		if(StringUtil.isNotEmpty(id)){
			CustCreditCheckFile file=customerModuleIntf.queryCustCreditCheckFile(Integer.valueOf(id));
			if(file!=null){
				String path = file.getFilePath();
				String filename = file.getFileName();
				String filenameOld = file.getFileNameOld();
				downloadImageFile(id,path,filename,filenameOld,response);
			}
		}
	}

	private void downloadImageFile(String id,String path,String filename,String filenameOld,HttpServletResponse response) {
		if (StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)) {
			String imageFilename = FileUtil.contact(path, filename);
			File file = new File(imageFilename);
			if (file.exists()) {
				FileInputStream stream = null;
				OutputStream output = null;
				try {
					long fileSize = file.length();
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/force-download");// 设置强制下载不打开
					String imageName = URLEncoder.encode(filenameOld, "UTF-8");
					response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
					response.addHeader("Content-Length", fileSize + "");

					stream = new FileInputStream(file);
					output = response.getOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = stream.read(bytes)) != -1) {
						output.write(bytes, 0, len);
						output.flush();
					}
				} catch (IOException e) {
					log.error("下截征信调查图片异常 id:" + id, e);
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (Exception e) {
						}
					}
					if (output != null) {
						try {
							output.close();
						} catch (Exception e) {
						}
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
					String imageName = URLEncoder.encode(imageFilename,"UTF-8");
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

	/**
	 * 下载征信图片
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadCreditCheckFile")
	public void downloadCreditCheckFile(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("creditCheckFileId");
		if(StringUtil.isNotEmpty(id)){
			CustCreditCheckFile file=customerModuleIntf.queryCustCreditCheckFile(Integer.valueOf(id));
			if(file!=null){
				String path = file.getFilePath();
				String filename = file.getFileName();
				String filenameOld = file.getFileNameOld();
				downloadFile(id,path,filename,filenameOld,response);
			}
		}
	}

	private void downloadFile(String id,String path,String filename,String filenameOld,HttpServletResponse response) {
		if (StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)) {
			String imageFilename = FileUtil.contact(path, filename);
			File file = new File(imageFilename);
			if (file.exists()) {
				FileInputStream stream = null;
				OutputStream output = null;
				try {
					long fileSize = file.length();
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/force-download");// 设置强制下载不打开
					String imageName = URLEncoder.encode(filenameOld, "UTF-8");
					response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
					response.addHeader("Content-Length", fileSize + "");

					stream = new FileInputStream(file);
					output = response.getOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = stream.read(bytes)) != -1) {
						output.write(bytes, 0, len);
						output.flush();
					}
				} catch (IOException e) {
					log.error("下截征信调查附件异常 id:" + id, e);
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (Exception e) {
						}
					}
					if (output != null) {
						try {
							output.close();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}
	
}
