package banger.moduleIntf;

/**
 * @Author: yangdw
 * @Description: 贷款行业指引等级说明表业务访问外部接口
 * @Date: Created in 11:29 2017/9/25.
 */
public interface IIndustryGradeexpProvider {

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description: 根据loanId更新或者新增贷款行业指引等级说明
	 * @Date: 9:19 2017/9/25
	 */
	void saveIndustryGradeexpByLoanId(Integer loanId);
}
