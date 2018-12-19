package banger.moduleIntf;

import banger.domain.customer.CustCustomerCreditCheck;
import banger.domain.enumerate.SocketCodeTypeEnum;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

public interface ILoanModule {

	/**
	 * 贷款申请
	 * @return
	 */
	ILoanApplyProvider getLoanApplyProvider();

	/**
	 * 审批
	 * @return
	 */
	ILoanApprovalProvider getLoanApprovalProvider();
	
	/**
	 * 返回贷款附件服务
	 * @return
	 */
	ILoanAddedProvider getLoanAddedProvider();

	/**
	 * 得到贷款历史
	 * @return
	 */
	ILoanHistoryProvider getLoanHistoryProvider();

	/**
	 * 贷款审批
	 * @return
	 */
	ILoanAuditProvider getLoanAuditProvider();


	/**
	 * 资产分析
	 * @return
	 */
	IAssetsInfoProvider getAssetsInfoProvider();


	/**
	 * 财务分析公用Service【对象设值】
	 * @return
	 */
	ILoanFinanceAnalysisService getLoanFinanceAnalysisService();

	/**
	 * 根据贷款id、贷款阶段查询所有贷款申请人、共同借款人、担保人
	 * @param loanId
	 * @param loanProcessType
	 * @return
	 */
	List<CustCustomerCreditCheck> getAllLoanPersonByLoanId(Integer loanId,String loanProcessType);

	/**
	 * 查询客户信息的贷款信息tab页列表
	 * @param condition
	 * @param page
	 * @return
	 */
	IPageList<LoanApplyInfo_Web_Query> queryBasicInfoListForCredit(Map<String,Object> condition, IPageSize page);


	String getContractCode(Integer operationCode,boolean type);

	String getAuthorizedSerialCode(boolean type);

	String syncCustomerInfo(Integer loanId);

	String syncGuaranteeInfo(Integer loanId);

	String syncMortgageInfo(Integer loanId);

	String syncPledgeInfo(Integer loanId);

	String syncContractInfo(Integer loanId);

	String syncLoanAuthorizedInfo(Integer loanId);

	String cancelLoanAccountInfo(Integer loanId);

	Map<String, Object> selectLoanAccountInfo(Integer loanId);

	String queryCusInfo(Integer loanId,Map map,SocketCodeTypeEnum socketCodeTypeEnum);

	DataTable getPledgeDataTableByLoanId(String presType, Integer loanId, Integer mortgageOrPledge);

	String relatedDataQuery(Integer loanId,Map<String,String> map,SocketCodeTypeEnum socketCodeTypeEnum);

	// 更新调查建议表单 LBIZ_SURVEY_RESULT
	void updateSurveyDataTableById(String loanId, Map<String,String> map);

}
