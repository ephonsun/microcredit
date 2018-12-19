/*
 * banger Inc.
 * ToDo       :This is Class Description...
 * Author     :Administrator
 * Create Date:2014-3-5
 */
package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.common.tools.NumberToCN;
import banger.common.tools.PoiExcelToHtmlUtil;
import banger.common.tools.StringUtil;
import banger.dao.intf.IApplyInfoDao;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.enumerate.*;
import banger.domain.loan.*;
import banger.domain.loan.contract.LoanContractExportRecord;
import banger.domain.loan.contract.LoanContractRelateItem;
import banger.domain.loan.contract.LoanContractTemplateFile;
import banger.domain.loan.contract.LoanContractTemplateType;
import banger.domain.permission.PmsUser;
import banger.framework.collection.DataColumn;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.FormatUtil;
import banger.framework.util.IdCardUtil;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ICustomerBlackProvider;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.IPotentialCustomersProvider;
import banger.service.intf.*;
import banger.socket.SocketDemo;
import banger.util.CodedProduceUtil;
import banger.util.MoneyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/contract")
public class LoanContractController extends BaseController {

	private static final long serialVersionUID = 1746006285862576499L;
	@Resource
	private ICustomerBlackProvider customerBlackProvider;
	@Autowired
	private IPotentialCustomersProvider potentialCustomersProvider;
	@Autowired
	private IRepayPlanInfoService repayPlanInfoService;
	@Resource
	private ILoanApplyService loanApplyService;
	@Autowired
	private IInfoAddedFilesService infoAddedFilesService;

	@Autowired
	private IContractService contractService;
	@Autowired
	private IApplyInfoService applyInfoService;
	@Resource
	IConfigModule configModule;
	@Autowired
	private IPermissionModule permissionModule;
	@Resource
	private IApplyInfoDao applyInfoDao;
	@Autowired
	private ICurrentAuditStatusService currentAuditStatusService;
	@Autowired
	private ILoanOperationService loanOperationService;
	@Autowired
	private SocketDemo socketDemo;
	@Autowired
	private IProfitLossInfoService profitLossInfoService;
	@Autowired
	private IAssetsInfoService assetsInfoService;

	@Value("${file_root_path}")
	private String fileRootPath;
	/**
	 * @return
	 */
	@RequestMapping("getContractTypeListPage")
	public String getContractTypeListPage(HttpServletRequest request){
		return "/system/vm/contractTemplateTypeList";
	}

	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("queryContractTypeList")
	@ResponseBody
	public void queryContractTypeList(){

		Map<String, Object> condition = new HashMap<String, Object>();


		List<LoanContractTemplateType> typeList = contractService.queryContractTemplateTypeList(condition);

		renderText(JsonUtil.toJson(typeList, "id", "id,typeName,typeLevel,sortNo,remark,isDel,createUser,createDate,updateUser,updateDate", "yyyy-MM-dd").toString());
	}


	/**
	 * 新增
	 * @return
	 */
	@RequestMapping("addContractType")
	@ResponseBody
	public void addContractType(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();
		String typeName = this.getParameter("typeName");
		if(StringUtils.isBlank(typeName)){
			renderText(false,"请输入分类名称！",null);
			return;
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(typeName)){
			condition.put("typeName",typeName);
		}
		List<LoanContractTemplateType> typeList = contractService.queryContractTemplateTypeList(condition);
		if (!CollectionUtils.isEmpty(typeList)){
			renderText(false,"分类已存在！",null);
			return;
		}

		LoanContractTemplateType templateType = new LoanContractTemplateType();
		templateType.setTypeName(typeName);
		templateType.setTypeLevel(1);
		templateType.setIsDel(0);
		contractService.insertContractTemplateType(templateType,userId);

		renderText(true, "", null);
	}




	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("deleteContractTypeById")
	@ResponseBody
	public void deleteContractTypeById(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();
		String typeId = this.getParameter("typeId");

		if(StringUtils.isNotBlank(typeId)&&StringUtils.isNumeric(typeId)){

			List<LoanContractTemplateFile> list = contractService.queryTemplateFileByTypeId(Integer.valueOf(typeId));
			if(list != null  && list.size() > 0){
				renderText(false,"该类型已存在合同模版配置，不能删除！",null);
				return;
			}else{
				contractService.deleteContractTemplateTypeById(Integer.valueOf(typeId));
				renderText(true,"success",null);
			}

		}
	}




	/**
	 * @return
	 */
	@RequestMapping("getContractTemplateRelatePage")
	public String getLoanTemplateRelatePage(HttpServletRequest request){
		String loanTypeId = this.getParameter("loanTypeId");
		request.setAttribute("loanTypeId",loanTypeId);
		return "/system/vm/contractTemplateRelate";
	}

	@RequestMapping("/getTemplateSelectFileList")
	@ResponseBody
	public void getTemplateSelectFile() {
		String loanTypeId = this.getParameter("loanTypeId");
		String fileJson = this.contractService.getTemplateSelectFile(loanTypeId);
		this.renderText(fileJson);
	}

	/**
	 * @return
	 */
	@RequestMapping("getExportContractPage")
	public String getExportContractPage(HttpServletRequest request){
		String loanId = this.getParameter("loanId");
		String loanTypeId = this.getParameter("loanTypeId");
		request.setAttribute("loanTypeId", loanTypeId);
		request.setAttribute("loanId",loanId);
		return "/system/vm/contractExportPage";
	}

