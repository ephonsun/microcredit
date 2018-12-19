package banger.dao.intf;

import banger.domain.loan.tmp.TmpLoanRepaymentPlan;

import java.util.List;
import java.util.Map;

/**
 * 还款计划(接口同步临时表)数据访问接口
 */
public interface ITmpLoanRepaymentPlanDao {


	/**
	 * 查询还款计划(接口同步临时表)
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanRepaymentPlan> queryLoanRepaymentPlanList(Map<String, Object> condition);


	@SuppressWarnings("unchecked")
	List<TmpLoanRepaymentPlan> queryRepaymentPlanByLoanAccount(String loanAccount);
}
