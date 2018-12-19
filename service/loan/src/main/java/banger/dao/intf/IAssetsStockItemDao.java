package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsStockItem;

/**
 * 资产负债存货项明细数据访问接口
 */
public interface IAssetsStockItemDao {

	/**
	 * 新增资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 */
	void insertAssetsStockItem(LoanAssetsStockItem assetsStockItem);

	/**
	 *修改资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 */
	void updateAssetsStockItem(LoanAssetsStockItem assetsStockItem);

	/**
	 * 通过主键删除资产负债存货项明细
	 * @param id 主键Id
	 */
	void deleteAssetsStockItemById(Integer id);

	/**
	 * 通过主键得到资产负债存货项明细
	 * @param id 主键Id
	 */
	LoanAssetsStockItem getAssetsStockItemById(Integer id);

	/**
	 * 查询资产负债存货项明细
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsStockItem> queryAssetsStockItemList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债存货项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsStockItem> queryAssetsStockItemList(Map<String, Object> condition, IPageSize page);

}
