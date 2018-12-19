package banger.dao.intf;

import banger.domain.loan.tmp.TmpLoanAccount;

import java.util.List;
import java.util.Map;

/**
 * 贷款账户信息接口数据访问接口
 */
public interface ITmpLoanAccountDao {



	/**
	 * 查询贷款账户信息接口
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanAccount> queryTmpLoanAccountList(Map<String, Object> condition);


	@SuppressWarnings("unchecked")
	TmpLoanAccount getTmpLoanAccountByLoanAccount(String loanAccount);
}
