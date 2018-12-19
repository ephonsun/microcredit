package banger.dao.impl;

import banger.dao.intf.ITmpLoanRepaymentItemDao;
import banger.domain.loan.tmp.TmpLoanRepaymentItem;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款记录数据访问类
 */
@Repository
public class TmpLoanRepaymentItemDao extends PageSizeDao<TmpLoanRepaymentItem> implements ITmpLoanRepaymentItemDao {



	/**
	 * 查询还款记录
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TmpLoanRepaymentItem> queryLoanRepaymentItemList(Map<String,Object> condition){
		return (List<TmpLoanRepaymentItem>)this.queryEntities("queryLoanRepaymentItemList", condition);
	}

	/**
	 * 查询还款记录
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal sumPaymentAmountByAccount(String loanAccount){
		return (BigDecimal)this.queryValue("sumPaymentAmountByAccount", loanAccount);
	}




}
