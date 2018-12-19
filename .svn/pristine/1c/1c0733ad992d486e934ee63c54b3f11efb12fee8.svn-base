package banger.dao.impl;

import banger.dao.intf.ITmpLoanRepaymentPlanDao;
import banger.domain.loan.tmp.TmpLoanRepaymentPlan;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 还款计划(接口同步临时表)数据访问类
 */
@Repository
public class TmpLoanRepaymentPlanDao extends PageSizeDao<TmpLoanRepaymentPlan> implements ITmpLoanRepaymentPlanDao {



	/**
	 * 查询还款计划(接口同步临时表)
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TmpLoanRepaymentPlan> queryLoanRepaymentPlanList(Map<String,Object> condition){
		return (List<TmpLoanRepaymentPlan>)this.queryEntities("queryLoanRepaymentPlanList", condition);
	}

	/**
	 * 查询还款计划(接口同步临时表)
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TmpLoanRepaymentPlan> queryRepaymentPlanByLoanAccount(String loanAccount){
		return (List<TmpLoanRepaymentPlan>)this.queryEntities("queryRepaymentPlanByLoanAccount", loanAccount);
	}


}
