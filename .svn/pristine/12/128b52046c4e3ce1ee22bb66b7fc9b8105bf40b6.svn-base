package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanIndustryGuidelines;

/**
 * 行业指引表数据访问接口
 */
public interface IIndustryGuidelinesDao {

	/**
	 * 新增行业指引表
	 * @param industryGuidelines 实体对像
	 */
	void insertIndustryGuidelines(LoanIndustryGuidelines industryGuidelines);

	/**
	 *修改行业指引表
	 * @param industryGuidelines 实体对像
	 */
	void updateIndustryGuidelines(LoanIndustryGuidelines industryGuidelines);

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

	void insertIndustryListByImport(List<LoanIndustryGuidelines> insertList);

	void truncateLoanIndustryGuideLines();

	List<LoanIndustryGuidelines> getIndustryGuidelinesByGroup(Map<String, Object> map);
}
