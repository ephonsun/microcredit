package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanIndustryGradeexp;

/**
 * 贷款行业指引等级说明表业务访问接口
 */
public interface IIndustryGradeexpService {

	/**
	 * 新增贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertIndustryGradeexp(LoanIndustryGradeexp industryGradeexp, Integer loginUserId);

	/**
	 *修改贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateIndustryGradeexp(LoanIndustryGradeexp industryGradeexp, Integer loginUserId);

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
	 * @param loanId 贷款id
	 * @Description: 根据loanId更新或者新增贷款行业指引等级说明
	 * @Date: 9:19 2017/9/25
	 */
	void saveIndustryGradeexpByLoanId(Integer loanId);
}