	@RequestMapping("/getExportTemplateFileList")
	@ResponseBody
	public void getExportTemplateFileList() {
		String loanTypeId = this.getParameter("loanTypeId");
		String fileJson = this.contractService.getExportTemplateFile(loanTypeId);
		this.renderText(fileJson);
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping("saveContractTemplateRelate")
	@ResponseBody
	public void saveContractTemplateRelate(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();
		String templateFileIds = this.getParameter("templateFileIds");
		if(StringUtils.isBlank(templateFileIds)){
			renderText(false,"请选择合同模版！",null);
			return;
		}

		String loanTypeId = this.getParameter("loanTypeId");
		if(StringUtils.isBlank(loanTypeId)||!StringUtils.isNumeric(loanTypeId)){
			renderText(false,"数据异常，请刷新重试！",null);
			return;
		}

		//删除
		contractService.deleteContractRelateItemByLoanTypeId(Integer.valueOf(loanTypeId));

		//新增
		String[] fileIds = templateFileIds.split(",");
		if (null!=fileIds){
			LoanContractRelateItem relateItem;
			for (String fileId : fileIds) {
				if(StringUtils.isNotBlank(fileId)&&StringUtils.isNumeric(fileId)){
					relateItem = new LoanContractRelateItem();
					relateItem.setLoanTypeId(Integer.valueOf(loanTypeId));
					relateItem.setTemplateFileId(Integer.valueOf(fileId));
					contractService.insertContractRelateItem(relateItem,userId);
				}
			}
		}

		renderText(true, "", null);
	}


	//-------------------------------------------------------------------------------------------------


	/**
	 * @return
	 */
	@RequestMapping("getTemplateFileListPage")
	public String getTemplateFileListPage(HttpServletRequest request){
		return "/system/vm/contractTemplateFileList";
	}

	/**
	 * @return
	 */
	@RequestMapping("getTemplateFilePage")
	public String getTemplateFilePage(HttpServletRequest request) {
		String idStr = this.getRequest().getParameter("id");
		if(StringUtils.isNotBlank(idStr)&&StringUtils.isNumeric(idStr)){
			LoanContractTemplateFile templateFile = contractService.getContractTemplateFileById(Integer.valueOf(idStr));
			request.setAttribute("templateFile",templateFile);

			//预览
			if(null!=templateFile&&StringUtils.isNotBlank(templateFile.getFilePath())&&StringUtils.isNotBlank(templateFile.getFileName())){
				try{
					String saveFilename = FileUtil.contact(templateFile.getFilePath(), templateFile.getFileName());
					File saveFile = new File(saveFilename);
					if(saveFile.exists()){
						//删除网页缓存数据
						String dirPath = "tmp_file" + File.separator + "html" + File.separator;
						String fullPath = FileUtil.contact(this.getRootPath(), dirPath );
						deleteDir(new File(FileUtil.contact(this.getRootPath(), "tmp_file")));


						//预览
						String configFileName = FileUtil.getFileName(saveFilename);
						String tmpName = UUID.randomUUID().toString().replace("-", "") + FileUtil.getFileFix(configFileName);
						File tmpFile = new File(FileUtil.contact(fullPath, tmpName));
						File dir = new File(fullPath);
						if (!dir.exists()) {
							dir.mkdirs();
							if (!tmpFile.exists())
								tmpFile.createNewFile();
						}
						String htmlPath="";
						String fix = FileUtil.getFileFix(configFileName);
						if(".docx".equals(FileUtil.getFileFix(configFileName))) {
							htmlPath = PoiWord07ToHtml(saveFile, tmpFile);
						}
						if(".xls".equals(FileUtil.getFileFix(configFileName))||".xlsx".equals(FileUtil.getFileFix(configFileName))){
							htmlPath = poiExcelToHtml(saveFile.getPath());
						}
//						String htmlPath = null;
//						htmlPath = PoiWord07ToHtml (saveFile,tmpFile);


						request.setAttribute("htmlPath",htmlPath);
						tmpFile.delete();
					}
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}

		return "/system/vm/contractTemplateFile";
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * @return
	 */
	@RequestMapping("downloadContractFile")
	@ResponseBody
	public void downloadContractFile(HttpServletRequest request) {
		String idStr = this.getRequest().getParameter("id");
		if (StringUtils.isNotBlank(idStr) && StringUtils.isNumeric(idStr)) {
			LoanContractTemplateFile templateFile = contractService.getContractTemplateFileById(Integer.valueOf(idStr));
			if (null != templateFile && StringUtils.isNotBlank(templateFile.getFilePath())&& StringUtils.isNotBlank(templateFile.getFileName())) {
				String saveFilename = FileUtil.contact(templateFile.getFilePath(), templateFile.getFileName());
				File file = new File(saveFilename);
				if (file.exists()) {
					try {
						FileInputStream fis = new FileInputStream(file);
						this.getResponse().addHeader(
								"Content-Disposition",
								"attachment;filename="+ URLEncoder.encode(templateFile.getOldFileName()+"."+templateFile.getFileName().split("\\.")[templateFile.getFileName().split("\\.").length-1],"utf-8").replace("+", "%20"));
						this.getResponse().setContentType("docx/*"); // 设置返回的文件类型
						OutputStream output = this.getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
						BufferedInputStream bis = new BufferedInputStream(fis);// 输入缓冲流
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
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						log.error("下载文件 error:" + e.getMessage(), e);
					} catch (IOException e) {
						e.printStackTrace();
						log.error("下载文件 error:" + e.getMessage(), e);
					}
				}
			}
		}


	}

	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("queryContractFileList")
	@ResponseBody
	public void queryContractFileList(){

		Map<String, Object> condition = new HashMap<String, Object>();

		List<LoanContractTemplateFile> fileList = contractService.queryContractTemplateFileList(condition);

		renderText(JsonUtil.toJson(fileList, "id", "id,templateTypeId,templateTypeName,oldFileName,fileName,filePath,sortNo,remark,isDel,createUser,createDate,updateUser,updateDate", "yyyy-MM-dd").toString());
	}


	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("deleteContractFileById")
	@ResponseBody
	public void deleteContractFileById(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();
		String idStr = this.getParameter("id");

		if(StringUtils.isNotBlank(idStr)&&StringUtils.isNumeric(idStr)){
			contractService.deleteContractTemplateFileById(Integer.valueOf(idStr));
			renderText(true,"success",null);
		}
	}


	/**
	 * 上传文件
	 *
	 * @return
	 */
	@RequestMapping("uploadContractFile")
	@ResponseBody
	public void uploadContractFile( @RequestParam MultipartFile uploadFile) {
		try {

			String flag = this.getRequest().getParameter("flag");

			// 保存文件
			String fullFilename = URLDecoder.decode(this.getRequest().getParameter("fullFilename"), "UTF-8");
			this.getResponse().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("text/html;charset=UTF-8");

			String path = this.fileRootPath + File.separator + "contract" + File.separator;
			File filePath = new File(path);
			if (!filePath.exists()) {// 文件不存在则创建
				filePath.mkdirs();
			}

			String configFileName = FileUtil.getFileName(fullFilename);

			String fileName = UUID.randomUUID().toString().replace("-", "") + FileUtil.getFileFix(configFileName);
			File templateFile = new File(FileUtil.contact(path, fileName));

			//写文件
			inputStreamToFile(uploadFile.getInputStream(),templateFile);

			//删除网页缓存数据
			String dirPath = "tmp_file" + File.separator + "html" + File.separator;
			String fullPath = FileUtil.contact(this.getRootPath(), dirPath );
			deleteDir(new File(FileUtil.contact(this.getRootPath(), "tmp_file")));

			JSONObject jo = new JSONObject();

			//预览
			String tmpName = UUID.randomUUID().toString().replace("-", "") + FileUtil.getFileFix(configFileName);
			File tmpFile = new File(FileUtil.contact(fullPath, tmpName));
			File dir = new File(fullPath);
			if (!dir.exists()) {
				dir.mkdirs();
				if (!tmpFile.exists())
					tmpFile.createNewFile();
			}

//			String htmlPath = PoiWord07ToHtml (templateFile,tmpFile);
//			jo.put("htmlPath",htmlPath);
			String htmlPath="";
			String fix = FileUtil.getFileFix(configFileName);
			if(".docx".equals(fix)) {
				htmlPath = PoiWord07ToHtml(templateFile, tmpFile);
			}
			if(".xls".equals(fix)||".xlsx".equals(fix)){
				htmlPath = poiExcelToHtml(templateFile.getPath());
			}
			jo.put("htmlPath",htmlPath);
			tmpFile.delete();
			//判断是否删除文件 0删除 1保存
			if("0".equalsIgnoreCase(flag)){
				templateFile.delete();
				jo.put("fileName","");
				jo.put("filePath","");
			}else{
				jo.put("fileName",fileName);
				jo.put("filePath",path);
			}

			this.renderText(true, "success", jo.toString());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}

	}

	/**
	 * 保存文件
	 *
	 * @return
	 */
	@RequestMapping("saveContractFile")
	@ResponseBody
	public void saveContractFile( ) {
		try {

			Integer userId = this.getLoginInfo().getUserId();
			String templateTypeId = this.getRequest().getParameter("templateTypeId");
			String oldFileName = this.getRequest().getParameter("oldFileName");
			String fileName = this.getRequest().getParameter("fileName");
			String filePath = this.getRequest().getParameter("filePath");
			String idStr = this.getRequest().getParameter("id");

			if(StringUtils.isBlank(templateTypeId)||!StringUtils.isNumeric(templateTypeId)){
				this.renderText(false, "请选择合同类型！", "");
				return;
			}
			if(StringUtils.isBlank(fileName)||StringUtils.isBlank(filePath)){
				this.renderText(false, "请选择合同文件！", "");
				return;
			}
			if(StringUtils.isBlank(oldFileName)){
				this.renderText(false, "请填写合同名称！", "");
				return;
			}

			Integer id = 0;
			if(StringUtils.isNotBlank(idStr)&&StringUtils.isNumeric(idStr)){
				id = Integer.valueOf(idStr);
			}

			//查询 oldFileName 是否存在
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("templateTypeId",templateTypeId);
			condition.put("oldFileName",oldFileName);
			if(id>0){
				condition.put("id",id);
			}

			List<LoanContractTemplateFile> fileList = contractService.queryContractTemplateFileList(condition);
			if(!CollectionUtils.isEmpty(fileList)){
				this.renderText(false, "合同名称已存在,请重新输入！", "");
				return;
			}

			//
			LoanContractTemplateFile templateFile = new LoanContractTemplateFile();
			if(id>0){//修改
				templateFile.setId(id);
				templateFile.setOldFileName(StringUtil.isNotEmpty(oldFileName) ? oldFileName : fileName);
				templateFile.setFileName(fileName);
				templateFile.setFilePath(filePath);
				templateFile.setTemplateTypeId(Integer.valueOf(templateTypeId));
				contractService.updateContractTemplateFile(templateFile, userId);
			}else{//新增
				templateFile.setOldFileName(StringUtil.isNotEmpty(oldFileName) ? oldFileName : fileName);
				templateFile.setFileName(fileName);
				templateFile.setFilePath(filePath);
				templateFile.setTemplateTypeId(Integer.valueOf(templateTypeId));
				templateFile.setSortNo(1);
				templateFile.setIsDel(0);
				contractService.insertContractTemplateFile(templateFile,userId);
			}

			this.renderText(true, "保存成功！", templateFile.getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}

	}



	/**
	 * @return
	 */
	@RequestMapping("getHtmlViewPage")
	public String getHtmlViewPage(){
		String htmlPath = this.getRequest().getParameter("htmlPath");
		try {
			htmlPath = URLDecoder.decode(htmlPath,"UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		if(StringUtils.isNotBlank(htmlPath)&&htmlPath.contains(".")){
			return htmlPath.split("\\.")[0];
		}else{
			return "";
		}
	}


	private String PoiWord07ToHtml (File wordFile,File tmpFile) throws IOException{

		//预览合同 替换标记
		replaceFileContent(tmpFile.getPath(), wordFile.getPath());

		String dirPath = "tmp_file" + File.separator + "html" + File.separator;
		String fullPath = FileUtil.contact(this.getRootPath(), dirPath );
		String imagePath = fullPath + "image" + File.separator;
		String htmlName = UUID.randomUUID() + ".vm";
		File htmlFile = new File(fullPath + htmlName);

		if (tmpFile.exists()) {
			//读取文档内容
			InputStream in = new FileInputStream(tmpFile);
			XWPFDocument document = new XWPFDocument(in);

			File imageFolderFile = new File(imagePath);
			if(!imageFolderFile.exists()){
				imageFolderFile.mkdirs();
			}

			//加载html页面时图片路径
			XHTMLOptions options = XHTMLOptions.create().URIResolver( new FileURIResolver(imageFolderFile));
			//图片保存文件夹路径
			options.setExtractor(new FileImageExtractor(imageFolderFile));
			OutputStream out = new FileOutputStream(htmlFile);
			XHTMLConverter.getInstance().convert(document, out, options);
			out.close();

		}else{
			return "";
		}

		return dirPath+htmlName;

	}

//	private String PoiWord07ToHtml (File wordFile,File tmpFile) throws IOException, TransformerException, ParserConfigurationException {
//
//		//预览合同 替换标记
//		replaceFileContent(tmpFile.getPath(), wordFile.getPath());
//
//		String dirPath = "tmp_file" + File.separator + "html" + File.separator;
//		String fullPath = FileUtil.contact(this.getRootPath(), dirPath );
//		String imagePath = fullPath + "image" + File.separator;
//		String htmlName = UUID.randomUUID() + ".vm";
//		File htmlFile = new File(fullPath + htmlName);
//
//		if (tmpFile.exists()) {
//			Word2Html.convert2Html(tmpFile.getAbsolutePath(),fullPath + htmlName,imagePath);
//		}else{
//			return "";
//		}
//
//		return dirPath+htmlName;
//
//	}


//	/**
//	 * 2007版本word转换成html 2017-2-27
//	 * @param wordPath word文件路径
//	 * @param wordName word文件名称无后缀
//	 * @param suffix  word文件后缀
//	 * @return
//	 * @throws IOException
//	 */
//	public static String Word2007ToHtml(File wordFile) throws IOException {
//
////		String htmlPath = FileUtil.contact(this.getRootPath(), "tmp_file" + File.separator + "html" + File.separator );
//		String htmlPath = FileUtil.contact("C:\\Users\\Administrator\\Desktop\\小微贷\\", "tmp_file" + File.separator + "html" + File.separator );
////		String htmlPath = wordPath + File.separator + wordName + "_show" + File.separator;
//		String htmlName = UUID.randomUUID() + ".html";
//		String imagePath = htmlPath + "image" + File.separator;
//
//		//判断html文件是否存在
//		File htmlFile = new File(htmlPath + htmlName);
//		if(htmlFile.exists()){
//			return htmlFile.getAbsolutePath();
//		}
//
//		//word文件
////		File wordFile = new File("C:\\Users\\Administrator\\Desktop\\小微贷\\新建 Microsoft Office Word 文档.docx");
//
//		// 1) 加载word文档生成 XWPFDocument对象
//		InputStream in = new FileInputStream(wordFile);
//		XWPFDocument document = new XWPFDocument(in);
//
//		// 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
//		File imgFolder = new File(imagePath);
//		XHTMLOptions options = XHTMLOptions.create();
//		options.setExtractor(new FileImageExtractor(imgFolder));
//		//html中图片的路径 相对路径
//		options.URIResolver(new BasicURIResolver("image"));
//		options.setIgnoreStylesIfUnused(false);
//		options.setFragment(true);
//
//		// 3) 将 XWPFDocument转换成XHTML
//		//生成html文件上级文件夹
//		File folder = new File(htmlPath);
//		if(!folder.exists()){
//			folder.mkdirs();
//		}
//		OutputStream out = new FileOutputStream(htmlFile);
//		XHTMLConverter.getInstance().convert(document, out, options);
//
//		return htmlFile.getAbsolutePath();
//	}

	private void inputStreamToFile(InputStream ins,File file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}

	private Map<File,String> fileMap = new HashMap<File,String>();
	/**
	 *
	 * @return
	 */
	@RequestMapping("exportContractFile")
	@ResponseBody
	public synchronized void exportContractFile(HttpServletRequest request){

		try{
			Integer userId = this.getLoginInfo().getUserId();
			String templateFileIds = this.getParameter("templateFileIds");
			if(StringUtils.isBlank(templateFileIds)){
				renderText(false,"请选择您要导出的合同！",null);
				return;
			}

			String loanIdStr = this.getParameter("loanId");
			if(StringUtils.isBlank(loanIdStr)||!StringUtils.isNumeric(loanIdStr)){
				renderText(false,"数据异常，请刷新重试！",null);
				return;
			}

			//临时文件目录
			String dirPath = FileUtil.contact(this.getRootPath(), "tmp_file" + File.separator + "docx" + File.separator );
			//
			String customerName = "";
			//
			String[] fileIds = templateFileIds.split(",");
			if (null!=fileIds){
				LoanContractTemplateFile templateFile;
				LoanContractExportRecord exportRecord;
				Date nowDate = new Date();
				List<File> subs = new ArrayList<File>();
				// 贷款信息集合
				Map<String, String> map = getLoanInfoMap(Integer.valueOf(loanIdStr));
				customerName = MapUtils.getString(map,"个人信息.客户姓名","");
				for (String fileId : fileIds) {
					templateFile = contractService.getContractTemplateFileById(Integer.valueOf(fileId));
					if (null != templateFile && StringUtils.isNotBlank(templateFile.getFilePath())&& StringUtils.isNotBlank(templateFile.getFileName())) {
						String saveFilename = FileUtil.contact(templateFile.getFilePath(), templateFile.getFileName());
						String filePath = FileUtil.contact(dirPath, templateFile.getOldFileName()+"."+templateFile.getFileName().split("\\.")[templateFile.getFileName().split("\\.").length-1] );
						//临时docx文件 文件目录 文件路径
						File dir = new File(dirPath);
						File file = new File(filePath);
						if (!dir.exists()) {
							dir.mkdirs();
							if (!file.exists())
								file.createNewFile();
						}
						String fix = FileUtil.getFileFix(saveFilename);
						addMapInfo(Integer.valueOf(loanIdStr),map);
						if(".docx".equals(fix)) {
							writeFileContent(filePath,saveFilename,map);
						}
						if(".xls".equals(fix)||".xlsx".equals(fix)){
							writeExcelFileContent(filePath,saveFilename,map);
						}
						//word
//						writeFileContent(filePath,saveFilename,map);


						//
						if (file.exists()) {
//							fileMap.put(file, templateFile.getTemplateTypeId() + "." + templateFile.getOldFileName() + "." + templateFile.getFileName().split("\\.")[templateFile.getFileName().split("\\.").length - 1]);
							fileMap.put(file, templateFile.getOldFileName() + "." + templateFile.getFileName().split("\\.")[templateFile.getFileName().split("\\.").length - 1]);
//							String pdfDir = "";
//							if(".docx".equals(fix)) {
//								pdfDir = file.getAbsolutePath().replace(".docx",".pdf");
//								PDFUtil.word2PDF(file.getAbsolutePath(),pdfDir);
//							}
//							if(".xls".equals(fix)||".xlsx".equals(fix)){
//								pdfDir = file.getAbsolutePath().replace(".xlsx",".pdf").replace(".xls", ".pdf");
//								PDFUtil.excel2PDF(file.getAbsolutePath(), pdfDir);
//							}
//							subs.add(new File(pdfDir));
							subs.add(file);
						}

						//记录合同导出日志
						exportRecord = new LoanContractExportRecord();
						exportRecord.setLoanId(Integer.valueOf(loanIdStr));
						exportRecord.setTemplateFileId(templateFile.getId());
						exportRecord.setTemplateFileName(templateFile.getOldFileName());
						exportRecord.setExportTime(nowDate);
						exportRecord.setExportUserId(userId);
						contractService.insertContractExportRecord(exportRecord,userId);
					}
				}

				this.getResponse().setContentType("APPLICATION/OCTET-STREAM");
				this.getResponse().setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(this.getZipFilename(customerName), "utf-8"));
				ZipOutputStream zos = new ZipOutputStream(this.getResponse().getOutputStream());
				zos.setEncoding("GBK");
				zipFile(subs, "",customerName, zos);
				zos.flush();
				zos.closeEntry();
				zos.close();
			}
		}catch (Exception e){
			e.printStackTrace();
			this.log.error(e);
		}finally {
			//删除缓存文件
			deleteDir(new File(FileUtil.contact(this.getRootPath(), "tmp_file")));
		}

	}

	private void replaceFileContent(String filePath, String saveFilename) throws IOException{

		//写文件
		XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(saveFilename));
		List<XWPFParagraph> list = document.getParagraphs();
		if (list != null && list.size() > 0) {
			for (XWPFParagraph paragraph : list) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String text = run.getText(0);
					if (text != null) {
						//当text没有匹配时候 使用正则表达式校验是否为替换文本格式 如果是 则替换为空
						String flag = findReplaceTextFlag(text);
						if(StringUtils.isNotBlank(flag)){
							run.setColor("00EE00");
						}

					}
				}
			}
		}

		//表格内容替换添加
		Iterator it = document.getTablesIterator();
		while(it.hasNext()){
			XWPFTable table = (XWPFTable)it.next();
			int rcount = table.getNumberOfRows();
			for(int i =0 ;i < rcount;i++) {
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for (XWPFTableCell cell : cells){
//					String text = cell.getText();
//					if (text != null) {
//						//当text没有匹配时候 使用正则表达式校验是否为替换文本格式 如果是 则替换为空
//						String flag = findReplaceTextFlag(text);
//						if(StringUtils.isNotBlank(flag)){
//							cell.setColor("00EE00");
//						}
//					}
					list = cell.getParagraphs();
					for (XWPFParagraph paragraph : list) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun run : runs) {
							String text = run.getText(0);
							if (text != null) {
								//当text没有匹配时候 使用正则表达式校验是否为替换文本格式 如果是 则替换为空
								String flag = findReplaceTextFlag(text);
								if(StringUtils.isNotBlank(flag)){
									run.setColor("00EE00");
								}
							}
						}
					}
				}
			}
		}
		FileOutputStream os = new FileOutputStream(new File(filePath));
		if(null!=document){
			document.write(os);
		}
		os.close();


	}

	private void writeFileContent(String filePath, String saveFilename, Map<String, String> map) throws IOException{

		//写文件
		XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(saveFilename));
		List<XWPFParagraph> list = document.getParagraphs();
		if (list != null && list.size() > 0) {
			for (XWPFParagraph paragraph : list) {

				List<XWPFRun> runs = paragraph.getRuns();

				for (int i = 0; i < runs.size(); i++) {
					XWPFRun run = runs.get(i);
					String text = run.getText(0);
					if (text != null) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							String key = entry.getKey();
							if (text.indexOf( key ) != -1) {
								text = text.replace("[" + key + "]", null == entry.getValue()?"":entry.getValue());
								if(text.indexOf("\r")>-1) {
									String[] texts = text.split("\r");
									for(int f=texts.length-1;f>=0;f--) {
										run.setText("", 0);
										XWPFRun targetRun = paragraph.insertNewRun(i);
//										target.getCTR().setRPr(source.getCTR().getRPr());
										copyRun(targetRun, run);
										targetRun.setText(texts[f]);
										if(f!=0) {
											paragraph.insertNewRun(i).addCarriageReturn();//硬回车
										}
									}
//									paragraph.removeRun(i);
								}else {
									run.setText(text, 0);
								}
							}
						}

						//当text没有匹配时候 使用正则表达式校验是否为替换文本格式 如果是 则替换为空
						List<String> flagList = findTextFlag(text);
						if(CollectionUtils.isNotEmpty(flagList)){
							for(String flag : flagList){
								text = text.replace(flag, "");
							}
							run.setText(text, 0);
						}
					}
				}


