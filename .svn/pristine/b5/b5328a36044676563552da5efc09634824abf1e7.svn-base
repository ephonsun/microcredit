package banger.moduleImpl;

import banger.dao.intf.*;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.tmp.TmpLoanAccount;
import banger.domain.loan.tmp.TmpLoanInfo;
import banger.domain.loan.tmp.TmpLoanRepaymentItem;
import banger.domain.loan.tmp.TmpLoanRepaymentPlan;
import banger.framework.collection.DataTable;
import banger.framework.spring.SpringContext;
import banger.moduleIntf.ITmpLoanDataService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款账户同步接口业务访问类
 */
public class TmpLoanDataService implements ITmpLoanDataService {

	public ITmpLoanAccountDao getTmpLoanAccountDao(){
		return (ITmpLoanAccountDao) SpringContext.instance().get("tmpLoanAccountDao");
	}

	public ITmpLoanInfoDao getTmpLoanInfoDao(){
		return (ITmpLoanInfoDao) SpringContext.instance().get("tmpLoanInfoDao");
	}

	public ITmpLoanRepaymentItemDao getTmpLoanRepaymentItemDao(){
		return (ITmpLoanRepaymentItemDao) SpringContext.instance().get("tmpLoanRepaymentItemDao");
	}

	public ITmpLoanRepaymentPlanDao getTmpLoanRepaymentPlanDao(){
		return (ITmpLoanRepaymentPlanDao) SpringContext.instance().get("tmpLoanRepaymentPlanDao");
	}

	public IRepayPlanInfoDao getRepayPlanInfoDao(){
		return (IRepayPlanInfoDao) SpringContext.instance().get("repayPlanInfoDao");
	}

	public IApplyInfoDao getApplyInfoDao(){
		return (IApplyInfoDao) SpringContext.instance().get("applyInfoDao");
	}



	@Override
	public DataTable getDataTableByLoanId(String tableName, String presType, Integer id){
		return getApplyInfoDao().getDataTableByLoanId(tableName, presType, id);
	}

	/**
	 * 查询贷款账户信息接口
	 * @param condition 查询条件
	 * @return
	 */
	public List<TmpLoanAccount> queryTmpLoanAccountList(Map<String,Object> condition){
		return this.getTmpLoanAccountDao().queryTmpLoanAccountList(condition);
	}

	@Override
	public TmpLoanAccount getTmpLoanAccountByLoanAccount(String loanAccount) {
		return getTmpLoanAccountDao().getTmpLoanAccountByLoanAccount(loanAccount);
	}

	@Override
	public List<TmpLoanInfo> queryTmpLoanInfoList(Map<String, Object> condition) {
		return getTmpLoanInfoDao().queryLoanInfoList(condition);
	}

	@Override
	public TmpLoanInfo getTmpLoanInfoByLoanAccount(String loanAccount) {
		return getTmpLoanInfoDao().getTmpLoanInfoByLoanAccount(loanAccount);
	}

	@Override
	public TmpLoanInfo getTmpLoanInfoByContractNo(String contractNo) {
		return getTmpLoanInfoDao().getTmpLoanInfoByContractNo(contractNo);
	}

	@Override
	public List<TmpLoanRepaymentItem> queryTmpRepaymentItemList(Map<String, Object> condition) {
		return getTmpLoanRepaymentItemDao().queryLoanRepaymentItemList(condition);
	}

	@Override
	public BigDecimal sumPaymentAmountByAccount(String loanAccount) {
		return getTmpLoanRepaymentItemDao().sumPaymentAmountByAccount(loanAccount);
	}

	@Override
	public List<TmpLoanRepaymentPlan> queryTmpRepaymentPlanList(Map<String, Object> condition) {
		return getTmpLoanRepaymentPlanDao().queryLoanRepaymentPlanList(condition);
	}

	@Override
	public List<TmpLoanRepaymentPlan> queryTmpRepaymentPlanByLoanAccount(String loanAccount) {
		return getTmpLoanRepaymentPlanDao().queryRepaymentPlanByLoanAccount(loanAccount);
	}

	@Override
	public List<LoanApplyInfo> queryApplyInfoList(Map<String, Object> condition) {
		return getApplyInfoDao().queryApplyInfoList(condition);
	}

	@Override
	public void updateApplyInfo(LoanApplyInfo loan) {
		getApplyInfoDao().updateApplyInfo(loan);
	}

	@Override
	public LoanApplyInfo getApplyInfoByLoanAccount(String loanAccount) {
		return getApplyInfoDao().getApplyInfoByLoanAccount(loanAccount);
	}


//	public IRepayPlanInfoDao getRepayPlanInfoDao(){
//		return (IRepayPlanInfoDao) SpringContext.instance().get("repayPlanInfoDao");
//	}

	/**
	 * 新增贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 */
	@Override
	public void insertRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo){
		getRepayPlanInfoDao().insertRepayPlanInfo(repayPlanInfo);
	}

	/**
	 *修改贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 */
	@Override
	public void updateRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo){
		getRepayPlanInfoDao().updateRepayPlanInfo(repayPlanInfo);
	}


	/**
	 * 得到第一批还款计录
	 * @param loanId
	 * @return
	 */
	@Override
	public LoanRepayPlanInfo getTopLoanRepayPlanInfo(Integer loanId){
		return getRepayPlanInfoDao().getTopLoanRepayPlanInfo(loanId);
	}

	@Override
	public List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId, String processType) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("loanId",loanId);
		condition.put("loanProcessType",processType);
		return getRepayPlanInfoDao().queryRepayPlanInfoList(condition);
	}

	@Override
	public DataTable getApprovalDataTableByLoanId(Integer loanId) {
		return getApplyInfoDao().getApprovalDataTableByLoanId(loanId);
	}


}
