package banger.dao.impl;

import banger.dao.intf.ITmpLoanAccountDao;
import banger.domain.loan.tmp.TmpLoanAccount;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 贷款账户信息接口数据访问类
 */
@Repository
public class TmpLoanAccountDao extends PageSizeDao<TmpLoanAccount> implements ITmpLoanAccountDao {


	/**
	 * 查询贷款账户信息接口
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TmpLoanAccount> queryTmpLoanAccountList(Map<String,Object> condition){
		return (List<TmpLoanAccount>)this.queryEntities("queryTmpLoanAccountList", condition);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TmpLoanAccount getTmpLoanAccountByLoanAccount(String loanAccount){
		return (TmpLoanAccount)this.queryEntity("getTmpLoanAccountByLoanAccount", loanAccount);
	}


}
