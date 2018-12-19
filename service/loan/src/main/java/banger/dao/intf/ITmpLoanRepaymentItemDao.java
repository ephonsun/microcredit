package banger.dao.intf;

import banger.domain.loan.tmp.TmpLoanRepaymentItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 还款记录数据访问接口
 */
public interface ITmpLoanRepaymentItemDao {


	/**
	 * 查询还款记录
	 * @param condition 查询条件
	 * @return
	 */
	List<TmpLoanRepaymentItem> queryLoanRepaymentItemList(Map<String, Object> condition);


	@SuppressWarnings("unchecked")
	BigDecimal sumPaymentAmountByAccount(String loanAccount);
}
