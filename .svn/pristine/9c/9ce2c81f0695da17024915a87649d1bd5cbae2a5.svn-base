package banger.moduleIntf;

import banger.domain.loan.LoanProfitConsumIncomeItem;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangdw
 * @Description:贷款消费类收入支出明细模块对外接口
 * @Date: Created in 14:08 2017/8/24.
 */
public interface IProfitConsumIncomeItemProvider {

	List<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String, Object> condition);

	void insertProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem, Integer loginUserId);

	void updateProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem, Integer loginUserId);

	/**
	 * 通过主键删除损益情况消费类收入支出明细表
	 * @param ID 主键Id
	 */
	void deleteProfitConsumIncomeItemById(Integer id);

	LoanProfitConsumIncomeItem getProfitConsumIncomeItemById(Integer id);
}
