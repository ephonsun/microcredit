package banger.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banger.domain.enumerate.GroupRolesEnum;
import banger.framework.util.IdCardUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.domain.customer.CustCreditCheckFile;
import banger.domain.customer.CustCustomerCreditCheck;
import banger.domain.customer.CustCustomerCreditCheckQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.framework.util.SensitiveInfoUtils;
import banger.framework.util.StringUtil;
import banger.service.intf.ICustCreditCheckFileService;
import banger.service.intf.ICustCustomerCreditCheckService;

/**
 * 客户征信调查
 */
@Controller
@RequestMapping("/creditCheck")
public class CustomerCreditCheckController extends BaseController {
	private static final long serialVersionUID = 2829310787973784563L;
	@Autowired
	private ICustCustomerCreditCheckService custCustomerCreditCheckService;
	@Autowired
	private ICustCreditCheckFileService custCreditCheckFileService;
	@Value("${file_root_path}")  
    private String fileRootPath;


	private String BASE_PATH = "/customer/vm/credit/";
	/**
	 * 得到客户征信调查表列表页面    后台人员使用
	 * @return
	 */
	@RequestMapping("/getCustomerCreditCheckListPage")
	public String getCustCustomerCreditCheckListPage(){
		String checkStatus = getParameter("checkStatus");
		setAttribute("checkStatus",checkStatus);
		return BASE_PATH + "customerCreditCheckList";
	}

	/**
	 * 查询客户征信调查表列表数据
	 * @return
	 */
	@RequestMapping("/queryCustomerCreditCheckListData")
	@ResponseBody
	public String queryCustCustomerCreditCheckListData(CustCustomerCreditCheckQuery custCustomerCreditCheckQuery){
		Map<String,Object> condition = new HashMap<String,Object>();
		
		//查询登录用户的数据权限
		String teamGroupIds = getLoginInfo().getUserGroupPermit();
		Integer groupId = getLoginInfo().getTeamGroupId();

		//判断用户是否为客户经理，客户经理只能查本人下的征信信息
		Integer userId = getLoginInfo().getUserId();
		String userRoles = getLoginInfo().getRoleIds();
		String[] roles = userRoles.split(",");
		for (int i = 0; i<roles.length;i++){
			if(GroupRolesEnum.MANAGER.getRoleId().toString().equals(roles[i])){
				if(!isInTeamGroupIds(teamGroupIds,groupId)){
					condition.put("applyUserId",userId);
				}
			}
		}


		if(teamGroupIds==null || "".equals(teamGroupIds)){
			teamGroupIds = String.valueOf(groupId);
		}else{
			if(groupId != null){
				teamGroupIds = teamGroupIds + "," + groupId;
			}
		}
		if("null".equals(teamGroupIds)){
			condition.put("teamGroupId","-1".split(","));
		}else{
			condition.put("teamGroupId", teamGroupIds.split(","));
		}
		condition.put("customerName", custCustomerCreditCheckQuery.getCustomerName());
		condition.put("userName", custCustomerCreditCheckQuery.getUserName());
		Integer checkStatus = custCustomerCreditCheckQuery.getCheckStatus();
		if (checkStatus.intValue() == 2) {
			//征信已查包含完成和拒绝
			condition.put("checkStatus","2,3");
		}else{
			condition.put("checkStatus", custCustomerCreditCheckQuery.getCheckStatus());
		}

		IPageList<CustCustomerCreditCheckQuery> custCustomerCreditCheckList = custCustomerCreditCheckService.queryCustCustomerCreditCheckList(condition,this.getPage(),false);
		renderText(JsonUtil.toJson(custCustomerCreditCheckList, "customerCreditCheckId","customerName,cardNo,userName,applyTime,checkStatus,checkDate,userName,customerType","yyyy-MM-dd HH:mm").toString());
		return null;
	}