				//特殊标记处理
//				replaceParagraphFlag(paragraph,runs);

			}
		}

		//表格内容替换添加
		Iterator it = document.getTablesIterator();
		while(it.hasNext()){
			XWPFTable table = (XWPFTable)it.next();
			int rcount = table.getNumberOfRows();
			for(int i =0 ;i < rcount;i++){
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells =  row.getTableCells();
				for (XWPFTableCell cell : cells){
					String text = cell.getText();
					if (text != null) {
						List<String> flagList = findTextFlag(text);
						if (CollectionUtils.isNotEmpty(flagList)) {
							for (String flag : flagList) {
								if(map.containsKey(flag.substring(1,flag.length()-1))){
									String str = MapUtils.getString(map,flag.substring(1,flag.length()-1),"");
									text = text.replace(flag, str);
								}else{
									text = text.replace(flag, "");
								}
							}
							clearAndSetCellVal(cell, text);
						}
					}
				}
			}
		}
		FileOutputStream os = new FileOutputStream(new File(filePath));
		if(null!=document){
			document.write(os);
		}
		os.close();


	}

	private void copyRun(XWPFRun targetRun, XWPFRun run) {
		if(run!=null){
			targetRun.setBold(run.isBold());
			targetRun.setItalic(run.isItalic());
			targetRun.setStrike(run.isStrike());
			targetRun.setUnderline(run.getUnderline());
			targetRun.setColor(run.getColor());
			targetRun.setTextPosition(run.getTextPosition());
			if(run.getFontSize()!=-1){
				targetRun.setFontSize(run.getFontSize());
			}
			if(run.getFontFamily()!=null){
				targetRun.setFontFamily(run.getFontFamily());
			}
			if(run.getCTR()!=null){
				if(run.getCTR().isSetRPr()){
					CTRPr tmpRPr =run.getCTR().getRPr();
					if(tmpRPr.isSetRFonts()){
						CTFonts tmpFonts=tmpRPr.getRFonts();
						CTRPr cellRPr=targetRun.getCTR().isSetRPr() ? targetRun.getCTR().getRPr() : targetRun.getCTR().addNewRPr();
						CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr.getRFonts() : cellRPr.addNewRFonts();
						cellFonts.setAscii(tmpFonts.getAscii());
						cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
						cellFonts.setCs(tmpFonts.getCs());
						cellFonts.setCstheme(tmpFonts.getCstheme());
						cellFonts.setEastAsia(tmpFonts.getEastAsia());
						cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
						cellFonts.setHAnsi(tmpFonts.getHAnsi());
						cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
					}
				}
			}
		}
	}

	private void clearAndSetCellVal(XWPFTableCell cell, String text) {
		int p = 0;
		List<XWPFParagraph> paragraphList = cell.getParagraphs();
		if(!CollectionUtils.isEmpty(paragraphList)){
			p = paragraphList.size();
		}
		for (int i = 0; i < p; i++) {
			cell.removeParagraph(0);
		}

		cell.setText(text);
	}

	//	列表处理 正则表达式标记
	private String findReplaceTextFlag(String text) {
		// 要验证的字符串
//			String str = "[中文.中1文12]";
		// 验证规则
		String regEx = "\\S*(\\[\\S+\\.{1}\\S+])\\S*";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);

		StringBuffer sbf = new StringBuffer();
		sbf.toString();
		while (matcher.find()) {
			sbf.append(matcher.group(1));
		}

		// 字符串是否与正则表达式相匹配
		return sbf.toString();
	}

	//	列表处理 正则表达式标记
	private static List<String> findTextFlag(String text) {
		// 要验证的字符串
//			String str = "[中文.中1文12]";
		// 验证规则
		String regEx = "\\S*(\\[\\D+\\.?\\S+])\\S*";
//		String regEx = "\\S*(\\[[^\\x00-\\xff]+\\.{1}[^\\x00-\\xff]+[0-9]{0,2}])\\S*";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);

		List<String> flagList = new ArrayList<String>();
		while (matcher.find()) {
			flagList.add(matcher.group(1));
		}

		// 字符串是否与正则表达式相匹配
		return flagList;
	}


//	private void replaceParagraphFlag(XWPFParagraph paragraph, List<XWPFRun> runs) {
//
//		String paragraphText = paragraph.getParagraphText();
////				paragraph.equals().
//		//TABLE.COPY.1.担保人
//		if(paragraphText.startsWith("TABLE.COPY.")){
//			for (XWPFRun run : runs) {
//				run.setText("",0);
//			}
//		}
//		//TABLE.ADD.2.担保人.姓名,身份证号
//		else if(paragraphText.startsWith("TABLE.ADD.")){
//			for (XWPFRun run : runs) {
//				run.setText("",0);
//			}
//		}
//		//FOREACH.担保人.
//		else if(paragraphText.startsWith("FOREACH.")){
//
//			List<XWPFRun> addRuns = new ArrayList<XWPFRun>();
//			int size = runs.size();
////			String startWord =
//			for (int i = 0; i < size; i++) {
//				paragraph.removeRun(0);
//			}
//
////					XWPFRun run =
////					paragraph.createRun().setFontFamily("") .setText(paragraphText + "\n测试");
//		}
//
//	}

	private Map<String, String> getLoanInfoMap(Integer loanId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String presType = "";
		//查询所有表单
		List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getAllTemplateList();
		if(CollectionUtils.isEmpty(templateList)){
			return map;
		}
//		private String name;		//名称
//		private String tableName;	//表名
//		private String lowerTableName;	//实体名
//		private String type;		//模板类型
//		private String module;		//模块
//		private List<AutoBaseField> fields;
//		private Object data;
//		private String jsonFields;//非持久化字段 fields的json形式
//		private String tableFormula ;//非持久化字段 表单合计
//		private Map<String, Object> attributeMap;
//		private String showOrHide;   //表单显示和隐藏
		DecimalFormat df = new DecimalFormat("#.0000");
		DecimalFormat df1 = new DecimalFormat("###,##0.00");
		AutoBaseTemplate pledgeTemplate = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{23}).get(0);
		pledgeTemplate.setName("抵押物");
		templateList.add(pledgeTemplate);
		AutoBaseTemplate pledgeTemplate1 = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{23}).get(0);
		pledgeTemplate1.setName("质押物");
		templateList.add(pledgeTemplate1);

		for (AutoBaseTemplate template : templateList){
			if(template.getName().equals("抵质押物")){
				continue;
			}
			if(TableInputType.LIST.type.equals(template.getType())){
				presType = LoanProcessTypeEnum.INVESTIGATE.type;
			}else {
				presType = "";
			}
			DataTable datatable = null;

			if(template.getTableName().equals("LBIZ_APPROVAL_RESOLUTION")){
				datatable = applyInfoService.getApprovalDataTableByLoanId( loanId);
			}else if(template.getName().equals("抵押物")){
				datatable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 1);
			}else if(template.getName().equals("质押物")){
				datatable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 2);
			}else if("LBIZ_TRUSTED_PAYMENY".equals(template.getTableName())){
				datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), LoanProcessTypeEnum.LOAN.type, loanId);
			}else{
				datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), presType, loanId);
			}
			DataRow[] rows = null;
			if(datatable!=null && datatable.rowSize()>0){
				rows = datatable.getRows();
			}

			if(null==rows){
				rows = new DataRow[1];
//				rows[0] = new DataRow(0);
			}
			StringBuffer sb = new StringBuffer("");
			StringBuffer sb1 = new StringBuffer("");
