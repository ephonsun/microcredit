package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitConsumIncomeItem;

/**
 * 损益情况消费类收入支出明细表数据访问接口
 */
public interface IProfitConsumIncomeItemDao {

	/**
	 * 新增损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 */
	void insertProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem);

	/**
	 *修改损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 */
	void updateProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem);

	/**
	 * 通过主键删除损益情况消费类收入支出明细表
	 * @param id 主键Id
	 */
	void deleteProfitConsumIncomeItemById(Integer id);

	/**
	 * 通过主键得到损益情况消费类收入支出明细表
	 * @param id 主键Id
	 */
	LoanProfitConsumIncomeItem getProfitConsumIncomeItemById(Integer id);

	/**
	 * 查询损益情况消费类收入支出明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String, Object> condition);

	/**
	 * 分页查询损益情况消费类收入支出明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String, Object> condition, IPageSize page);

}
