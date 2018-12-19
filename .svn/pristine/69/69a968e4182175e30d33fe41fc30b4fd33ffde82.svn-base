package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitBizIncomeItem;

/**
 * 损益情况经营类收入支出明细表数据访问接口
 */
public interface IProfitBizIncomeItemDao {

	/**
	 * 新增损益情况经营类收入支出明细表
	 * @param profitBizIncomeItem 实体对像
	 */
	void insertProfitBizIncomeItem(LoanProfitBizIncomeItem profitBizIncomeItem);

	/**
	 *修改损益情况经营类收入支出明细表
	 * @param profitBizIncomeItem 实体对像
	 */
	void updateProfitBizIncomeItem(LoanProfitBizIncomeItem profitBizIncomeItem);

	/**
	 * 通过主键删除损益情况经营类收入支出明细表
	 * @param id 主键Id
	 */
	void deleteProfitBizIncomeItemById(Integer id);

	/**
	 * 通过主键得到损益情况经营类收入支出明细表
	 * @param id 主键Id
	 */
	LoanProfitBizIncomeItem getProfitBizIncomeItemById(Integer id);

	/**
	 * 查询损益情况经营类收入支出明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String, Object> condition);

	/**
	 * 分页查询损益情况经营类收入支出明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String, Object> condition, IPageSize page);

}
