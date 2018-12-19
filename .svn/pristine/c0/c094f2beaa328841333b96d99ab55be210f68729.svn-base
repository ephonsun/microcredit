package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanIndustryGradeexp;

/**
 * 贷款行业指引等级说明表数据访问接口
 */
public interface IIndustryGradeexpDao {

	/**
	 * 新增贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 */
	void insertIndustryGradeexp(LoanIndustryGradeexp industryGradeexp);

	/**
	 *修改贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 */
	void updateIndustryGradeexp(LoanIndustryGradeexp industryGradeexp);

	/**
	 * 通过主键删除贷款行业指引等级说明表
	 * @param id 主键Id
	 */
	void deleteIndustryGradeexpById(Integer id);

	/**
	 * 通过主键得到贷款行业指引等级说明表
	 * @param id 主键Id
	 */
	LoanIndustryGradeexp getIndustryGradeexpById(Integer id);

	/**
	 * 查询贷款行业指引等级说明表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanIndustryGradeexp> queryIndustryGradeexpList(Map<String, Object> condition);

	/**
	 * 分页查询贷款行业指引等级说明表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanIndustryGradeexp> queryIndustryGradeexpList(Map<String, Object> condition, IPageSize page);

	/**
	 * @Author: yangdw
	 * @param loanId
	 * @Description:通过loanId删除款行业指引等级说明表
	 * @Date: 10:00 2017/9/25
	 */
	void deleteIndustryGradeexpByLoanId(Integer loanId);
}