//			StringBuffer sb3 = new StringBuffer("");
			for (int r = 0; r < rows.length; r++) {
				StringBuffer sb2 = new StringBuffer("");
				template.setData(rows[r]);
				List<AutoBaseField> fieldList = template.getFields();
				if("LBIZ_TRUSTED_PAYMENY".equals(template.getTableName())){
					setPaymentIdAndStatus(fieldList);
				}
				for (int i = 0; i < fieldList.size(); i++) {
					AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
//					Object value = field.getValue(template.getData());
					Object value = "";
					String display = "";
					if(null!=template.getData()){
						value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
					}
					//				Text Address Number Ratio Decimal TextArea Quest Select MultipleSelect DropPopup YesNo
					//				YesNo Date Select DropPopup MultipleSelect Ratio
					if(null!=value){
						if(field.getType().equalsIgnoreCase("YesNo")){
							if(value.equals("1")){
								display = "是";
							}else {
								display = "否";
							}
						}else if(field.getType().equalsIgnoreCase("Date")){
							display = FormatUtil.formatDate(value, "yyyy年MM月dd日");
						}else if(field.getType().equalsIgnoreCase("Select")){
							display = field.getOptionTextByValue(value.toString());
						}else if(field.getType().equalsIgnoreCase("MultipleSelect")){
							display = field.getOptionTextByValues(value.toString());
						}else if(value instanceof BigDecimal){//去掉末尾多余的0
							display = new BigDecimal(value.toString()).stripTrailingZeros().toPlainString();
						}else{
							display = value.toString();
						}
					}

					// 多个逗号分割 数字相加
					String key = template.getName() + "." + field.getFieldName();

					if(field.getType().equalsIgnoreCase("Decimal")&& StringUtils.isNotBlank(display)){
						map.put(key+"大写"+(r+1), MoneyUtil.toChinese(display)+"整");//select显示名称/list逗号分割/Decimal之和
						map.put(key+"小写"+(r+1),df1.format(new BigDecimal(display)));
					}else if(field.getType().equalsIgnoreCase("Ratio")&&StringUtils.isNotBlank(display)){
						map.put(key+"%"+(r+1),df.format(new BigDecimal(display))+"%");
					}else if(field.getType().equalsIgnoreCase("Number")&&StringUtils.isNotBlank(display)){
						map.put(key + "大写"+(r+1),MoneyUtil.toChinese(display).replace("元",""));
					}
					map.put(key+(r+1), display);


					if(map.containsKey(key)&& StringUtils.isNotBlank(display)&& StringUtils.isNotBlank(map.get(key))&&!template.getTableName().equals("LBIZ_APPROVAL_RESOLUTION")&&TableInputType.LIST.type.equals(template.getType())){
						//如果是数字则相加
						if(field.getType().equalsIgnoreCase("Decimal")){
							display = new BigDecimal(map.get(key)).add(new BigDecimal(display)).toString();
						}else{
							display = map.get(key)+","+display;
						}
					}

					map.put(key, display);//select显示名称/list逗号分割/Decimal之和
					if(null!=value&&!display.equals(value.toString())){
						map.put(key+"key", null!=value?value.toString():"");//原值
					}
					if(field.getType().equalsIgnoreCase("Decimal")&& StringUtils.isNotBlank(display)){
						map.put(key+"大写", MoneyUtil.toChinese(display)+"整");//select显示名称/list逗号分割/Decimal之和
						map.put(key+"小写",df1.format(new BigDecimal(display)));
					}
//					map.put(key+(r+1), display);

				}
				int serno = r + 1;
				if("LBIZ_LOAN_GUARANTEE".equals(template.getTableName())){

					sb.append("（").append(MoneyUtil.toLowerChinese(serno + 1+"").replace("元", "")).append("）").append("保证人：").append(MapUtils.getString(map,"担保人.姓名"+serno,"")).append("\r").append("\t")
							.append("法定代表人：").append(MapUtils.getString(map,"担保人.法定代表人"+serno,"")).append("\r").append("\t")
							.append("住所地/住址：").append(MapUtils.getString(map,"担保人.居住地址"+serno,"")).append("\r").append("\t")
							.append("组织机构代码/统一社会信用代码/身份证件号码： ").append(MapUtils.getString(map,"担保人.证件号码"+serno,"")).append("\r").append("\t");

					sb1.append("保证人：").append(MapUtils.getString(map,"担保人.姓名"+serno,"")).append("\r")
							.append("送达地址：").append(MapUtils.getString(map, "担保人.居住地址" + serno, "")).append("\r")
							.append("法定代表人：").append(MapUtils.getString(map, "担保人.法定代表人" + serno, "")).append("\r")
							.append("电话：").append(MapUtils.getString(map, "担保人.联系电话" + serno, "")).append("     ").append("传真：").append("\r")
							.append("广东法院诉讼文书接收专用免费电子邮箱：\r").append(MapUtils.getString(map, "担保人.证件号码" + serno, "")).append("@sd.gdcourts.gov.cn").append("\r");
				}
				if("抵押物".equals(template.getName())){
					sb.append("（").append(MoneyUtil.toLowerChinese(serno + 1 + "").replace("元", "")).append("）").append("抵押人：").append(MapUtils.getString(map, "抵押物.抵押人名称" + serno, "")).append("\r").append("\t")
							.append("法定代表人：").append(MapUtils.getString(map, "抵押物.法定代表人" + serno, "")).append("\r").append("\t")
							.append("住所地/住址：").append(MapUtils.getString(map, "抵押物.居住地址" + serno, "")).append("\r").append("\t")
							.append("组织机构代码/统一社会信用代码/身份证件号码： ").append(MapUtils.getString(map, "抵押物.身份证号码" + serno, "")).append("\r").append("\t");

					sb1.append("抵押人、抵押人配偶（如有）：").append(MapUtils.getString(map, "抵押物.抵押人名称" + serno, "")).append("\r")
							.append("送达地址：").append(MapUtils.getString(map, "抵押物.居住地址" + serno, "")).append("\r")
							.append("法定代表人：").append(MapUtils.getString(map, "抵押物.法定代表人" + serno, "")).append("\r")
							.append("电话：").append(MapUtils.getString(map, "抵押物.电话" + serno, "")).append("     ").append("传真：").append("\r")
							.append("广东法院诉讼文书接收专用免费电子邮箱：\r").append(MapUtils.getString(map, "抵押物.身份证号码" + serno, "")).append("@sd.gdcourts.gov.cn").append("\r");
					sb2.append(MapUtils.getString(map, "抵押物.权属证件编号" + serno, ""));
					String isCommonAssetStr = MapUtils.getString(map, "抵押物.是否共同产权" + serno,"");
					if(StringUtil.isNotEmpty(isCommonAssetStr)&&"是".equals(isCommonAssetStr)){
						int rowId = (Integer)rows[r].get("ID");
						List<CommPeoInfo> commPeoInfoList = loanApplyService.queryCommPeoInfoListByItemId(rowId);
						for(CommPeoInfo commPeoInfo : commPeoInfoList){
							sb1.append("抵押人、抵押人配偶（如有）：").append(commPeoInfo.getCommPeoName()).append("\r")
									.append("送达地址：").append(commPeoInfo.getAddress()).append("\r")
									.append("法定代表人：").append(commPeoInfo.getCorporation()).append("\r")
									.append("电话：").append(commPeoInfo.getTelephone()).append("     ").append("传真：").append(commPeoInfo.getFacsimile()).append("\r")
									.append("广东法院诉讼文书接收专用免费电子邮箱：\r").append(commPeoInfo.getCommLicenseNo()).append("@sd.gdcourts.gov.cn").append("\r");
							sb2.append("、").append(commPeoInfo.getCommNo());
						}
					}
				}
				if("质押物".equals(template.getName())){
					sb.append("（").append(MoneyUtil.toLowerChinese(serno + 1 + "").replace("元", "")).append("）").append("质押人：").append(MapUtils.getString(map, "质押物.抵押人名称" + serno, "")).append("\r").append("\t")
							.append("法定代表人：").append(MapUtils.getString(map, "质押物.法定代表人" + serno, "")).append("\r").append("\t")
							.append("住所地/住址：").append(MapUtils.getString(map, "质押物.居住地址"+serno,"")).append("\r").append("\t")
							.append("组织机构代码/统一社会信用代码/身份证件号码：").append(MapUtils.getString(map, "质押物.身份证号码"+serno,"")).append("\r").append("\t");

					sb1.append("抵押人、抵押人配偶（如有）：").append(MapUtils.getString(map, "质押物.抵押人名称" + serno, "")).append("\r")
							.append("送达地址：").append(MapUtils.getString(map, "质押物.居住地址" + serno, "")).append("\r")
							.append("法定代表人：").append(MapUtils.getString(map, "质押物.法定代表人" + serno, "")).append("\r")
							.append("电话：").append(MapUtils.getString(map, "质押物.电话" + serno, "")).append("     ").append("传真：").append("\r")
							.append("广东法院诉讼文书接收专用免费电子邮箱：\r").append(MapUtils.getString(map, "质押物.身份证号码" + serno, "")).append("@sd.gdcourts.gov.cn").append("\r");

					sb2.append(MapUtils.getString(map, "质押物.权属证件编号" + serno, ""));

					String isCommonAssetStr = MapUtils.getString(map, "质押物.是否共同产权" + serno,"");
					if(StringUtil.isNotEmpty(isCommonAssetStr)&&"是".equals(isCommonAssetStr)){
						int rowId = (Integer)rows[r].get("ID");
						List<CommPeoInfo> commPeoInfoList = loanApplyService.queryCommPeoInfoListByItemId(rowId);
						for(CommPeoInfo commPeoInfo : commPeoInfoList){
							sb1.append("质押人、质押人配偶（如有）：").append(commPeoInfo.getCommPeoName()).append("\r")
									.append("送达地址：").append(commPeoInfo.getAddress()).append("\r")
									.append("法定代表人：").append(commPeoInfo.getCorporation()).append("\r")
									.append("电话：").append(commPeoInfo.getTelephone()).append("     ").append("传真：").append(commPeoInfo.getFacsimile()).append("\r")
									.append("广东法院诉讼文书接收专用免费电子邮箱：\r").append(commPeoInfo.getCommLicenseNo()).append("@sd.gdcourts.gov.cn").append("\r");
							sb2.append("、").append(commPeoInfo.getCommNo());
						}
					}
				}
				if("LBIZ_TRUSTED_PAYMENY".equals(template.getTableName())){
					String paymentStatus = MapUtils.getString(map,"受托支付.支付状态"+serno,"");
					if("0".equals(paymentStatus)){
						map.put("受托支付.支付状态"+serno,"录入状态");
					}else if("1".equals(paymentStatus)){
						map.put("受托支付.支付状态"+serno,"已确认");
					}else if("2".equals(paymentStatus)){
						map.put("受托支付.支付状态"+serno,"支付成功");
					}else if("3".equals(paymentStatus)){
						map.put("受托支付.支付状态"+serno,"已作废");
					}else if("4".equals(paymentStatus)){
						map.put("受托支付.支付状态"+serno,"支付退汇");
					}else{
						map.put("受托支付.支付状态"+serno,"");
					}
				}
				if("抵押物".equals(template.getName())){
					map.put("抵押人权属证列表"+serno,sb2.toString());
				}else if("质押物".equals(template.getName())){
					map.put("质押人权属证列表"+serno,sb2.toString());
				}


			}
			if("LBIZ_LOAN_GUARANTEE".equals(template.getTableName())){
				map.put("保证人列表",sb.toString());
				map.put("保证人地址列表",sb1.toString());
			}else if("抵押物".equals(template.getName())){
				map.put("抵押物列表",sb.toString());
				map.put("抵押人地址列表",sb1.toString());
//				map.put("抵押人权属证列表",sb2.toString());
			}else if("质押物".equals(template.getName())){
				map.put("质押物列表",sb.toString());
				map.put("质押人地址列表",sb1.toString());
//				map.put("质押人权属证列表",sb2.toString());
			}
		}

		//贷款主表
		LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(loanId);
		if (null != loanApplyInfo) {

			map.put("贷款合同类型", String.valueOf(loanApplyInfo.getLoanContractTypeId()));
			map.put("合同号", loanApplyInfo.getLoanContractNumber());
			map.put("合同编码", loanApplyInfo.getContractCode());
			map.put("保证人合同号", loanApplyInfo.getGuaranteeContractNo());
			map.put("保证人合同编码", loanApplyInfo.getGuaranteeContractCode());
			map.put("抵押合同号", loanApplyInfo.getMortgageContractNo());
			map.put("抵押合同编码", loanApplyInfo.getMortgageContractCode());
			map.put("质押合同号", loanApplyInfo.getPledgeContractNo());
			map.put("质押合同编码", loanApplyInfo.getPledgeContractCode());
			map.put("借据号", loanApplyInfo.getIouNum());
			map.put("放款授权码", loanApplyInfo.getAuthorizationCode());
			map.put("贷款账号", loanApplyInfo.getLoanAccountNo());

//			private String accountNum;             //还款账号
//			private String enterAccount;           //入账账号
			map.put("还款帐号", loanApplyInfo.getAccountNum());
			map.put("入账账号", loanApplyInfo.getEnterAccount());

//
			map.put("贷款申请时间", getStringDate(loanApplyInfo.getLoanApplyDate(), "yyyy年MM月dd日"));
			map.put("贷款分配时间", getStringDate(loanApplyInfo.getLoanAssignmentDate(), "yyyy年MM月dd日"));
			map.put("贷款调查时间", getStringDate(loanApplyInfo.getLoanInvestigationDate(), "yyyy年MM月dd日"));
			map.put("贷款审批时间", getStringDate(loanApplyInfo.getLoanAuditDate(), "yyyy年MM月dd日"));
			map.put("贷款放款时间", getStringDate(loanApplyInfo.getLoanCreditDate(), "yyyy年MM月dd日"));
			map.put("当前时间（年月日）", getStringDate(new Date(), "yyyy年MM月dd日"));
			map.put("当前时间（-）", getStringDate(new Date(), "yyyy-MM-dd"));

			map.put("币种", "人民币");
			//合同种类
			StringBuffer value = new StringBuffer("");
			if(StringUtil.isNotEmpty(loanApplyInfo.getContractCode())){
				value.append(loanApplyInfo.getContractCode());
			}
			if(StringUtil.isNotEmpty(loanApplyInfo.getGuaranteeContractCode())){
				value.append("、").append(loanApplyInfo.getGuaranteeContractCode());
			}
			if(StringUtil.isNotEmpty(loanApplyInfo.getMortgageContractCode())){
				value.append("、").append(loanApplyInfo.getMortgageContractCode());
			}
			if(StringUtil.isNotEmpty(loanApplyInfo.getPledgeContractCode())){
				value.append("、").append(loanApplyInfo.getPledgeContractCode());
			}
			map.put("合同种类",value.toString());
			map.put("放款审核表.是否最高额贷款","3".equals(loanApplyInfo.getLoanContractTypeId()+"")?"否":"是");

		}



		//抵质押物.租赁情况 1抵押物未出租给第三人  2抵押物已出租给第三人
		String isLease = MapUtils.getString(map,"抵押物.是否租赁key");//是否租赁 1	是 2	否
		map.put("抵质押物.租赁情况","1");
		if(StringUtils.isNotBlank(isLease)&&isLease.contains("1")) {
			map.put("抵质押物.租赁情况","2");
		}


		//还款方式1	等额本金 2	等额本息 3	只还利息,到期还本 4	分期归还法
		//		（1）借款人于借款到期日一次性偿还全部借款本金；
		//		（2）采用按月      （A、等额本金；B等额本息）还款方式；
		//		（3）分期还款计划。借款人按约定分期还款计划偿还借款本金：
		String repaymentMode = MapUtils.getString(map,"审批决议.还款方式key");
		if(StringUtils.isNotBlank(repaymentMode)){
			if(repaymentMode.equals("1")){
				map.put("贷款合同.还本方式","(2)");
				map.put("贷款合同.按月还本方式","A.等额本金");
			}else if(repaymentMode.equals("2")){
				map.put("贷款合同.还本方式","(2)");
				map.put("贷款合同.按月还本方式","B.等额本息");
			}else if(repaymentMode.equals("3")){
				map.put("贷款合同.还本方式","(1)");
			}else if(repaymentMode.equals("4")){
				map.put("贷款合同.还本方式","(3)");
			}else if(repaymentMode.equals("5")){
				map.put("贷款合同.还本方式","(3)");
			}
		}

		//01	信用 02	担保 03	抵押 04质押   1、担保合同名称：《抵押担保合同》；   2、担保合同名称：《质押担保合同》；  3、担保合同名称：《保证担保合同》；
		String guaranteeContract = "";
		String guaranteeMode = MapUtils.getString(map,"调查结论.担保方式key");
		if(StringUtils.isNotBlank(guaranteeMode)){
			if(guaranteeMode.equals("02")) guaranteeContract+="3";
			else if(guaranteeMode.equals("03")) guaranteeContract+="1";
			else if(guaranteeMode.equals("04")) guaranteeContract+="2";
		}
		String guaranteeMode2 = MapUtils.getString(map,"调查结论.担保方式2key");
		if(StringUtils.isNotBlank(guaranteeMode2)){
			if(guaranteeMode2.equals("02")) guaranteeContract+="+3";
			else if(guaranteeMode2.equals("03")) guaranteeContract+="+1";
			else if(guaranteeMode2.equals("04")) guaranteeContract+="+2";
		}
		String guaranteeMode3 = MapUtils.getString(map,"调查结论.担保方式3key");
		if(StringUtils.isNotBlank(guaranteeMode3)){
			if(guaranteeMode3.equals("02")) guaranteeContract+="+3";
			else if(guaranteeMode3.equals("03")) guaranteeContract+="+1";
			else if(guaranteeMode3.equals("04")) guaranteeContract+="+2";
		}

		// 3 一般贷款		4	最高额贷款 调查结论.担保方式
		String contractType = MapUtils.getString(map,"贷款合同类型");
		if(StringUtils.isNotBlank(contractType)){
			if(contractType.equals("3")){
				map.put("贷款合同.是否授信","A.非授信额度内的单笔业务合同");
				map.put("贷款合同.非授信担保合同",guaranteeContract);
				map.put("账号或卡号",MapUtils.getString(map,"贷款合同.还款账号"));

			}else if(contractType.equals("4")){
				map.put("贷款合同.是否授信","B.授信额度内的具体业务合同");
				map.put("贷款合同.授信担保合同",guaranteeContract);
				map.put("贷款合同.额度合同编号",MapUtils.getString(map,"合同编码"));
				map.put("贷款合同.最高额抵押担保合同号",MapUtils.getString(map,"抵押合同编码"));
				map.put("贷款合同.最高额质押担保合同号",MapUtils.getString(map,"质押合同编码"));
				map.put("贷款合同.最高额保证担保合同号",MapUtils.getString(map,"保证人合同编码"));
				map.put("账号或卡号",StringUtil.isEmpty(MapUtils.getString(map,"贷款合同.还款卡号"))?MapUtils.getString(map,"贷款合同.还款账号"):MapUtils.getString(map,"贷款合同.还款卡号"));
			}
		}

		//放款起始日期 放款终止日期
		String loanBeginDateStr = MapUtils.getString(map,"放贷信息.放款起始日期");
		String loanEndDateStr = MapUtils.getString(map,"放贷信息.放款终止日期");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		if(StringUtils.isNotBlank(loanBeginDateStr)){
			map.put("放贷信息.放款起始日期",DateUtil.getYear(sdf.parse(loanBeginDateStr))+"   "+DateUtil.getMonth(sdf.parse(loanBeginDateStr))+"   "+DateUtil.getDayOfMonth(sdf.parse(loanBeginDateStr))+"    ");
		}
		if(StringUtils.isNotBlank(loanEndDateStr)){
			map.put("放贷信息.放款终止日期",DateUtil.getYear(sdf.parse(loanEndDateStr))+"   "+ DateUtil.getMonth(sdf.parse(loanEndDateStr))+"   "+DateUtil.getDayOfMonth(sdf.parse(loanEndDateStr)) + "    ");
		}
		String approvalRatio = MapUtils.getString(map,"审批决议.决议利率");
		String loanRatio = MapUtils.getString(map, "放贷信息.放款利率");
		if(StringUtils.isNotBlank(approvalRatio)){
			map.put("审批决议.决议利率%",df.format(new BigDecimal(approvalRatio))+"%");
			map.put("借据利率",df.format(new BigDecimal(approvalRatio)));
			//审查表.贷款利率
			map.put("贷款合同审查表.贷款利率","执行利率不低于"+df.format(new BigDecimal(approvalRatio))+"%");
		}
		if(StringUtils.isNotBlank(loanRatio)){
			map.put("放贷信息.放款利率",df.format(new BigDecimal(loanRatio)));
			map.put("放贷信息.放款利率%",df.format(new BigDecimal(loanRatio))+"%");
			map.put("借据利率",df.format(new BigDecimal(loanRatio)));
		}
        map.put("审批决议.决议金额（元）",MapUtils.getString(map,"审批决议.决议金额",""));
		// 组织机构  字年份  合同顺序号
		map.put("年号",DateUtil.getYear(new Date())+"");
		//合同号
		if(StringUtil.isNotEmpty(MapUtils.getString(map,"合同号",""))){
			map.put("主合同.顺序号",MapUtils.getString(map,"合同号").substring(9,16));
		}
		//保证人合同号
		if(StringUtil.isNotEmpty(MapUtils.getString(map,"保证人合同号",""))){
			map.put("保证合同.顺序号",MapUtils.getString(map,"保证人合同号").substring(9,16));
		}
		//抵押合同号
		if(StringUtil.isNotEmpty(MapUtils.getString(map,"抵押合同号",""))){
			map.put("抵押合同.顺序号",MapUtils.getString(map,"抵押合同号").substring(9,16));
		}
		//质押合同号
		if(StringUtil.isNotEmpty(MapUtils.getString(map,"质押合同号",""))){
			map.put("质押合同.顺序号",MapUtils.getString(map,"质押合同号").substring(9,16));
		}
		//担保品入库通知书
		map.put("入库通知书.机构名称","中山农村商业银行股份有限公司西区支行营业部");
		map.put("入库通知书.日期","日期: "+DateUtil.format(new Date(),"yyyy年MM月dd日"));
		Integer loanLimit = MapUtils.getInteger(map, "审批决议.决议期限", 0);
		if(loanLimit<=12){
			map.put("借据.借款种类", "短期" + MapUtils.getString(map, "调查结论.业务品种分类名称", "").replace("&gt;", ">").substring(MapUtils.getString(map, "调查结论.业务品种分类名称", "").replace("&gt;", ">").lastIndexOf(">")+1));
		}else if(loanLimit<=60){
			map.put("借据.借款种类","中期"+MapUtils.getString(map,"调查结论.业务品种分类名称", "").replace("&gt;", ">").substring(MapUtils.getString(map,"调查结论.业务品种分类名称","").replace("&gt;", ">").lastIndexOf(">")+1));
		}else{
			map.put("借据.借款种类","长期"+MapUtils.getString(map,"调查结论.业务品种分类名称","").replace("&gt;",">").substring(MapUtils.getString(map,"调查结论.业务品种分类名称","").replace("&gt;", ">").lastIndexOf(">")+1));
		}
		map.put("出账通知书.贷款金额大写","(大写):"+MapUtils.getString(map,"审批决议.决议金额大写",""));
		map.put("出账通知书.贷款金额小写","(小写):"+ df1.format(new BigDecimal(MapUtils.getIntValue(map, "审批决议.决议金额",0))));
		String loanContractEndStr = MapUtils.getString(map, "贷款合同.借款终止日期");
		if(StringUtil.isNotEmpty(loanContractEndStr)){
			map.put("出账通知书.贷款到期日", DateUtil.format(sdf.parse(loanContractEndStr),"yyyy-MM-dd"));
		}
		map.put("合同期限",MapUtils.getString(map,"贷款合同.借款起始日期")+"至"+MapUtils.getString(map,"贷款合同.借款终止日期"));
        map.put("贷款资料清单.借款人and金额"," 借款人名称："+MapUtils.getString(map,"个人信息.客户姓名")+"            贷款余额："+df1.format(new BigDecimal(MapUtils.getIntValue(map, "审批决议.决议金额", 0))) + "元");
		map.put("代征印花税通知书.借款合同金额",df1.format(new BigDecimal(MapUtils.getIntValue(map, "审批决议.决议金额",0))));
		map.put("代征印花税通知书.应交印花税",df1.format(new BigDecimal(MapUtils.getIntValue(map, "审批决议.决议金额",0)*0.00005)));
		map.put("受托支付交易列表.贷款金额",MapUtils.getString(map,"放贷信息.放款金额大写","")+"    "+"¥"+MapUtils.getString(map,"放贷信息.放款金额小写",""));

