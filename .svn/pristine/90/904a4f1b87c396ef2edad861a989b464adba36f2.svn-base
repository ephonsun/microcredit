package banger.service.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import banger.domain.system.SysImportHistory;
import banger.framework.component.dataimport.AbstractImportHandler;
import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.IRecordReader;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.dataimport.context.DefaultImportContext;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.util.*;
import banger.moduleIntf.IImportHistoryProvider;
import banger.moduleIntf.IIndustryGuidelinesProvider;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IIndustryGuidelinesDao;
import banger.service.intf.IIndustryGuidelinesService;
import banger.domain.loan.LoanIndustryGuidelines;

import javax.annotation.Resource;

/**
 * 行业指引表业务访问类
 */
@Repository
public class IndustryGuidelinesService implements IIndustryGuidelinesService,IIndustryGuidelinesProvider {

	private static final Logger logger = LoggerFactory.getLogger(IndustryGuidelinesService.class);

	public IndustryGuidelinesService() {
		this.handlerMap = new HashMap<String, AbstractImportHandler>();
	}
	@Autowired
	private IIndustryGuidelinesDao industryGuidelinesDao;
	private Map<String, AbstractImportHandler> handlerMap;
	@Resource
	private IProgressBarManager progressBarManager;
	@Resource
	IImportHistoryProvider importHistoryProvider;

	public IProgressBarManager getProgressBarManager() {
		return progressBarManager;
	}

	public void setProgressBarManager(IProgressBarManager progressBarManager) {
		this.progressBarManager = progressBarManager;
	}

	/**
	 * 新增行业指引表
	 * @param industryGuidelines 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertIndustryGuidelines(LoanIndustryGuidelines industryGuidelines,Integer loginUserId){
		industryGuidelines.setCreateDate(DateUtil.getCurrentDate());
		industryGuidelines.setUpdateDate(DateUtil.getCurrentDate());
		this.industryGuidelinesDao.insertIndustryGuidelines(industryGuidelines);
	}

	/**
	 *修改行业指引表
	 * @param industryGuidelines 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateIndustryGuidelines(LoanIndustryGuidelines industryGuidelines,Integer loginUserId){
		industryGuidelines.setUpdateDate(DateUtil.getCurrentDate());
		this.industryGuidelinesDao.updateIndustryGuidelines(industryGuidelines);
	}

	/**
	 * 通过主键删除行业指引表
	 * @param id 主键Id
	 */
	public void deleteIndustryGuidelinesById(Integer id){
		this.industryGuidelinesDao.deleteIndustryGuidelinesById(id);
	}

	/**
	 * 通过主键得到行业指引表
	 * @param id 主键Id
	 */
	public LoanIndustryGuidelines getIndustryGuidelinesById(Integer id){
		return this.industryGuidelinesDao.getIndustryGuidelinesById(id);
	}

