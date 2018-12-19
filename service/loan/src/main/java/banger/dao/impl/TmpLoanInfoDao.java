package banger.dao.impl;

import banger.dao.intf.ITmpLoanInfoDao;
import banger.domain.loan.tmp.TmpLoanInfo;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 贷款信息核实接口数据访问类
 */
@Repository
public class TmpLoanInfoDao extends PageSizeDao<TmpLoanInfo> implements ITmpLoanInfoDao {


	/**
	 * 查询贷款信息核实接口
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TmpLoanInfo> queryLoanInfoList(Map<String,Object> condition){
		return (List<TmpLoanInfo>)this.queryEntities("queryLoanInfoList", condition);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TmpLoanInfo getTmpLoanInfoByLoanAccount(String loanAccount){
		return (TmpLoanInfo)this.queryEntity("getTmpLoanInfoByLoanAccount", loanAccount);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TmpLoanInfo getTmpLoanInfoByContractNo(String contractNo){
		return (TmpLoanInfo)this.queryEntity("getTmpLoanInfoByContractNo", contractNo);
	}


}
