package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitLossRatioItem;

/**
 * 损益情况毛利率明细表数据访问接口
 */
public interface IProfitLossRatioItemDao {

	/**
	 * 新增损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 */
	void insertProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem);

	/**
	 *修改损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 */
	void updateProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem);

	/**
	 * 通过主键删除损益情况毛利率明细表
	 * @param id 主键Id
	 */
	void deleteProfitLossRatioItemById(Integer id);

	/**
	 * 通过主键得到损益情况毛利率明细表
	 * @param id 主键Id
	 */
	LoanProfitLossRatioItem getProfitLossRatioItemById(Integer id);


	/**
	 * 查询损益情况毛利率明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanProfitLossRatioItem> queryProfitLossRatioItemList(Map<String, Object> condition);

	/**
	 * 分页查询损益情况毛利率明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanProfitLossRatioItem> queryProfitLossRatioItemList(Map<String, Object> condition, IPageSize page);

	/**
	 * 根据loanId和列名查询毛利率明细表
	 * @param loanId
	 * @param columnName
	 * @return
	 */
	List<LoanProfitLossRatioItem> queryGrossProfitMarginList(Integer loanId,String columnName);
}
