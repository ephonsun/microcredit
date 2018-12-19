package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsStockItem;

/**
 * 资产负债存货项明细业务访问接口
 */
public interface IAssetsStockItemService {

	/**
	 * 新增资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAssetsStockItem(LoanAssetsStockItem assetsStockItem, Integer loginUserId);

	/**
	 *修改资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAssetsStockItem(LoanAssetsStockItem assetsStockItem, Integer loginUserId);

	/**
	 * 通过主键删除资产负债存货项明细
	 * @param ID 主键Id
	 */
	void deleteAssetsStockItemById(Integer id);

	/**
	 * 通过主键得到资产负债存货项明细
	 * @param ID 主键Id
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
