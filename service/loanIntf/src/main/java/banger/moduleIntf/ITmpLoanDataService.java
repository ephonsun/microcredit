package banger.moduleIntf;

import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.tmp.TmpLoanAccount;
import banger.domain.loan.tmp.TmpLoanInfo;
import banger.domain.loan.tmp.TmpLoanRepaymentItem;
import banger.domain.loan.tmp.TmpLoanRepaymentPlan;
import banger.framework.collection.DataTable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 贷款账户信息接口业务访问接口
 */
public interface ITmpLoanDataService {


	DataTable getDataTableByLoanId(String tableName, String presType, Integer id);

	/**
	 * 查询贷款账户信息接口
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanAccount> queryTmpLoanAccountList(Map<String, Object> condition);


	TmpLoanAccount getTmpLoanAccountByLoanAccount(String loanAccount);


	/**
	 * 查询贷款信息核实接口
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanInfo> queryTmpLoanInfoList(Map<String, Object> condition);

	@SuppressWarnings("unchecked")
	TmpLoanInfo getTmpLoanInfoByLoanAccount(String loanAccount);

	@SuppressWarnings("unchecked")
	TmpLoanInfo getTmpLoanInfoByContractNo(String contractNo);

	/**
	 * 查询还款记录
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanRepaymentItem> queryTmpRepaymentItemList(Map<String, Object> condition);

	/**
	 * 查询还款记录
	 * @param loanAccount 查询条件
	 * @return
	 */
	BigDecimal sumPaymentAmountByAccount(String loanAccount);

	/**
	 * 查询还款计划(接口同步临时表)
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanRepaymentPlan> queryTmpRepaymentPlanList(Map<String, Object> condition);


	List<TmpLoanRepaymentPlan> queryTmpRepaymentPlanByLoanAccount(String loanAccount);



	//=================================================================================
	List<LoanApplyInfo> queryApplyInfoList(Map<String, Object> condition);

	void updateApplyInfo(LoanApplyInfo loan);

	LoanApplyInfo getApplyInfoByLoanAccount(String loanAccount);

	void insertRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo);

	void updateRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo);

	LoanRepayPlanInfo getTopLoanRepayPlanInfo(Integer loanId);

	List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId, String type);

	DataTable getApprovalDataTableByLoanId(Integer loanId);

//	IApplyInfoDao getApplyInfoDao();
//
//	IRepayPlanInfoDao getRepayPlanInfoDao();
}