	private boolean isInTeamGroupIds(String teamGroupIds,Integer groupId) {
		if(!"null".equals(teamGroupIds) && StringUtil.isNotEmpty(teamGroupIds)){
			String[] teamG = teamGroupIds.split(",");
			for(int i=0;i<teamG.length;i++){
				if(groupId==Integer.parseInt(teamG[i])){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 得到客户征信调查表修改页面
	 * @return
	 */
	@RequestMapping("/getCustomerCreditCheckUpdatePage")
	public String getCustCustomerCreditCheckUpdatePage(){
		String customerCreditCheckId = getParameter("customerCreditCheckId");
		CustCustomerCreditCheckQuery custCustomerCreditCheck = custCustomerCreditCheckService.getCustCustomerCreditCheckById(Integer.parseInt(customerCreditCheckId));
		
		String identifyNum=custCustomerCreditCheck.getCardNo();
		String cutomerType=custCustomerCreditCheck.getCustomerType();//类型
		//identifyNum= IdCardUtil.getIdentifyNumX(identifyNum,2);			//去掉脱敏
		
		//查询附件列表
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("fileType", "1");
		condition.put("customerCreditCheckId", Integer.valueOf(customerCreditCheckId));
		List<CustCreditCheckFile> appFileList=custCreditCheckFileService.queryCustCreditCheckFileList(condition);
//		List<CustCreditCheckFile> appFiles = new ArrayList<CustCreditCheckFile>();
		for(int i=0;i<appFileList.size();i++){
			CustCreditCheckFile file = appFileList.get(i);
			String fileViewName = file.getFileViewName();
			if("证件正面".equals(fileViewName)){
				CustCreditCheckFile f = appFileList.get(0);
				appFileList.set(0,file);
				appFileList.set(i,f);
			}else if("证件背面".equals(fileViewName)){
				CustCreditCheckFile f = appFileList.get(1);
				appFileList.set(1,file);
				appFileList.set(i,f);
			}else if("授权书".equals(fileViewName)){
				CustCreditCheckFile f = appFileList.get(2);
				appFileList.set(2,file);
				appFileList.set(i,f);
			}
		}
		
		condition.put("fileType", "2");
		List<CustCreditCheckFile> creditFileList=custCreditCheckFileService.queryCustCreditCheckFileList(condition);
		setAttribute("userName",custCustomerCreditCheck.getUserName());
		setAttribute("applyTime", custCustomerCreditCheck.getApplyTime());
		setAttribute("checkDate",custCustomerCreditCheck.getCheckDate());
		setAttribute("customerCreditCheck",custCustomerCreditCheck);
		setAttribute("identifyNum",identifyNum);
		setAttribute("cutomerType",cutomerType);
		setAttribute("customerName",custCustomerCreditCheck.getCustomerName());
		setAttribute("appFileList",appFileList);
		setAttribute("creditFileList",creditFileList);


		if(custCustomerCreditCheck.getCheckStatus()==1){//跳转到编辑页面
			return BASE_PATH + "customerCreditCheck";
		}else{//跳转到查看页面
			return BASE_PATH + "customerCreditCheckView";
		}
	}


	/**
	 * 修改客户征信调查表数据
	 * @return
	 */
	@RequestMapping(value="/updateCustomerCreditCheck", method = RequestMethod.POST)
	@ResponseBody
	public void updateCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck){
		Integer loginUserId = getLoginInfo().getUserId();
		try {
			String  opType=this.getRequest().getParameter("opType");
			if("submit".equals(opType)){//调查提交后，修改状态
				custCustomerCreditCheck.setCheckStatus(2);
				custCustomerCreditCheck.setCheckDate(new Date());
				custCustomerCreditCheck.setCheckUserId(loginUserId);
			} else if ("refuse".equals(opType)) {
				//新增拒绝状态
				custCustomerCreditCheck.setCheckStatus(3);
				custCustomerCreditCheck.setCheckDate(new Date());
				custCustomerCreditCheck.setCheckUserId(loginUserId);
			}
			custCustomerCreditCheckService.updateCustCustomerCreditCheck(custCustomerCreditCheck,loginUserId);
			
			//处理上次的调查资料文件
			String fileNameTask=this.getRequest().getParameter("fileNameTask");
			String fileNameOld=this.getRequest().getParameter("fileNameOld");
			String fileSize=this.getRequest().getParameter("fileSize");
			String saveFilename=this.getRequest().getParameter("saveFilename");
			String fullFilenameDel=this.getRequest().getParameter("fullFilenameDel");
			//保存调查资料文件
			//新增人员资质附件
			if (fileNameTask != null && !"".equals(fileNameTask)) {
			    String[] fileNameTasks = fileNameTask.split(",");
			    String[] fileNameOlds = fileNameOld.split(",");
			    String[] fileSizes = fileSize.split(",");
			    String[] saveFilenames = saveFilename.split(",");
			   
			    for (int i = 0; i < fileNameTasks.length; i++) {
			    	CustCreditCheckFile file=new CustCreditCheckFile();
			    	file.setCustCreditCheckId(custCustomerCreditCheck.getCustomerCreditCheckId());
			    	file.setFileName(fileNameOlds[i].trim());
			    	file.setFileNameOld(fileNameTasks[i].trim());
			    	file.setFilePath(saveFilenames[i]);
			    	file.setFileSize(Integer.valueOf(fileSizes[i]));
			    	file.setFileType(2);//调查资料
			    	custCreditCheckFileService.insertCustCreditCheckFile(file, loginUserId);
			    }
			}
			//删除已取消附件
			if(!StringUtil.isNullOrEmpty(fullFilenameDel)){
				String[] fullFilenamesDel = fullFilenameDel.split(",");
				for(int i = 0; i < fullFilenamesDel.length; i++){
					File file = new File(fullFilenamesDel[i]);
					if(file.exists() && file.isFile()){
						file.delete();
					}
				}
			}
			renderText(true, "保存征信调查资料成功", "");
			return;
		} catch (Exception e) {
			log.error("保存征信调查资料报错", e);
		}		
		renderText(false, "保存征信调查资料失败",  "");
	}

	/**
	 * 删除征信资料
	 * @return
	 */
	@RequestMapping("/delCreditCheckFile")
	@ResponseBody
	public void delCreditCheckFile(){
		try {
			Integer fileId=Integer.valueOf(this.getRequest().getParameter("fileId"));
			CustCreditCheckFile creditCheckFile=custCreditCheckFileService.getCustCreditCheckFileById(fileId);
			
			String filePath=FileUtil.contact(creditCheckFile.getFilePath(), creditCheckFile.getFileName());
			File file = new File(filePath);
			if(file.exists() && file.isFile()){
				file.delete();
			}
			custCreditCheckFileService.deleteCustCreditCheckFileById(fileId);
			renderText("success");
			return;
		} catch (Exception e) {
			log.error("删除征信调查资料报错", e);
		}
		renderText("删除征信调查资料失败！");
	}
	
	
	@RequestMapping("/uploadCreditFile")
	@ResponseBody
	public String uploadCreditFile(@RequestParam(value = "fileInput", required = false) CommonsMultipartFile file) throws IOException{
		if(file!= null){
			String fileName = file.getOriginalFilename();
			String filePath = FileUtil.contact(fileRootPath, "creditFile");
						
			try {
	            String timePath = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	            //路径拼接时间
	            filePath = FileUtil.contact(filePath, timePath);
	            String oldName = getUUID() + fileName.substring(fileName.lastIndexOf("."));
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
	            return fileName + "||" + oldName + "||" + filePath;
	        } catch (IOException e) {
	            log.error("", e);
	        }
			
		}
		return null;
	}
	
    private String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.replaceAll("-","");
    }
	
	@NoLoginInterceptor
	@RequestMapping("/downloadCreditFile")
	public void downloadCreditFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Integer fileId=Integer.valueOf(this.getRequest().getParameter("fileId"));
		
		CustCreditCheckFile creditCheckFile=custCreditCheckFileService.getCustCreditCheckFileById(fileId);
		
		try {
			String filePath=FileUtil.contact(creditCheckFile.getFilePath(), creditCheckFile.getFileName());
			File file = new File(filePath);
			if(file.exists() && file.isFile()){           	
	            FileInputStream fis = new FileInputStream(file);	           
	            response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(creditCheckFile.getFileNameOld(),"utf-8").replace("+", "%20"));
	
	            //this.getResponse().setContentType("movie/*"); //设置返回的文件类型 
	            OutputStream output = this.getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象 
	            BufferedInputStream bis = new BufferedInputStream(fis);//输入缓冲流      
	            BufferedOutputStream bos = new BufferedOutputStream(output);//输出缓冲流     
	
	            byte data[] = new byte[4096];//缓冲字节数    
	
	            int size = 0;
	            size = bis.read(data);
	            while (size != -1) {
	                bos.write(data, 0, size);
	                size = bis.read(data);
	            }
	            bis.close();
	            bos.flush();//清空输出缓冲流     
	            bos.close();
	            output.close();
            }else{
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                log.error("下载征信调查资料失败,文件不存在");
            	 out.print("下载的征信调查资料失败，请重新下载，谢谢！");
                 out.close();
            }	
        }catch (Exception e) {
			log.error("下载征信调查资料文件的时候没有找到文件",e);
		}		
		
	}
	
