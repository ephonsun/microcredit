package banger.service.intf;

/**
 * 贷款信息同频接口
 * @author zhusw
 *
 */
public interface ILoanInfoSyncService {

	/**
	 * 同步主表信息到子表
	 * @param loanId
	 */
	void syncLoanToBiz(Integer loanId);

	/**
	 * 同步主表信息到子表
	 * @param loanId
	 */
	void syncLoanToBiz(Integer loanId,Integer marketCustomerId);

	/**
	 * 同步子表信息到贷款主表
	 * @param id
	 * @param tableName
	 * @param processType
	 */
	void syncBizToLoan(Integer id,String tableName,String processType);

	/**
	 * 贷款申请信息同步到贷款调查信息
	 * @param loanId
	 */
	void syncApplyToInvestigate(Integer loanId);

	/**
	 * 贷款申请信息同步到贷款调查信息
	 * @param loanIds
	 */
	void syncApplyToInvestigate(Integer[] loanIds);
	
}
