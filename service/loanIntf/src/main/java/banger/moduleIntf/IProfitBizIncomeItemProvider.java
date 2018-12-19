package banger.moduleIntf;

import banger.domain.loan.LoanProfitBizIncomeItem;
import banger.domain.loan.LoanProfitBizIncomeMonth;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangdw
 * @Description:贷款经营类收入支出明细模块对外接口
 * @Date: Created in 18:54 2017/8/23.
 */
public interface IProfitBizIncomeItemProvider {

	List<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String, Object> map);

	void insertProfitBizIncomeItem(LoanProfitBizIncomeItem loanProfitBizIncomeItem, Integer createUser);

	void deleteProfitBizIncomeItemById(Integer id);

	void updateProfitBizIncomeItem(LoanProfitBizIncomeItem loanProfitBizIncomeItem, Integer userId);

	LoanProfitBizIncomeItem getProfitBizIncomeItemById(Integer itmeId);

	void saveProfitBizIncomeItem(LoanProfitBizIncomeItem loanProfitBizIncomeItem, List<LoanProfitBizIncomeMonth> monthList, int userId);
}
