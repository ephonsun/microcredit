package banger.dao.intf;

import banger.domain.loan.tmp.TmpLoanInfo;

import java.util.List;
import java.util.Map;

/**
 * 贷款信息核实接口数据访问接口
 */
public interface ITmpLoanInfoDao {

	/**
	 * 查询贷款信息核实接口
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanInfo> queryLoanInfoList(Map<String, Object> condition);

	@SuppressWarnings("unchecked")
	TmpLoanInfo getTmpLoanInfoByLoanAccount(String loanAccount);

	@SuppressWarnings("unchecked")
	TmpLoanInfo getTmpLoanInfoByContractNo(String contractNo);
}