//		map.put("合同合同","贷款利率\r贷款利率\r贷款利率");

//		map.put("主合同.字号","3".equals(MapUtils.getString(map,"","")));
		//查询所有field map
//		List<AutoTableColumn>  tableColumnList = configModule.getAutoFieldProvider().getAllActivedTableColumnList();
//		if(CollectionUtils.isEmpty(tableColumnList)){
//			return map;
//		}else {
//			for(AutoTableColumn tableColumn : tableColumnList){
//				map.put(tableColumn.getTableName()+"."+tableColumn.getFieldColumn(),tableColumn.getFieldName());
//			}
//		}

//		//查询所有表单数据
//		for (AutoTableInfo tableInfo : tableInfoList) {
//			DataTable datatable = applyInfoService.getDataTableById(tableInfo.getTableDbName(), loanId);
//			if(datatable!=null && datatable.rowSize()>0){
//				DataTable dataColumn = datatable.getRow(0).getTable();
//				if(null!=dataColumn&&dataColumn.colSize()>0){
//					for (int i = 0; i < dataColumn.colSize(); i++) {
//						String key = tableInfo.getTableModuleName() + "." + MapUtils.getString( map,tableInfo.getTableDbName()+"."+dataColumn.getColumn(i).getName() );
//						String value = null!=dataColumn.getColumn(i).getValues()[0]?dataColumn.getColumn(i).getValues()[0].toString():null;
//						map.put(key, value);
//					}
//				}
//			}
//		}


		return map;
	}
	private List<AutoBaseField> setPaymentIdAndStatus(List<AutoBaseField> autoBaseFields){
		AutoBaseField autoBaseField = new AutoFieldWrapper(3101,"paymentId","PAYMENT_ID","支付ID号","Text","",true,150,true,true,"paymentId",null,null);
		AutoBaseField autoBaseField1 = new AutoFieldWrapper(3102,"paymentStatus","PAYMENT_STATUS","支付状态","Text","",true,150,true,true,"paymentStatus",null,null);
		autoBaseFields.add(autoBaseField);
		autoBaseFields.add(autoBaseField1);
		return autoBaseFields;
	}
	private String getStringDate(Date date, String pattern) {
		try{
			if(null!=date){
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				return sdf.format(date);
			}else{
				return "";
			}
		}catch (Exception e){
			return "";
		}
	}


	private void zipFile(List<File> subs, String baseName,String dir, ZipOutputStream zos) throws IOException {
		for (int i=0;i<subs.size();i++) {
			File f=subs.get(i);
			zos.putNextEntry(new ZipEntry(baseName + dir+'/'+ MapUtils.getString(fileMap,f,f.getName())));
//			zos.putNextEntry(new ZipEntry(baseName + f.getName()));
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int r = 0;
			while ((r = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, r);
			}
			fis.close();
		}
	}
	private String getZipFilename(String customerName){
		return customerName+"_贷款文件_"+DateUtil.format(new Date(), "yyyyMMddHHmmss")+".zip";
	}

	private  String poiExcelToHtml(String excelFile){
		String dirPath = "tmp_file" + File.separator + "html" + File.separator;
		String fullPath = FileUtil.contact(this.getRootPath(), dirPath );
		String imagePath = fullPath + "image" + File.separator;
		String htmlName = UUID.randomUUID() + ".vm";
		File htmlFile = new File(fullPath + htmlName);
		PoiExcelToHtmlUtil.excelToHtml(excelFile, htmlFile.getPath());
		return dirPath+htmlName;
	}

	private void writeExcelFileContent(String filePath, String saveFilename, Map<String, String> map) throws Exception{
		//do Something
		File sourcefile = new File(saveFilename);
		InputStream is = new FileInputStream(sourcefile);
		Workbook wb = WorkbookFactory.create(is);//此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
		if (wb instanceof XSSFWorkbook) {
			XSSFWorkbook xWb = (XSSFWorkbook) wb;
			writeExcelInfo(xWb,map);
		}else if(wb instanceof HSSFWorkbook){
			HSSFWorkbook hWb = (HSSFWorkbook) wb;
			writeExcelInfo(hWb,map);
		}
		FileOutputStream os = new FileOutputStream(new File(filePath));
		if(null!=wb){
			wb.write(os);
		}
		os.close();

	}

	private void writeExcelInfo(Workbook wb,Map<String, String> map){
		//map中借据对金额和金额大写的处理
//		String result
//		if(){
//
//		}
		for(int i=0;i<wb.getNumberOfSheets();i++) {
			Sheet sheet = wb.getSheetAt(i);//获取第i个Sheet的内容
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;        //兼容
			Cell cell = null;    //兼容
			for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
				row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				int lastColNum = row.getLastCellNum();
				for (int colNum = 0; colNum < lastColNum; colNum++) {
					cell = row.getCell(colNum);
					if (cell == null) {
						continue;
					}
					String text = PoiExcelToHtmlUtil.getCellValue(cell);
//					String flag = findReplaceTextFlag(text);
					if(StringUtils.isNotBlank(text)) {
						if(StringUtils.isNotBlank(findReplaceTextFlag(text))){
							cell.setCellValue("");
						}
//						String info = text.substring(text.indexOf("[")+1,text.indexOf("]"));
						for (Map.Entry<String, String> entry : map.entrySet()) {
							String key = entry.getKey();
							if (text.equals("[" + key + "]")) {
//								text = text.replace("["+key+"]",entry.getValue());
								cell.setCellValue(entry.getValue());
								break;
							}
						}
					}
				}
			}
		}
	}
	//处理map
	private void addMapInfo(Integer loanId,Map map) throws Exception {

		//do Something
		LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(loanId);
		if(applyInfo!=null){
			//客户经理
			String belongUserName = permissionModule.getPmsUserByUserId(Integer.valueOf(applyInfo.getLoanBelongId())).getUserName();
			map.put("审批表.客户经理",belongUserName);
			String resultLoanMoney = NumberToCN.number2CNMontrayUnit(applyInfo.getLoanResultAmount());
			if(applyInfo.getLoanCreditAmount()!=null&&applyInfo.getLoanCreditAmount().intValue()>0){
				resultLoanMoney = NumberToCN.number2CNMontrayUnit(applyInfo.getLoanCreditAmount());
			}
			map.put("决议金额.大写",resultLoanMoney);
			//处理金额——取得金额每位上的数【十亿千百十万千百十元角分】
			String[] keys = {"决议金额.十亿","决议金额.亿","决议金额.千万","决议金额.百万","决议金额.十万","决议金额.万","决议金额.千","决议金额.百","决议金额.十","决议金额.元","决议金额.角","决议金额.分"};
			String[] strs = new String[12];
			BigDecimal loanMoney = (applyInfo.getLoanCreditAmount()!=null&&applyInfo.getLoanCreditAmount().intValue()>0)?applyInfo.getLoanCreditAmount():applyInfo.getLoanResultAmount();
			loanMoney.setScale(2,BigDecimal.ROUND_HALF_UP);
			String[] ms = loanMoney.toString().split("\\.");
			for(int i=ms[0].length() - 1,j=9;i>=0;i--,j--){
				strs[j]=String.valueOf(ms[0].charAt(i));
			}
			if(ms[0].length()<=10){
				strs[9-ms[0].length()]="¥";
			}
			//小数
			for(int i=0,j=10;i<ms[1].length();i++,j++){
				strs[j]=String.valueOf(ms[1].charAt(i));
			}
			for(int i=0;i<keys.length;i++){
				map.put(keys[i],strs[i]);
			}
		}
	}


	/**
	 * 保存合同信息
	 * @param json 自定义表单信息
	 * @return
	 */
	@RequestMapping(value = "/loanContractSave", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void loanContractSave(@RequestParam(value = "json", defaultValue = "") String json,
							  @RequestParam(value = "id", defaultValue = "") String id,
							  @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
							  @RequestParam(value = "cusId", defaultValue = "") String cusId,
							  @RequestParam(value = "loanContractBegin", defaultValue = "") String loanContractBegin,
							  @RequestParam(value = "loanContractEnd", defaultValue = "") String loanContractEnd,
							  @RequestParam(value = "accountNum", defaultValue = "") String accountNum,
							  @RequestParam(value = "accountBank", defaultValue = "") String accountBank,
							  @RequestParam(value = "enterAccount", defaultValue = "") String enterAccount){
		try {
			if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
				boolean isBlack = customerBlackProvider.isExistCustomerBlack(Integer.parseInt(id));
				if (isBlack) {
					renderText(false,"客户贷款受限",id);
				} else {


					LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(Integer.parseInt(id));
					//如果合同撤销的合同号重新生成,并把sysStatus置为0
					if(null!=applyInfo.getSyncStatus()&&applyInfo.getSyncStatus()==-1){
						applyInfo.setLoanContractNumber("");
						applyInfo.setLoanContractNumber("");
						applyInfo.setAuthorizationCode("");
						applyInfo.setGuaranteeContractNo("");
						applyInfo.setMortgageContractNo("");
						applyInfo.setPledgeContractNo("");
						applyInfo.setSyncStatus(0);
					}

					//主合同
					if(StringUtil.isNullOrEmpty(applyInfo.getLoanContractNumber())) {
						applyInfo.setLoanContractNumber(CodedProduceUtil.getCode(OperationCode.CODE_100.getCode(), true));
//						applyInfo.setContractCode(generateContractCode(Integer.valueOf(loanTypeId),1,applyInfo.getLoanContractNumber().substring(9, 16)));
					}
					//借据号
//					if(StringUtil.isNullOrEmpty(applyInfo.getIouNum())){
//						applyInfo.setIouNum(CodedProduceUtil.getCode(OperationCode.CODE_200.getCode(), true));
//					}
					//授权流水号
//					if(StringUtil.isNullOrEmpty(applyInfo.getAuthorizationCode())){
//						applyInfo.setAuthorizationCode(CodedProduceUtil.getAuthorizedSerialCode(true));
//					}
					Map<String, Object> map = JsonTools.parseJSON2Map(json);
						if (map != null && map.size() > 0) {
							List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByLoanType(String.valueOf(loanTypeId), LoanProcessTypeEnum.CONTRACT.type,null);
							boolean is_Have_Loan_Contract = false;
							if(CollectionUtils.isNotEmpty(templateList)){
								Map<String, Object> customerMap = new HashMap<String, Object>();
								for (AutoBaseTemplate autoTemplate : templateList) {
									String tableName = autoTemplate.getTableName();
									DataTable dataTable = applyInfoService.getDataTableByLoanId(tableName,LoanProcessTypeEnum.CONTRACT.type,Integer.valueOf(id));
									List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(tableName);
									if (CollectionUtils.isNotEmpty(list)) {
										for (Map<String, Object> dataMap : list) {
											setCustomerMap(customerMap, dataMap);
											dataTable.setName(tableName);
											dataTable.addColumn("ID");
											String dataId = (String) customerMap.get("ID");
											dataTable.newRow();
											DataRow dataRow = dataTable.getRow(0);
											dataRow.set("LOAN_ID", id);
											dataRow.set("LOAN_PROCESS_TYPE", LoanProcessTypeEnum.CONTRACT.type);
											dataRow.set("CREATE_DATE", new Date());
											dataRow.set("CREATE_USER", this.getLoginInfo().getUserId());
											dataRow.set("UPDATE_DATE", new Date());
											dataRow.set("UPDATE_USER", this.getLoginInfo().getUserId());

											List<AutoBaseField> fieldList = autoTemplate.getFields();
											setDataRowByFieldList(dataRow, fieldList, customerMap);
											if("LOAN_CONTRACT".equals(tableName)){
												is_Have_Loan_Contract = true;
												String isGuaranteeContract = (String)dataRow.get("IS_GUARANTEE_CONTRACT");
												String isCollateralContract = (String)dataRow.get("IS_COLLATERAL_CONTRACT");
												String isPledgeContract = (String)dataRow.get("IS_PLEDGE_CONTRACT");
												if(StringUtil.isNullOrEmpty(isGuaranteeContract)||"02".equals(isGuaranteeContract)){
													applyInfo.setGuaranteeContractNo("");
												}else{
													applyInfo.setGuaranteeContractNo(StringUtil.isNullOrEmpty(applyInfo.getGuaranteeContractNo())?CodedProduceUtil.getCode(OperationCode.CODE_101.getCode(),true):applyInfo.getGuaranteeContractNo());
//													applyInfo.setGuaranteeContractCode(generateContractCode(Integer.parseInt(loanTypeId),2,applyInfo.getGuaranteeContractNo().substring(9,16)));
												}
												if(StringUtil.isNullOrEmpty(isCollateralContract)||"02".equals(isCollateralContract)){
													applyInfo.setMortgageContractNo("");
												}else {
													applyInfo.setMortgageContractNo(StringUtil.isNullOrEmpty(applyInfo.getMortgageContractNo())? CodedProduceUtil.getCode(OperationCode.CODE_101.getCode(), true) : applyInfo.getMortgageContractNo());
//													applyInfo.setMortgageContractCode(generateContractCode(Integer.parseInt(loanTypeId), 3, applyInfo.getGuaranteeContractNo().substring(9, 16)));

												}
												if(StringUtil.isNullOrEmpty(isPledgeContract)||"02".equals(isPledgeContract)){
													applyInfo.setPledgeContractNo("");
												}else {
													applyInfo.setPledgeContractNo(StringUtil.isNullOrEmpty(applyInfo.getPledgeContractNo()) ? CodedProduceUtil.getCode(OperationCode.CODE_101.getCode(), true) : applyInfo.getPledgeContractNo());
//													applyInfo.setPledgeContractCode(generateContractCode(Integer.parseInt(loanTypeId),4,applyInfo.getPledgeContractNo().substring(9,16)));
												}
											}
											loanApplyService.saveLoanTemplateInfo(dataTable);
										}
									}
								}
							}
							if(!is_Have_Loan_Contract){
								applyInfo.setGuaranteeContractNo("");
								applyInfo.setMortgageContractNo("");
								applyInfo.setPledgeContractNo("");
							}
							applyInfo.setLoanContractTypeId(Integer.valueOf(loanTypeId));
							if(!StringUtil.isNullOrEmpty(loanContractBegin)){
								applyInfo.setLoanContractBegin(DateUtil.parser(loanContractBegin,"yyyy-MM-dd"));
							}
							if(!StringUtil.isNullOrEmpty(loanContractEnd)){
								applyInfo.setLoanContractEnd(DateUtil.parser(loanContractEnd, "yyyy-MM-dd"));
							}
							//还款帐号
							if(!StringUtil.isNullOrEmpty(accountNum)){
								applyInfo.setAccountNum(accountNum);
							}
							//入账账号
							if(!StringUtil.isNullOrEmpty(enterAccount)){
								applyInfo.setEnterAccount(enterAccount);
							}
							//更新客户编码
//							if(!StringUtil.isNullOrEmpty(cusId)){
//								DataTable dataTable = loanApplyService.getLoanTemplateDataById(LoanProcessTypeEnum.INVESTIGATE.type,"LBIZ_PERSONAL_INFO",applyInfo.getLoanId());
//								if(dataTable!=null&&dataTable.rowSize()>0){
//									dataTable.setName("LBIZ_PERSONAL_INFO");
//									DataRow row = dataTable.getRow(0);
//									row.set("CUSTOMER_NUM",cusId);
//									loanApplyService.saveLoanTemplateInfo(dataTable);
//								}
//
//							}
							//合同编码
							//一般 最高额 卡贷宝
							if(applyInfo.getLoanContractTypeId()!=null){
								if(applyInfo.getLoanContractTypeId()==3){
									applyInfo.setContractCode(StringUtil.isNullOrEmpty(applyInfo.getLoanContractNumber())?"":"中农商银（"+getLoginInfo().getDeptName()+"）个借字["+DateUtil.getYear(new Date())+"]第"+ applyInfo.getLoanContractNumber().substring(9,16)+"号");
									applyInfo.setGuaranteeContractCode(StringUtil.isNullOrEmpty(applyInfo.getGuaranteeContractNo()) ? "" :"中农商银（" + getLoginInfo().getDeptName() + "）保字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getGuaranteeContractNo().substring(9, 16) + "号");
									applyInfo.setMortgageContractCode(StringUtil.isNullOrEmpty(applyInfo.getMortgageContractNo()) ? "" :"中农商银（" + getLoginInfo().getDeptName() + "）抵字[" + DateUtil.getYear(new Date()) + "]第" +  applyInfo.getMortgageContractNo().substring(9, 16) + "号");
									applyInfo.setPledgeContractCode(StringUtil.isNullOrEmpty(applyInfo.getPledgeContractNo()) ? "" : "中农商银（" + getLoginInfo().getDeptName() + "）质字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getPledgeContractNo().substring(9, 16) + "号");
								}
//								else if(applyInfo.getLoanContractTypeId()==4){
//									applyInfo.setContractCode(StringUtil.isNullOrEmpty(applyInfo.getLoanContractNumber())?"":"中农商银（"+getLoginInfo().getDeptName()+"）高借字["+DateUtil.getYear(new Date())+"]第"+ applyInfo.getLoanContractNumber().substring(9,16)+"号");
//									applyInfo.setGuaranteeContractCode(StringUtil.isNullOrEmpty(applyInfo.getGuaranteeContractNo()) ? "" :"中农商银（" + getLoginInfo().getDeptName() + "）高保字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getGuaranteeContractNo().substring(9, 16) + "号");
//									applyInfo.setMortgageContractCode(StringUtil.isNullOrEmpty(applyInfo.getMortgageContractNo()) ? "" :"中农商银（" + getLoginInfo().getDeptName() + "）高抵字[" + DateUtil.getYear(new Date()) + "]第" +  applyInfo.getMortgageContractNo().substring(9, 16) + "号");
//									applyInfo.setPledgeContractCode(StringUtil.isNullOrEmpty(applyInfo.getPledgeContractNo()) ? "" : "中农商银（" + getLoginInfo().getDeptName() + "）高质字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getPledgeContractNo().substring(9, 16) + "号");
//								}
								if(applyInfo.getLoanContractTypeId()==4){
									applyInfo.setContractCode(StringUtil.isNullOrEmpty(applyInfo.getLoanContractNumber())?"":"中农商银（"+getLoginInfo().getDeptName()+"）卡高借字["+DateUtil.getYear(new Date())+"]第"+ applyInfo.getLoanContractNumber().substring(9,16)+"号");
									applyInfo.setGuaranteeContractCode(StringUtil.isNullOrEmpty(applyInfo.getGuaranteeContractNo()) ? "" : "中农商银（" + getLoginInfo().getDeptName() + "）高保字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getGuaranteeContractNo().substring(9, 16) + "号");
									applyInfo.setMortgageContractCode(StringUtil.isNullOrEmpty(applyInfo.getMortgageContractNo()) ? "" : "中农商银（" + getLoginInfo().getDeptName() + "）高抵字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getMortgageContractNo().substring(9, 16) + "号");
									applyInfo.setPledgeContractCode(StringUtil.isNullOrEmpty(applyInfo.getPledgeContractNo()) ? "" : "中农商银（" + getLoginInfo().getDeptName() + "）高质字[" + DateUtil.getYear(new Date()) + "]第" + applyInfo.getPledgeContractNo().substring(9, 16) + "号");
								}
							}
							applyInfoDao.updateApplyInfo(applyInfo);
							renderText(true, "保存成功", id);
						} else {
							renderText(false,"参数错误","json");
						}
				}
			} else {
				renderText(false,"参数错误","json");
			}
		} catch (Exception e) {
			log.error("保存贷款返款信息异常|"+json,e);
			renderText(false,"保存失败",String.valueOf(""));
		}

	}

	/**
	 *
	 **合同提交
	 */
	@RequestMapping(value = "/loanContractSubmit", method = RequestMethod.POST)
	@ResponseBody
	public void loanContractSubmit(@RequestParam(value = "loanId", defaultValue = "") String loanId,
								   @RequestParam(value = "userId", defaultValue = "") String userId
								   ){
		try {

			if (!StringUtil.isNullOrEmpty(loanId) && StringUtil.isNumeric(loanId)){
				if(!StringUtil.isNullOrEmpty(loanId) && StringUtil.isNumeric(userId)){

					PmsUser pmsUser = permissionModule.getPmsUserByUserId(Integer.valueOf(userId));
					if(pmsUser!=null) {
						if(pmsUser.getUserStatus()==0){
							renderText(false, "用户处于禁用状态!", loanId);
						}else{
							LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.valueOf(loanId));
							loanApplyInfo.setContractCheckUser(Integer.valueOf(userId));
							loanApplyInfo.setLoanProcessType(LoanProcessTypeEnum.SIGN.type);
							loanApplyInfo.setContractSubmitDate(new Date());
							loanApplyService.updateApplyInfo(loanApplyInfo,getLoginInfo().getUserId());
							// 记录操作日志
							loanOperationService.addLoanOperation(Integer.valueOf(loanId), LoanOperationType.LOAN_CONTRACT_SUBMIT.typeName, "", getLoginInfo().getUserId(), LoanProcessTypeEnum.CONTRACT.type);
							renderText(true, "提交成功!", loanId);
						}
					}else{
						renderText(false, "账号或密码错误", "json");
					}

				}else {
					renderText(false, "签订用户为空", "json");
				}
			}else{
				renderText(false, "贷款状态异常!", "json");
			}
//			String account = getLoginInfo().getAccount();
//			if(!StringUtil.isNullOrEmpty(account)&&account.equals(userName)){
//				renderText(false,"签订人不能是本人!",loanId);
//			}else {
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("userAccount",userName);
//				PmsUser pmsUser = permissionModule.getPmsUserByAccount(map);
//				if(pmsUser!=null){
//					if(pmsUser.getUserPassword().equals(Md5Encrypt.encrypt(userPassword))){
//						if(pmsUser.getUserStatus()==0){
//							renderText(false, "用户处于禁用状态!", loanId);
//						}else {
//							if (!StringUtil.isNullOrEmpty(loanId) && StringUtil.isNumeric(loanId)) {
//								String result = "success";
//								//1对私客户
//								result = socketDemo.syncCustomerInfo(Integer.valueOf(loanId));
//								if(!"success".equals(result)){
//									renderText(false, result, loanId);
//									return;
//								}
//								//2同步担保信息
//								result = socketDemo.syncGuaranteeInfo(Integer.valueOf(loanId));
//								if(!"success".equals(result)){
//									renderText(false, result, loanId);
//									return;
//								}
//								//3是否有抵押合同编码
//								result = socketDemo.syncMortgageInfo(Integer.valueOf(loanId));
//								if(!"success".equals(result)){
//									renderText(false, result, loanId);
//									return;
//								}
//								//是否有质押合同编码
//								result = socketDemo.syncPledgeInfo(Integer.valueOf(loanId));
//								if(!"success".equals(result)){
//									renderText(false, result, loanId);
//									return;
//								}
//								//合同信息同步
//								result = socketDemo.syncContractInfo(Integer.valueOf(loanId));
//								if(!"success".equals(result)){
//									renderText(false, result, loanId);
//									return;
//								}
//								LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.valueOf(loanId));
//								loanApplyInfo.setSyncStatus(1);
//								loanApplyInfo.setLoanProcessType(LoanProcessTypeEnum.LOAN.type);
//								loanApplyInfo.setContractCheckUser(pmsUser.getUserId());
//								loanApplyService.updateApplyInfo(loanApplyInfo,getLoginInfo().getUserId());
//								// 记录操作日志
//								loanOperationService.addLoanOperation(Integer.valueOf(loanId), LoanOperationType.LOAN_CONTRACT_SUBMIT.typeName, "", this.getLoginInfo().getUserId(), LoanProcessTypeEnum.CONTRACT.type);
//								renderText(true, "签订成功!", loanId);
//							} else {
//								renderText(false, "贷款状态异常!", "json");
//							}
//						}
//					}else {
//						renderText(false,"密码错误!","json");
//					}
//				}else {
//					renderText(false,"用户不存在!","json");
//				}
//			}
		}catch (Exception e) {
			log.error("合同提交失败",e);
			renderText(false,"签订提交失败",String.valueOf(""));
		}
	}
	/**
	 **合同驳回
	 * @param id 贷款loanId
	 */
	@RequestMapping(value = "/contractGiveBack", method = RequestMethod.POST)
	@ResponseBody
	public void contractGiveBack(@RequestParam(value = "id", defaultValue = "") String id){
		try {
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(id));
			Integer loginUserId = this.getLoginInfo().getUserId();
			if (LoanProcessTypeEnum.CONTRACT.type.equals(applyInfo.getLoanProcessType())) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("loanId", id);
				paramMap.put("isValid", "1");
				paramMap.put("auditResult", LoanAuditResultEnum.FAMILY.value);
				paramMap.put("processId", applyInfo.getLoanAuditProcessId());
				//查出当前审核人的审核状态信息，并更新
				List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);
				if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses)) {
					LoanCurrentAuditStatus loanCurrentAuditStatus = loanCurrentAuditStatuses.get(0);
					loanCurrentAuditStatus.setAuditResult(LoanAuditResultEnum.PERSONAL.value);
					currentAuditStatusService.updateCurrentAuditStatus(loanCurrentAuditStatus, this.getLoginInfo().getUserId());
				}
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.APPROVAL.type);
				loanApplyService.updateApplyInfo(applyInfo, this.getLoginInfo().getUserId());
				// 记录操作日志
				loanOperationService.addLoanOperation(Integer.valueOf(id), LoanOperationType.LOAN_CONTRACT_BACK.typeName, "", loginUserId, LoanProcessTypeEnum.CONTRACT.type);
				renderText(true, "操作成功", "json");
			}
		}catch (Exception e){
			log.error("合同驳回失败",e);
			renderText(false,"驳回失败",String.valueOf(""));
		}
	}

	/**
	 **合同注销
	 * @param id 贷款loanId
	 */
	@RequestMapping(value = "/contractCancel", method = RequestMethod.POST)
	@ResponseBody
	public void contractCancel(@RequestParam(value = "id", defaultValue = "") String id){
		try {
			if(StringUtil.isNullOrEmpty(id)){
				renderText(false,"缺少参数!",id);
				return;
			}
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(id));
			if(applyInfo==null){
				renderText(false,"贷款数据异常",id);
				return;
			}
			//目前放款阶段和结清阶段可以注销合同
			if(!(LoanProcessTypeEnum.LOAN.type.equals(applyInfo.getLoanProcessType())
					||LoanProcessTypeEnum.AUTHORIZED.type.equals(applyInfo.getLoanProcessType())
					||LoanProcessTypeEnum.CONTRACT.type.equals(applyInfo.getLoanProcessType())
					||LoanProcessTypeEnum.CLEARANCE.type.equals(applyInfo.getLoanProcessType())
			)
					){
				renderText(false,LoanProcessTypeEnum.getTypeNameByType(applyInfo.getLoanProcessType())+"该贷款阶段不允许合同注销!",id);
				return;
			}
			//合同注销
			String resultStr = socketDemo.cancelContract(applyInfo.getLoanId());
			if (!"success".equals(resultStr)){
				renderText(false,"注销失败:"+resultStr,String.valueOf(""));
				return;
			}
			Integer loginUserId = this.getLoginInfo().getUserId();
			applyInfo.setSyncStatus(-1);
			if(LoanProcessTypeEnum.LOAN.type.equals(applyInfo.getLoanProcessType())||LoanProcessTypeEnum.AUTHORIZED.type.equals(applyInfo.getLoanProcessType())){
				//授权和放款阶段注销退回调查
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.INVESTIGATE.type);
				currentAuditStatusService.deleteAuditStatusByLoanId(applyInfo.getLoanId());
			}else if(LoanProcessTypeEnum.CLEARANCE.type.equals(applyInfo.getLoanProcessType())){
				//不改变 修改结清注销合同标志
				applyInfo.setIsContractCancel(1);
			}
			loanApplyService.updateApplyInfo(applyInfo, this.getLoginInfo().getUserId());
			// 记录操作日志
			loanOperationService.addLoanOperation(Integer.valueOf(id), LoanOperationType.LOAN_CONTRACT_CANCEL.typeName, "合同注销", loginUserId, LoanProcessTypeEnum.LOAN.type);
			renderText(true, "操作成功", "json");
