package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitConsumIncomeItem;

/**
 * 损益情况消费类收入支出明细表业务访问接口
 */
public interface IProfitConsumIncomeItemService {

	/**
	 * 新增损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem, Integer loginUserId);

	/**
	 *修改损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem, Integer loginUserId);

	/**
	 * 通过主键删除损益情况消费类收入支出明细表
	 * @param ID 主键Id
	 */
	void deleteProfitConsumIncomeItemById(Integer id);

	/**
	 * 通过主键得到损益情况消费类收入支出明细表
	 * @param ID 主键Id
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
