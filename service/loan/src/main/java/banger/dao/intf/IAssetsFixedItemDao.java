package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsFixedItem;

/**
 * 资产负债固定资产项明细数据访问接口
 */
public interface IAssetsFixedItemDao {

	/**
	 * 新增资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 */
	void insertAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem);

	/**
	 *修改资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 */
	void updateAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem);

	/**
	 * 通过主键删除资产负债固定资产项明细
	 * @param id 主键Id
	 */
	void deleteAssetsFixedItemById(Integer id);

	/**
	 * 通过主键得到资产负债固定资产项明细
	 * @param id 主键Id
	 */
	LoanAssetsFixedItem getAssetsFixedItemById(Integer id);

	/**
	 * 查询资产负债固定资产项明细
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsFixedItem> queryAssetsFixedItemList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债固定资产项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsFixedItem> queryAssetsFixedItemList(Map<String, Object> condition, IPageSize page);

}