//			if (LoanProcessTypeEnum.LOAN.type.equals(applyInfo.getLoanProcessType())) {
//							if (!StringUtil.isNotEmpty(applyInfo.getIouNum())){
//				log.error("撤销失败:借据号为空");
//				renderText(false,"撤销失败:借据号为空",String.valueOf(""));
//				return;
//
//			}else {
//				//通过借据号查询是否已经有放款账号 有标明已经放款不允许撤销
//				Map<String,Object> result = socketDemo.selectLoanAccountInfo(applyInfo.getLoanId());
//				if ("success".equals(result.get("code"))){
//					//有卡号的 不允许撤销
//					renderText(false,"撤销失败:已经有放款账号",String.valueOf(""));
//					return;
//				}else {
//					//系统异常
//					//正常查询出来放款账号为空的
//					if (result.get("data").toString().contains("没有找到对应的贷款账号")){
//						String resultStr = socketDemo.cancelContractInfo(applyInfo.getLoanId());
//						if (!"success".equals(resultStr)){
//							renderText(false,"注销失败:"+resultStr,String.valueOf(""));
//							return;
//						}else {
//							applyInfo.setSyncStatus(-1);
//							applyInfo.setLoanProcessType(LoanProcessTypeEnum.INVESTIGATE.type);
//							loanApplyService.updateApplyInfo(applyInfo, this.getLoginInfo().getUserId());
//
//							currentAuditStatusService.deleteAuditStatusByLoanId(applyInfo.getLoanId());
//
//							// 记录操作日志
//							loanOperationService.addLoanOperation(Integer.valueOf(id), LoanOperationType.LOAN_CONTRACT_CANCEL.typeName, "合同注销", loginUserId, LoanProcessTypeEnum.LOAN.type);
//							renderText(true, "操作成功", "json");
//						}
//					}else {
//						renderText(false,"注销失败:"+result.get("data").toString(),String.valueOf(""));
//					}
//				}
//			}
//			}
		}catch (Exception e){
			log.error("撤销失败",e);
			renderText(false,"注销失败",String.valueOf(""));
		}
	}
	/**
	 **合同贷款申请
	 * @param id 贷款loanId
	 */
	@RequestMapping(value = "/contractLoanApply", method = RequestMethod.POST)
	@ResponseBody
	public void contractLoanApply(@RequestParam(value = "id", defaultValue = "") String id){
		try {

			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(id));
			Integer loanId = 0;
			Integer loginUserId = this.getLoginInfo().getUserId();
			//主表
			applyInfo.setLoanId(null);
			applyInfo.setLoanProcessType(LoanProcessTypeEnum.INVESTIGATE.type);
			applyInfoService.insertApplyInfo(applyInfo,loginUserId);

			loanId = applyInfo.getLoanId();
			Integer loanTypeId = applyInfo.getLoanTypeId();
			//同步复制工作
			//1.损益信息&资产负债
			if(applyInfo.getLoanClassId()!=null && applyInfo.getLoanClassId().intValue()>0) {
				LoanProfitLossInfo loanProfitLossInfo = profitLossInfoService.getProfitLossInfoByLoanId(Integer.parseInt(id));
				if(loanProfitLossInfo==null){
					loanProfitLossInfo = new LoanProfitLossInfo();
				}
				loanProfitLossInfo.setLoanId(applyInfo.getLoanId());
				loanProfitLossInfo.setLoanClassId(applyInfo.getLoanClassId());
				profitLossInfoService.insertProfitLossInfo(loanProfitLossInfo, applyInfo.getLoanBelongId());

				LoanAssetsInfo assetsInfo = assetsInfoService.getAssetsInfoByLoanId(Integer.parseInt(id));
				if(assetsInfo==null){
					assetsInfo = new LoanAssetsInfo();
				}
				assetsInfo.setLoanId(applyInfo.getLoanId());
				assetsInfo.setLoanClassId(applyInfo.getLoanClassId());
				assetsInfoService.insertAssetsInfo(assetsInfo, applyInfo.getLoanBelongId());
			}
			//2.复制表单内容
			List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByLoanType(String.valueOf(loanTypeId), LoanProcessTypeEnum.INVESTIGATE.type,null);
			for(AutoBaseTemplate template : templateList){
				String tableName = template.getTableName();
				//有贷款id的时候，为贷款编辑；放款时如果没有放款数据，从审批中拉取；列表编辑时，如果没有数据，新增一个id（标记地图使用）
				if(StringUtils.isNotBlank(id) && StringUtils.isNumeric(id) ){
					dealTable(tableName,LoanProcessTypeEnum.APPLY.type,id,loanId);
					dealTable(tableName,LoanProcessTypeEnum.INVESTIGATE.type,id,loanId);
				}
			}
			//3.复制贷款资料文件（LOAN_INFO_ADDED_FILES）
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("loanId",id);
			List<LoanInfoAddedFilesExtend> loanInfoAddedFilesExtendList = infoAddedFilesService.queryLoanInfoFile(condition);
			for (LoanInfoAddedFilesExtend loanAddFile : loanInfoAddedFilesExtendList) {
				loanAddFile.setLoanId(loanId);
				infoAddedFilesService.insertInfoAddedFiles(loanAddFile,loginUserId);
			}


			// 记录操作日志
			loanOperationService.addLoanOperation(Integer.valueOf(loanId), LoanOperationType.LOAN_APPLY_CREATE.typeName, "新建贷款", loginUserId, LoanProcessTypeEnum.CONTRACT_CANCEL.type);

			renderText(true, "操作成功", String.valueOf(loanId));
		}catch (Exception e){
			log.error("重新申请贷款失败",e);
			renderText(false,"重新申请贷款失败",String.valueOf(""));
		}
	}

	/**
	 **签订提交
	 * @param loanId 贷款loanId
	 */
	@RequestMapping(value = "/contractSignSubmit", method = RequestMethod.POST)
	@ResponseBody
	public void contractSignSubmit(@RequestParam(value = "loanId", defaultValue = "") String loanId){
		try {
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
			Integer loginUserId = this.getLoginInfo().getUserId();
			if (LoanProcessTypeEnum.SIGN.type.equals(applyInfo.getLoanProcessType())) {
				if (!StringUtil.isNullOrEmpty(loanId) && StringUtil.isNumeric(loanId)) {
					String result = "success";
					//1对私客户
					result = socketDemo.syncCustomerInfo(Integer.valueOf(loanId));
					if(!"success".equals(result)){
						renderText(false, result, loanId);
						return;
					}
					//3是否有抵押合同编码
					result = socketDemo.syncMortgageInfo(Integer.valueOf(loanId));
					if(!"success".equals(result)){
						renderText(false, result, loanId);
						return;
					}
					//是否有质押合同编码
					result = socketDemo.syncPledgeInfo(Integer.valueOf(loanId));
					if(!"success".equals(result)){
						renderText(false, result, loanId);
						return;
					}
					//2同步担保信息
					result = socketDemo.syncGuaranteeInfo(Integer.valueOf(loanId));
					if(!"success".equals(result)){
						renderText(false, result, loanId);
						return;
					}
					//合同信息同步
					result = socketDemo.syncContractInfo(Integer.valueOf(loanId));
					if(!"success".equals(result)){
						renderText(false, result, loanId);
						return;
					}
//					LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.valueOf(loanId));
					applyInfo.setSyncStatus(1);
					applyInfo.setLoanProcessType(LoanProcessTypeEnum.LOAN.type);
					applyInfo.setContractSignDate(new Date());
					loanApplyService.updateApplyInfo(applyInfo,getLoginInfo().getUserId());

					// 记录操作日志
					loanOperationService.addLoanOperation(Integer.valueOf(loanId), LoanOperationType.CONTRACT_SIGN_SUBMIT.typeName, "", this.getLoginInfo().getUserId(), LoanProcessTypeEnum.SIGN.type);
					renderText(true, "签订成功!", loanId);
				} else {
					renderText(false, "贷款状态异常!", "json");
				}
			}
		}catch (Exception e){
			log.error("合同签订失败",e);
			renderText(false,"签订失败",String.valueOf(""));
		}
	}
	//核对客户号信息
	private String checkCusNoInfo(String tableName,Integer loanId,Integer mortgageOrPledge){
		//1.首先调用省信贷客户查询接口，查询到 则更新客户编码，更新为存量客户
		//2.查询不到调用核心客户查询接口 查询到 更新客户编码
		//3.还是查询不大的话 不允许提交 提示借款人尚未再核心开户
			DataTable table = null;
			if(mortgageOrPledge!=null){
				table = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 1);
			}else {
				table = applyInfoService.getDataTableByLoanId(tableName, LoanProcessTypeEnum.INVESTIGATE.type, loanId);
			}
			if(table!=null || table.rowSize()>0){
				table.setName(tableName);
				for (int r = 0; r < table.getRows().length; r++) {
					DataRow row = table.getRow(r);
					Map<String, String> map = new HashMap<String, String>();
					if("LBIZ_LOAN_GUARANTEE".equals(tableName)){
						map.put("证件号码", (String) row.get("ID_CARD"));
						map.put("证件类型", (String) row.get("IDENTIFY_TYPE"));
					}else if("LBIZ_PLEDGE_ITEM".equals(tableName)){
						map.put("证件号码",(String)row.get("ID_NUM"));
					}else if("LBIZ_PERSONAL_INFO".equals(tableName)){
						map.put("证件号码", (String) row.get("IDENTIFY_NUM"));
						map.put("证件类型", (String) row.get("IDENTIFY_TYPE"));
					}
					//可能存在空记录 筛掉
					if(StringUtil.isNullOrEmpty(map.get("证件号码"))){
						continue;
					}
					String cusName="";
					if("LBIZ_PERSONAL_INFO".equals(tableName)){
						cusName = (String)row.get("CUSTOMER_NAME");
					}else if("LBIZ_PLEDGE_ITEM".equals(tableName)){
						cusName = (String)row.get("PLEDGE_OWNER");
					}else if("LBIZ_LOAN_GUARANTEE".equals(tableName)){
						cusName = (String)row.get("FULL_NAME");
					}
					//信贷客户查询
					String returnStr = socketDemo.relatedDataQuery(loanId, map, SocketCodeTypeEnum.CHNCMI4050);
					JSONObject jsonObject = JSONObject.fromObject(returnStr);
					if(jsonObject.getBoolean("success") && !StringUtil.isNullOrEmpty(jsonObject.getString("cus_id"))){
						row.set("CUSTOMER_NUM",jsonObject.getString("cus_id"));
						row.set("IS_STOCK_CUST","01");
						//20-正式客户  91-合并注销 00-草稿客户（正式） 01-临时客户 02-草稿客户（担保） 03-担保客户
						//1.抵质人和借款人必须是正式客户
						String cus_status = jsonObject.getString("cus_status");
						if(("LBIZ_PERSONAL_INFO".equals(tableName)|| "LBIZ_PLEDGE_ITEM".equals(tableName) )&& !"20".equals(cus_status)){
								return cusName + "必须是正式客户!";
						}
						//2.担保人
						if("LBIZ_LOAN_GUARANTEE".equals(tableName) && !("20".equals(cus_status)||"03".equals(cus_status))){
							return cusName + "必须是正式客户或者担保客户!";
						}
					}else{
						//核心查询
						row.set("IS_STOCK_CUST","02");
						returnStr = socketDemo.queryCusInfo(loanId, map, SocketCodeTypeEnum.QRY00402);
						jsonObject = JSONObject.fromObject(returnStr);
						if(jsonObject.getBoolean("success") && !StringUtil.isNullOrEmpty(jsonObject.getString("cus_no"))){
							row.set("CUSTOMER_NUM",jsonObject.getString("cus_no"));
						}else {
							//查询失败不允许保存
							if( "-1".equals(jsonObject.getString("code"))){
								return cusName+"核心查询客户失败!原因："+jsonObject.getString("msg");
							}else{
								//借款人，抵质押人必须在核心已开户
								if("LBIZ_PERSONAL_INFO".equals(tableName)||"LBIZ_PLEDGE_ITEM".equals(tableName)){
									return cusName + "在核心尚未开户!原因："+jsonObject.getString("msg");
								}
							}
						}
					}
				}
				loanApplyService.saveLoanTemplateInfo(table,true);
			}
		return "success";
	}
	/**
	 **签订退回
	 * @param loanId 贷款loanId
	 */
	@RequestMapping(value = "/contractSignBack", method = RequestMethod.POST)
	@ResponseBody
	public void contractSignBack(@RequestParam(value = "loanId", defaultValue = "") String loanId){
		try {
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
			Integer loginUserId = this.getLoginInfo().getUserId();
			if (LoanProcessTypeEnum.SIGN.type.equals(applyInfo.getLoanProcessType())) {

					LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.valueOf(loanId));
					loanApplyInfo.setLoanProcessType(LoanProcessTypeEnum.CONTRACT.type);
					loanApplyInfo.setContractCheckUser(0);
					loanApplyService.updateApplyInfo(loanApplyInfo, getLoginInfo().getUserId());
					// 记录操作日志
					loanOperationService.addLoanOperation(Integer.valueOf(loanId), LoanOperationType.CONTRACT_SIGN_BACK.typeName, "", this.getLoginInfo().getUserId(), LoanProcessTypeEnum.SIGN.type);
					renderText(true, "退回成功!", loanId);
				} else {
					renderText(false, "贷款状态异常!", "json");
				}
		}catch (Exception e){
			log.error("签订退回失败",e);
			renderText(false,"签订退回失败",String.valueOf(""));
		}
	}
	private void dealTable(String tableName,String type, String id,Integer loanId){
		DataTable dataTable = applyInfoService.getDataTableByLoanId(tableName,type,Integer.valueOf(id));
		if(dataTable!=null && dataTable.rowSize()>0){
			dataTable.addColumn("ID");
			dataTable.setName(tableName);
			for(int i=0;i<dataTable.rowSize();i++) {
				DataRow dataRow = dataTable.getRow(i);
				dataRow.set("ID", 0);
				dataRow.set("LOAN_ID", loanId);
				dataRow.set("LOAN_PROCESS_TYPE", type);
				dataRow.set("CREATE_DATE", new Date());
				dataRow.set("CREATE_USER", this.getLoginInfo().getUserId());
				dataRow.set("UPDATE_DATE", new Date());
				dataRow.set("UPDATE_USER", this.getLoginInfo().getUserId());
			}
			loanApplyService.saveLoanTemplateInfo(dataTable,false);
			}

	}
	/**
	 * 将原始数据转换成表单map
	 * @param customerMap
	 * @param map
	 */
	private void setCustomerMap(Map<String, Object> customerMap, Map<String, Object> map) {
		customerMap.clear();
		for (String key : map.keySet()) {
			String val = (String) map.get(key);
			if (key.indexOf("field.") > -1) {
				String propertyName = key.substring("field.".length());
				if(val != null){
					if ("identifyNum".equals(propertyName))
						val = IdCardUtil.toUpperCase(val);
					if ("idCard".equals(propertyName))
						val = IdCardUtil.toUpperCase(val);
					customerMap.put(propertyName, val);
				}
			}
		}
	}

	private void setDataRowByFieldList(DataRow dataRow, List<AutoBaseField> fieldList, Map<String, Object> customerMap) {
		if(CollectionUtils.isNotEmpty(fieldList)){
			for (AutoBaseField baseFiled : fieldList) {
				String column = baseFiled.getColumn();
				String field = baseFiled.getField();
				Object o= MapUtils.getObject(customerMap,field);
				dataRow.set(column,o);
			}
		}
	}


	/**
	 *
	 **合同提交页面
	 */
	@RequestMapping(value = "/getContractSubmitPage")
	public String getContractSubmitPage(){
			Integer teamGroupId = getLoginInfo().getTeamGroupId();
			String loanId = getRequest().getParameter("loanId");
			Integer loginUserId = getLoginInfo().getUserId();
//			List<PmsRole> roleList = permissionModule.getTeamRolesByGroupId(teamGroupId);
//			setAttribute("roleList",roleList);
			setAttribute("loanId",loanId);
			setAttribute("teamGroupId",teamGroupId);
			setAttribute("loginUserId",loginUserId);
			return "/loan/vm/contract/loanContractSubmit";
	}

	/**
	 *开户行
	 */
	@RequestMapping(value = "/queryAccountBank")
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String queryAccountBank(@RequestParam(defaultValue ="0" ,value="text")  String text){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("coffer",text);
		List<DepositBank> list = applyInfoService.getDepositBankListByCondition(condition);
		setAttribute("list",list);
		return "/loan/vm/contract/depositBankGrid";
	}


	/**
	 * 根据团队id 获取经理
	 */
	@RequestMapping("queryUserByRoleId")
	@ResponseBody
	@NoLoginInterceptor
	public void queryUserByRoleId(@RequestParam(defaultValue ="0" ,value="roleId")  String roleId,
								  @RequestParam(defaultValue ="0" ,value="teamGroupId")  String teamGroupId){
		List<PmsUser> list = permissionModule.queryUserListByRoleId(roleId, teamGroupId);

		JSONObject resultJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for (PmsUser user: list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", user.getUserId());
			jsonObject.put("userName",user.getUserName());
			jsonObject.put("userAccount",user.getUserAccount());
			jsonArray.add(jsonObject);
		}
		resultJson.put("data", jsonArray);
		renderJson(true, "", resultJson);
	}
	@RequestMapping("getContractSignCheckPage")
	public String getContractSignCheckPage(){
		String loanId = getRequest().getParameter("loanId");
		setAttribute("loanId",loanId);
		return "/loan/vm/contract/contractSignCheck";
	}

	@RequestMapping("selectAccountBank")
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String selectAccountBank(){
//		String loanId = getRequest().getParameter("loanId");
//		setAttribute("loanId",loanId);

		return "/loan/vm/contract/selectAccountBank";
	}

	/**
	 * 选择贷款投向页面
	 * @return
	 */
	@RequestMapping("selectLoanOrientation")
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String selectLoanOrientation(){
		String loanId = getRequest().getParameter("loanId");
		setAttribute("loanId",loanId);
		return "/loan/vm/contract/selectLoanOrientation";
	}

	/**
	 * 取产品分类 产品类型
	 * @return
	 */
	@RequestMapping("selectBizAndPrdType")
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String selectBizAndPrdType(){
		return "/loan/vm/contract/selectBizAndPrdType";
	}

	/**
	 * 取业务条线
	 * @return
	 */
	@RequestMapping("selectCrmPrdType")
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String selectCrmPrdType(){
		return "/loan/vm/contract/selectCrmPrdType";
	}

	/**
	 * 贷款投向
	 * @return
	 */
	@RequestMapping("getLoanOrientationJson")
	@ResponseBody
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String getLoanOrientationJson(){
		String jsonString =applyInfoService.getLoanOrientationJson();
		return jsonString;
	}

	/**
	 * 业务品种
	 * @return
	 */
	@RequestMapping("getBizAndPrdTypeJson")
	@ResponseBody
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String getBizAndPrdTypeJson(){
		String jsonString =applyInfoService.getBizAndPrdTypeJson(null);
		return jsonString;
	}

	/**
	 * 业务条线
	 * @returncr
	 */
	@RequestMapping("queryCrmPrdTypeJson")
	@ResponseBody
	@NoLoginInterceptor
	@NoTokenAnnotation
	public String queryCrmPrdTypeJson(){
		String jsonString =applyInfoService.queryCrmPrdTypeJson(null);
		return jsonString;
	}
	/**
	 * 根据卡号/存款帐号查询客户信息
	 * @return
	 */
	@RequestMapping("checkCusInfo")
	@ResponseBody
	@NoLoginInterceptor
	public void checkCusInfo(@RequestParam(defaultValue ="0" ,value="loanId")  String loanId) {
		if (!StringUtil.isNullOrEmpty(loanId)) {
			//保存贷款信息前核对借款人、担保人、抵质押物客户信息，根据接口QRY00402查询客户编码
			String result = "success";
			//借款人
			result = checkCusNoInfo("LBIZ_PERSONAL_INFO", Integer.parseInt(loanId),null);
			if (!"success".equals(result)) {
				renderText(false, result, "json");
				return;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("IS_GUARANTEE_CONTRACT", "LBIZ_LOAN_GUARANTEE");
			map.put("IS_COLLATERAL_CONTRACT", "LBIZ_PLEDGE_ITEM");
			map.put("IS_PLEDGE_CONTRACT", "LBIZ_PLEDGE_ITEM");
			DataTable table = applyInfoService.getDataTableByLoanId("LOAN_CONTRACT", LoanProcessTypeEnum.CONTRACT.type, Integer.parseInt(loanId));
			if (table != null && table.rowSize() > 0) {
				DataRow row = table.getRow(0);
				//担保、抵质押
				for (Map.Entry<String, String> entry : map.entrySet()) {
					if ("01".equals(row.get(entry.getKey()))) {
						Integer mortgageOrPledge = "IS_GUARANTEE_CONTRACT".equals(entry.getKey())?null:("IS_COLLATERAL_CONTRACT".equals(entry.getKey())?1:2);
						result = checkCusNoInfo(entry.getValue(), Integer.parseInt(loanId),mortgageOrPledge);
						if (!"success".equals(result)) {
							renderText(false, result, "json");
							return;
						}
					}
				}
				renderText(true, "核对成功", "json");
			} else {
				renderText(false, "贷款状态异常", loanId);
			}
		}
	}
	/**
	 * 根据卡号/存款帐号查询客户信息
	 * @return
	 */
	@RequestMapping("checkCusInfoData")
	@ResponseBody
	@NoLoginInterceptor
	public String checkCusInfoData(@RequestParam(defaultValue ="0" ,value="accNo")  String accNo,
								   @RequestParam(defaultValue ="0" ,value="loanId")  String loanId){
		if(!StringUtil.isNullOrEmpty(accNo) && !StringUtil.isNullOrEmpty(loanId)){
			Map<String,String> map = new HashMap<String, String>();
			map.put("账户",accNo);
			String returnMsg = socketDemo.queryCusInfo(Integer.parseInt(loanId),map,SocketCodeTypeEnum.QRY00400);
			return returnMsg;
		}else{
			return null;
		}
	}

	/**
	 * 合同提交遮罩层
	 * @return
	 */
	@RequestMapping("getContractCheckDia")
	@NoLoginInterceptor
	public String getContractCheckDia(){
		String loanId = getRequest().getParameter("loanId");
		setAttribute("loanId",loanId);
		return "/loan/vm/contract/contractCheckDialog";
	}


	@RequestMapping("/operationCollateral")
	public void operationCollateral(@RequestParam(value = "id",required = true) String id,@RequestParam(value = "type",required = true) String type){
		String result = null;
		String code = null;
		String guarWay = null;
		Integer loanId = null;
		String status = null;
		try {
			DataTable dataTable = applyInfoService.getPledgeDataTableById(Integer.valueOf(id));
			if (dataTable!=null){
				for (DataColumn column:dataTable.getColumns()){
					if ("WARRANTY_NUM".equals(column.getName())){
						if (column.getValues()[0]==null){
							renderText(false, "担保编号不能为空", String.valueOf(""));
							return;
						}
						code = column.getValues()[0].toString();
					}else if ("MORTGAGE_OR_PLEDGE".equals(column.getName())){
						if (column.getValues()[0]==null){
							renderText(false, "抵押质押类型不能为空", String.valueOf(""));
							return;
						}
						guarWay = column.getValues()[0].toString();
					}else if ("LOAN_ID".equals(column.getName())){
						loanId = (Integer) column.getValues()[0];
					}else if ("PLEDGE_STATUS".equals(column.getName())){
						status = column.getValues()[0]==null?"":column.getValues()[0].toString();
					}
				}
			}
			if ("1".equals(type)){
				if ("1".equals(status)) {
					renderText(false, "不能重复入库", String.valueOf(""));
					return;
				}else if(!"0".equals(status)){
					renderText(false, "无补录前不能入库", String.valueOf(""));
					return;
				}
			}else if ("2".equals(type)){
				if ("2".equals(status)) {
					renderText(false, "不能重复出库", String.valueOf(""));
					return;
				}
				LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(loanId);
				if(!"1".equals(loanApplyInfo.getIsContractCancel() +"")){
					renderText(false, "合同未注销不能出库", String.valueOf(""));
					return;
				}
			}

			result = socketDemo.operationCollateral(loanId, code, guarWay, type);
			if ("success".equals(result)){
				applyInfoService.updatePledgeStatusById(Integer.valueOf(id),type);
				renderText(true,"交易成功:",String.valueOf(""));
			}else {
				renderText(false,"出入库出错:"+result,String.valueOf(""));
			}
		}catch (Exception e){
			log.error("operationCollateral 出入库出错：id="+id+" type="+type,e);
			renderText(false,"出入库出错:"+result,String.valueOf(""));
		}
	}

}