	/**
	 * 查询行业指引表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanIndustryGuidelines> queryIndustryGuidelinesList(Map<String,Object> condition){
		return this.industryGuidelinesDao.queryIndustryGuidelinesList(condition);
	}

	/**
	 * 分页查询行业指引表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanIndustryGuidelines> queryIndustryGuidelinesList(Map<String,Object> condition,IPageSize page){
		return this.industryGuidelinesDao.queryIndustryGuidelinesList(condition,page);
	}
//	根据行业一级和行业二级查询行业指引表
	public List<LoanIndustryGuidelines> queryLoanIndustryGuidelinesListByCondition(Map<String, Object> map) {
		return this.industryGuidelinesDao.queryLoanIndustryGuidelinesListByCondition(map);
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:根据行业等级获取行业信息
	 * @Date: 10:50 2017/9/11
	 */
	public List<LoanIndustryGuidelines> getIndustryGuidelinesByGroup(Map<String, Object> map) {
		return this.industryGuidelinesDao.getIndustryGuidelinesByGroup(map);
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:右边模糊查询行业指引项目名称
	 * @Date: 10:28 2017/9/6
	 */
	public List<LoanIndustryGuidelines> queryLoanIndustryGuidelinesListByLike(String str) {
		return this.industryGuidelinesDao.queryLoanIndustryGuidelinesListByLike(str);
	}

	/**
	 * 通过导入的Excel文件得到表头
	 *
	 * @param excelFilename
	 * @return
	 */
	public Map<String, Object> getImportExcelHead(String excelFilename) {
		IImportContext context = new DefaultImportContext(excelFilename);
//		context.setMaxRow(1);
		IndustryImportHandler handler = new IndustryImportHandler(this, null,false);
		handler.setContext(context);
		handler.start();
		List<String> columns = handler.getHead();
		int total = handler.getTotal();
		Map<String,Object> map = new HashedMap();
		map.put("columns", columns);
		map.put("total", total);
		return map;
	}

	/**
	 * @Author: yangdw
	 * @params: userId        用户Id
	 * @params: excelFilename 导入文件
	 * @params: columns       导入列和字段的对应关系
	 * @Description:开始导入行业指引数据
	 * @Date: 15:38 2017/9/7
	 */

	public ProgressBar importExcel(String userId, String excelFilename, List<ColumnInfo> columns) {
		IImportContext context = new DefaultImportContext(excelFilename);
		context.setColumns(columns);
		context.setBatch(200);

		IndustryImportHandler handler = new IndustryImportHandler(this, Integer.parseInt(userId),true);
		handler.setContext(context);
		this.handlerMap.put(userId, handler);

		ProgressBar bar = progressBarManager.add("industryGuideImport_" + userId, userId);
		handler.setProgressBar(bar);
		handler.start();

		return bar;
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到导入结果
	 * @Date: 15:38 2017/9/7
	 */

	public IImportResult getImportResultByUserId(String userId) {
		if (this.handlerMap.containsKey(userId)) {
			return this.handlerMap.get(userId);
		}
		return null;
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:清空行业指引表
	 * @Date: 13:55 2017/9/8
	 */
	public void truncateLoanIndustryGuideLines() {
		industryGuidelinesDao.truncateLoanIndustryGuideLines();
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:行业指引导入处理类
	 * @Date: 13:54 2017/9/8
	 */
	class IndustryImportHandler extends AbstractImportHandler {
		private IIndustryGuidelinesService industryGuidelinesService;
		private List<LoanIndustryGuidelines> list;
		private Integer loginUserId;
		private int batchCount;// 批量提交次数
		private boolean isInsert; //false为计算total
		private int total;

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public IndustryImportHandler(IIndustryGuidelinesService industryGuidelinesService, Integer userId, boolean isInsert) {
			this.industryGuidelinesService = industryGuidelinesService;
			this.list = new ArrayList<LoanIndustryGuidelines>();
			this.loginUserId = userId;
			this.isInsert = isInsert;
		}

		/**
		 * 逐行读取Excel数据
		 */
		@Override
		public void read(IRecordReader reader) {
			if (reader.rownum() > 0) {
				if(isInsert == true){
					List<ColumnInfo> columns = this.getContext().getColumns();
					LoanIndustryGuidelines industry = new LoanIndustryGuidelines();
					for (ColumnInfo column : columns) {
						if (!StringUtil.isNullOrEmpty(column.getFieldName())) {
							String val = (String) reader.getValue(column.getIndex());
							ReflectorUtil.setPropertyValue(industry, column.getFieldName(), val);
						}
					}
					if (this.validImportIndustryGuideData(industry, reader)) {
						list.add(industry);
					}
					this.rowCount++;
				}else{
					this.total++;
				}
			}
			super.read(reader);                //读进度条
		}

		/**
		 * 批量提交
		 */
		public void batchCommit() {
			//int startNum = batchCount++ * this.getContext().getBatch();
			try {
				if (this.list.size() > 0) {
					List<LoanIndustryGuidelines> insertList = new ArrayList<LoanIndustryGuidelines>();
					for (int i = insertCount; i < this.list.size(); i++) {
						LoanIndustryGuidelines industryGuidelines = this.list.get(i);
						industryGuidelines.setCreateDate(Calendar.getInstance().getTime());
						industryGuidelines.setUpdateDate(Calendar.getInstance().getTime());
						if(industryGuidelines.getValue1() != null && industryGuidelines.getValue5()!=null){
							if (industryGuidelines.getValue1().doubleValue() > industryGuidelines.getValue5().doubleValue()) {
								industryGuidelines.setValueType(0);
							}else {
								industryGuidelines.setValueType(1);
							}
						}else{
							industryGuidelines.setValueType(1);
						}
						insertList.add(industryGuidelines);
					}
					industryGuidelinesDao.insertIndustryListByImport(insertList);
					this.insertCount += insertList.size();
					this.successCount = this.insertCount;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ProgressBar bar = progressBarManager.getProgressBarById("industryGuideImport_"+ loginUserId);
				if(bar != null){
					int current = bar.getCurrent();
					logger.error("导入行业指引Excel,第---" + current + "---数据报错");
				}
				logger.error("导入行业指引执行插入报错 error:"+e.getMessage());


			}
		}

		/**
		 * 校验导入用户的数据是否正确
		 *
		 * @param industry
		 * @return
		 */
		private boolean validImportIndustryGuideData(LoanIndustryGuidelines industry, IRecordReader reader) {
			String error = "";
			String importResult = "成功";
			boolean result = true;
			//行业一级（必填）	行业二级（必填）	行业三级（必填）	项目（必填）	优秀值（必填）	良好值（必填）	平均值（必填）	较低值（必填）	较差值（必填）
			if (isNullOrEmpty(industry.getGradeFirst())) {
				error = "行业一级不能为空";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(isNullOrEmpty(industry.getGradeSecond())){
				error = "行业二级不能为空";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if (isNullOrEmpty(industry.getGradeThird())) {
				error = "行业三级不能为空";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			} else if (isNullOrEmpty(industry.getItemName())) {
				error = "项目不能为空";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			} else if (industry.getValue1() == null) {
				error = "优秀值只能是数值";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			} else if (industry.getValue2() == null) {
				error = "良好值只能是数值";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			} else if (industry.getValue3() == null) {
				error = "平均值只能是数值";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			} else if (industry.getValue4() == null) {
				error = "较低值只能是数值";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			} else if (industry.getValue4() == null) {
				error = "较差值只能是数值";
				importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}
			this.writeLineReportFile(reader, importResult, "", error);
			return result;
		}

		private boolean isNullOrEmpty(String str) {
			if (str == null || str.equals("") || "null".equals(str)) {
				return true;
			} else {
				return str.trim().length() == 0;
			}
		}

		private boolean isIncludeSpecialLetter(String str){
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.find();
		}
	}
}
