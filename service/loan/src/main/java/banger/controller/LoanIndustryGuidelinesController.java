package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanIndustryGuidelines;
import banger.domain.system.SysImportHistory;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.framework.util.OperationUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IImportHistoryProvider;
import banger.service.intf.IIndustryGuidelinesService;
import banger.service.intf.ILoanApplyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 行业指引表页面访问类
 */
@Controller
@RequestMapping("/loanIndustryGuidelines")
public class LoanIndustryGuidelinesController extends BaseController {
	private static final long serialVersionUID = 5295633017871872140L;
	@Autowired
	private IIndustryGuidelinesService industryGuidelinesService;
	@Resource
	private IProgressBarManager progressBarManager;
	@Resource
	ILoanApplyService loanApplyService;
	@Resource
	IImportHistoryProvider importHistoryProvider;

	private String fullFilename;		//上传的本地文件名
//	private File uplodeFile;
	/**
	 * 得到行业指引表列表页面
	 * @return
	 */
	@RequestMapping("/getIndustryGuidelinesListPage")
	public String getIndustryGuidelinesListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanIndustryGuidelines> industryGuidelinesList = industryGuidelinesService.queryIndustryGuidelinesList(condition,this.getPage());
		setAttribute("industryGuidelinesList",industryGuidelinesList);
		return SUCCESS;
	}

	/**
	 * 查询行业指引表列表数据
	 * @return
	 */
	@RequestMapping("/queryIndustryGuidelinesListData")
	public String queryIndustryGuidelinesListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanIndustryGuidelines> industryGuidelinesList = industryGuidelinesService.queryIndustryGuidelinesList(condition,this.getPage());
		renderText(JsonUtil.toJson(industryGuidelinesList, "id","gradeFirst,gradeSecond,gradeThird,itemName,value1,value2,value3,value4,value5,valueType,createDate,updateDate").toString());
		return null;
	}

	/**
	 * 得到行业指引表新增页面
	 * @return
	 */
	@RequestMapping("/getIndustryGuidelinesInsertPage")
	public String getIndustryGuidelinesInsertPage(){
		LoanIndustryGuidelines industryGuidelines = new LoanIndustryGuidelines();
		setAttribute("industryGuidelines",industryGuidelines);
		return SUCCESS;
	}

	/**
	 * 得到行业指引表修改页面
	 * @return
	 */
	@RequestMapping("/getIndustryGuidelinesUpdatePage")
	public String getIndustryGuidelinesUpdatePage(){
		String id = getParameter("id");
		LoanIndustryGuidelines industryGuidelines = industryGuidelinesService.getIndustryGuidelinesById(Integer.parseInt(id));
		setAttribute("industryGuidelines",industryGuidelines);
		return SUCCESS;
	}

	/**
	 * 得到行业指引表查看页面
	 * @return
	 */
	@RequestMapping("/getIndustryGuidelinesDetailPage")
	public String getIndustryGuidelinesDetailPage(){
		String id = getParameter("id");
		LoanIndustryGuidelines industryGuidelines = industryGuidelinesService.getIndustryGuidelinesById(Integer.parseInt(id));
		setAttribute("industryGuidelines",industryGuidelines);
		return SUCCESS;
	}

	/**
	 * 新增行业指引表数据
	 * @return
	 */
	@RequestMapping("/insertIndustryGuidelines")
	public String insertIndustryGuidelines(@ModelAttribute LoanIndustryGuidelines industryGuidelines){
		Integer loginUserId = getLoginInfo().getUserId();
		industryGuidelinesService.insertIndustryGuidelines(industryGuidelines,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改行业指引表数据
	 * @return
	 */
	@RequestMapping("/updateIndustryGuidelines")
	public String updateIndustryGuidelines(@ModelAttribute LoanIndustryGuidelines industryGuidelines){
		Integer loginUserId = getLoginInfo().getUserId();
		industryGuidelinesService.updateIndustryGuidelines(industryGuidelines,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除行业指引表数据
	 * @return
	 */
	@RequestMapping("/deleteIndustryGuidelines")
	public String deleteIndustryGuidelines(){
		String id = getParameter("id");
		industryGuidelinesService.deleteIndustryGuidelinesById(Integer.parseInt(id));
		renderText(SUCCESS);
		return null;
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:跳转到行业指引表的dialog
	 * @Date: 14:31 2017/9/11
	 */
	@RequestMapping("/getIndustryGuidelinesPage")
	public ModelAndView getIndustryGuidelinesPage(@RequestParam Integer loanId,
												  @RequestParam String businessCatalog,
												  @RequestParam String loanProcess) throws UnsupportedEncodingException {
		//根据行业等级获取行业信息
		ModelAndView mv = new ModelAndView("/loan/vm/industryGuide/industryGuidelines");
		Map<String, Object> map = new HashedMap();
		map.put("industryGrade", "grade_first");
		List<LoanIndustryGuidelines> firstList = industryGuidelinesService.getIndustryGuidelinesByGroup(map);
		String str = URLDecoder.decode(this.getRequest().getParameter("businessCatalog"), "UTF-8");
		if (StringUtil.isNotEmpty(businessCatalog)) {
			String[] split = str.split(",");
			if (split.length == 3) {
				mv.addObject("gradeFirst", split[0]);
				mv.addObject("gradeSecond", split[1]);
				mv.addObject("gradeThird", split[2]);
			}
		}
		mv.addObject("loanId", loanId);
		mv.addObject("firstList", firstList);
		mv.addObject("loanProcess", loanProcess);

		return mv;
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:
	 * @Date: 16:38 2017/9/11
	 */
	@RequestMapping("/getIndustryGuidelineByGroup")
	public String getIndustryGuidelineByGroup(@RequestParam("industryGrade") String industryGrade,
										@RequestParam("industryParGrade") String industryParGrade,
										@RequestParam("industryParName") String industryParName){
		log.info("web开始调取获取根据行业等级获取行业信息接口,参数industryGrade:------" + industryGrade +
				"参数industryParGrade:----" + industryParGrade + ",industryParName:-----" + industryParName);
		//根据行业等级获取行业信息
		Map<String, Object> map = new HashedMap();
		map.put("industryGrade", industryGrade);
		map.put("industryParGrade", industryParGrade);
		map.put("industryParName", industryParName);

		List<LoanIndustryGuidelines> list = industryGuidelinesService.getIndustryGuidelinesByGroup(map);
		renderText(JsonUtil.toJson(list, "id","gradeFirst,gradeSecond,gradeThird").toString());
		return null;
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:跳转到行业指引导入页面
	 * @Date: 10:19 2017/9/7
	 */
	@RequestMapping("/importGuideLinesPage")
	public String importGuideLinesPage(){
		return "/loan/vm/industryGuide/importIndustryGuide";
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:下载行业指引模板Excel
	 * @Date: 10:58 2017/9/7
	 */
	@RequestMapping("/downloadExcelFile")
	public void downloadExcelFile() {
		String filename = FileUtil.contact(getRootPath(), "template"+ File.separator+"importIndustry.xlsx");
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				getResponse().addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode("行业指引导入模板.xlsx",
								"utf-8").replace("+", "%20"));
				getResponse().setContentType("xlsx/*"); // 设置返回的文件类型
				OutputStream output = getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
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
				log.error("下载行业指引Excel模板 error:"+e.getMessage(),e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("下载行业指引Excel模板 error:"+e.getMessage(),e);
			}
		}
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:文件上传
	 * @Date: 13:39 2017/9/7
	 */
	@RequestMapping("/doUpload")
	public void doUpload(HttpServletRequest request,@RequestParam("uplodeFile") MultipartFile uplodeFile) throws IOException {
		if (uplodeFile.getSize() > 2 * 1024 * 1024) {
			//判断文件大小不能超过5M
			renderText("文件不能超过2M");
		}else{
//			fullFilename = URLDecoder.decode(getRequest().getParameter("fullFilename"), "UTF-8");
			String timePath = new SimpleDateFormat("yyyyMMddHmsS").format(Calendar.getInstance().getTime());
			String savePath = FileUtil.contact(getRootPath(), "temp_file"+File.separator+"import"+File.separator+timePath);
			File savedDir = new File(savePath);
			if (!savedDir.exists()) {// 文件不存在则创建
				savedDir.mkdirs();
			}
			String saveFilename =  FileUtil.contact(savePath,"importIndustry.xlsx");
			uplodeFile.transferTo(new File(saveFilename));
			renderText(saveFilename);
		}

	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到导入Excel的表头信息
	 * @Date: 13:55 2017/9/7
	 */
	@RequestMapping("/getExcelHeadData")
	public String getExcelHeadData(HttpServletRequest request) throws IOException {
		String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
		Map<String, Object> importExcelHead = industryGuidelinesService.getImportExcelHead(saveFilename);
		List<String> headList = (List<String>) importExcelHead.get("columns");
		Object total = importExcelHead.get("total");
		List<ColumnInfo> list = new ArrayList<ColumnInfo>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("行业一级（必填）","gradeFirst");
		map.put("行业二级（必填）","gradeSecond");
		map.put("行业三级（必填）","gradeThird");
		map.put("项目（必填）","itemName");
		map.put("优秀值（必填）","value1");
		map.put("良好值（必填）","value2");
		map.put("平均值（必填）","value3");
		map.put("较低值（必填）","value4");
		map.put("较差值（必填）","value5");
		for(int i=0;i<headList.size();i++){
			String head = headList.get(i);
			if(map.containsKey(head)){
				String field = (map.containsKey(head))?map.get(head):"";
				ColumnInfo col = new ColumnInfo(i,head,field);
				list.add(col);
			}else{
				renderText("warning");
				return null;
			}
		}
		setAttribute("columnInfos",list);
		setAttribute("headList",headList);
		setAttribute("total",total);
		return "/loan/vm/industryGuide/importGuideColumn";
	}
	/**
	 * 下载Excel模板
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/downloadFailFile")
	public void downloadFailFile(HttpServletRequest request) throws IOException {
		String filename = URLDecoder.decode(this.getParameter("filename"), "UTF-8");
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				getResponse().addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode("行业指引导入错误数据.csv",
								"utf-8").replace("+", "%20"));
				getResponse().setContentType("csv/*"); // 设置返回的文件类型
				OutputStream output = getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
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
				log.error("下载行业指引导入错误数据Excel模板 error:"+e.getMessage(),e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("下载行业指引导入错误数据Excel模板 error:"+e.getMessage(),e);
			}
		}
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:执行行业指引导入操作
	 * @Date: 15:05 2017/9/7
	 */
	@RequestMapping("/doImportIndustryGuide")
	public void doImportIndustryGuide(HttpServletRequest request){
		try {
			String leftColumns = this.getParameter("leftColumns");
			String rightFields = this.getParameter("rightFields");
			String importTotal = this.getParameter("importTotal");
			String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
			String[] excelColumns = leftColumns.split(",");
			String[] fields = rightFields.split(",");
			List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
			for(int i=0;i<excelColumns.length;i++){
				ColumnInfo info = new ColumnInfo();
				info.setIndex(i);
				info.setColumnName(excelColumns[i]);
				if(!StringUtil.isNullOrEmpty(fields[i]))info.setFieldName(fields[i]);
				columns.add(info);
			}


			//首先清除行业指引表的数据
			industryGuidelinesService.truncateLoanIndustryGuideLines();
			Integer userId = getLoginInfo().getUserId();
			//插入导入历史表
			SysImportHistory sysImportHistory = new SysImportHistory();
			sysImportHistory.setUserId(userId);
			sysImportHistory.setImportModuleName("行业指引");
			sysImportHistory.setIsComplete(0);
			sysImportHistory.setProgressCode("industryGuideImport_"+userId);
			sysImportHistory.setProgressName("行业指引正在导入");
			sysImportHistory.setImportTotal(Integer.valueOf(importTotal));
			importHistoryProvider.insertImportHistory(sysImportHistory,userId);

			ProgressBar progressBar = industryGuidelinesService.importExcel(userId.toString(), saveFilename, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入行业指引报错 error:"+e.getMessage());
			//更新导入状态
			Map<String, Object> condition = new HashedMap();
			condition.put("userId", getLoginInfo().getUserId());
			condition.put("isComplete", 0);
			condition.put("progressCode", "industryGuideImport_" + getLoginInfo().getUserId());
			List<SysImportHistory> list = importHistoryProvider.queryImportHistoryList(condition);
			for (SysImportHistory history : list) {
				history.setIsComplete(1);
				history.setProgressName("行业指引导入系统报错");
				importHistoryProvider.updateImportHistory(history,getLoginInfo().getUserId());
			}
		}

		//导入之后删除文件(先不删除,文件夹中包含错误数据文件,导入数据文件)
//		File file = new File(saveFilename);
//		file.delete();

	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到行业指引导入进度信息
	 * @Date: 14:56 2017/9/8
	 */
	@RequestMapping("/getImportProgress")
	public void getImportProgress(HttpServletRequest request){
		String importTotal = this.getParameter("importTotal");
		BigDecimal decimal = new BigDecimal(importTotal);
		ProgressBar bar = progressBarManager.getProgressBarById("industryGuideImport_"+ getLoginInfo().getUserId());
		JSONObject jo = new JSONObject();
		if(bar!=null){
			jo.put("progressId", bar.getId());
			jo.put("total", bar.getTotal());
//			jo.put("rate", bar.getRate());
			jo.put("rate", OperationUtil.divide(decimal, 1, new BigDecimal(bar.getCurrent())));
			jo.put("current", bar.getCurrent());
			jo.put("isCompleted", bar.isCompleted());
			jo.put("isClosed", bar.isClosed());
		}
		renderText(jo.toString());
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到导入结果信息
	 * @Date: 15:20 2017/9/7
	 */
	@RequestMapping("/getImportResult")
	public void getImportResult(){
		Integer userId = getLoginInfo().getUserId();
		JSONObject jo = new JSONObject();
		IImportResult result = industryGuidelinesService.getImportResultByUserId(userId.toString());
		if(result!=null){
			jo.put("total", result.getRowCount());
			jo.put("success", result.getSuccessCount());
			jo.put("insert", result.getInsertCount());
			jo.put("update", result.getUpdateCount());
			jo.put("fail", result.getFailCount());
			jo.put("filename", result.getFailFilename());
			jo.put("reportFileName", result.getReportFileName());
		}
		//导入历史记录更新数据为导入完成
		Map<String, Object> condition = new HashedMap();
		condition.put("userId", userId);
		condition.put("isComplete", 0);
		condition.put("progressCode", "industryGuideImport_" + userId);
		List<SysImportHistory> list = importHistoryProvider.queryImportHistoryList(condition);
		for (SysImportHistory history : list) {
			history.setIsComplete(1);
			history.setProgressName("行业指引导入成功");
			importHistoryProvider.updateImportHistory(history,userId);
		}
		renderText(jo.toString());
	}

	public String getFullFilename() {
		return fullFilename;
	}

	public void setFullFilename(String fullFilename) {
		this.fullFilename = fullFilename;
	}

//	public File getUplodeFile() {
//		return uplodeFile;
//	}
//
//	public void setUplodeFile(File uplodeFile) {
//		this.uplodeFile = uplodeFile;
//	}
}
