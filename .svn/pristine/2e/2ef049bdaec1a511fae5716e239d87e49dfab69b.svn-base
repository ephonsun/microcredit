package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.ProgressBar;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanIndustryGuidelines;

/**
 * 行业指引表业务访问接口
 */
public interface IIndustryGuidelinesService {

	/**
	 * 新增行业指引表
	 * @param industryGuidelines 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertIndustryGuidelines(LoanIndustryGuidelines industryGuidelines, Integer loginUserId);

	/**
	 *修改行业指引表
	 * @param industryGuidelines 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateIndustryGuidelines(LoanIndustryGuidelines industryGuidelines, Integer loginUserId);

	/**
	 * 通过主键删除行业指引表
	 * @param id 主键Id
	 */
	void deleteIndustryGuidelinesById(Integer id);

	/**
	 * 通过主键得到行业指引表
	 * @param id 主键Id
	 */
	LoanIndustryGuidelines getIndustryGuidelinesById(Integer id);

	/**
	 * 查询行业指引表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanIndustryGuidelines> queryIndustryGuidelinesList(Map<String, Object> condition);

	/**
	 * 分页查询行业指引表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanIndustryGuidelines> queryIndustryGuidelinesList(Map<String, Object> condition, IPageSize page);

	List<LoanIndustryGuidelines> queryLoanIndustryGuidelinesListByCondition(Map<String, Object> map);
	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:右边模糊查询行业指引项目名称
	 * @Date: 10:28 2017/9/6
	 */

	List<LoanIndustryGuidelines> queryLoanIndustryGuidelinesListByLike(String str);

	/**
	 * @Author: yangdw
	 * @params: * @param null
	 * @Description:通过导入的Excel文件得到表头
	 * @Date: 13:58 2017/9/7
	 */
	public Map<String, Object> getImportExcelHead(String excelFilename);

	ProgressBar importExcel(String userId, String saveFilename, List<ColumnInfo> columns);

	IImportResult getImportResultByUserId(String userId);

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:清空行业指引表
	 * @Date: 13:53 2017/9/8
	 */
	void truncateLoanIndustryGuideLines();

	List<LoanIndustryGuidelines> getIndustryGuidelinesByGroup(Map<String, Object> map);
}