	/**
	 * 跳转到图片查看页面
	 * @return
	 */
	@RequestMapping("/showImageDiv")
	public String showImageDiv(){
		try {
            String fileName = URLDecoder.decode(this.getRequest().getParameter("fileName"), "UTF-8");
            String filePath = URLDecoder.decode(this.getRequest().getParameter("filePath"), "UTF-8");
            setAttribute("fileName",fileName);
            setAttribute("filePath",filePath);
        }catch (Exception e){
            log.error("",e);
        }
		return BASE_PATH + "vPhotoDiv";
	}
	
    /**
     * 获取资质图片
     */
	@NoLoginInterceptor
	@RequestMapping("/getImageThumbnail")
    public String getImageThumbnail() {
        FileInputStream file = null;
        try {
            String fileName = URLDecoder.decode(this.getRequest().getParameter("fileName"), "UTF-8");
            String filePath = URLDecoder.decode(this.getRequest().getParameter("filePath"), "UTF-8");
            String filePathAndFileName = FileUtil.contact(filePath, fileName);
            if (!FileUtil.isExistFile(filePathAndFileName)) {
                return null;
            }
            file = new FileInputStream(filePathAndFileName);
            this.getResponse().setContentType("image/*"); // 设置返回的文件类型

            OutputStream output = this.getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
            BufferedInputStream bis = new BufferedInputStream(file);// 输入缓冲流
            BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
            byte data[] = new byte[4096];// 缓冲字节数
            int size = 0;
            size = bis.read(data);
            while (size != -1) {
                bos.write(data, 0, size);
                size = bis.read(data);
            }
            bis.close();
            bos.flush();// 清空输出缓冲流
            bos.close();
            output.close();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

}
