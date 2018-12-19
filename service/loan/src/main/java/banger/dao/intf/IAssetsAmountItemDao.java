package banger.dao.intf;

import banger.domain.loan.LoanAssetsAmountItem;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 资产负债金额项目明细表数据访问接口
 */
public interface IAssetsAmountItemDao {

	/**
	 * 新增资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 */
	void insertAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem);

	/**
	 *修改资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 */
	void updateAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem);

	/**
	 * 通过主键删除资产负债金额项目明细表
	 * @param id 主键Id
	 */
	void deleteAssetsAmountItemById(Integer id);

	/**
	 * 通过主键得到资产负债金额项目明细表
	 * @param id 主键Id
	 */
	LoanAssetsAmountItem getAssetsAmountItemById(Integer id);

	/**
	 * 查询资产负债金额项目明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsAmountItem> queryAssetsAmountItemList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债金额项目明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsAmountItem> queryAssetsAmountItemList(Map<String, Object> condition, IPageSize page);


	List<LoanAssetsAmountItem> getAssetsItemListByColumnName(Integer loanId, String columnName, String tableName);

}
