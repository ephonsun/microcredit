package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsFixedItem;

/**
 * 资产负债固定资产项明细业务访问接口
 */
public interface IAssetsFixedItemService {

	/**
	 * 新增资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem, Integer loginUserId);

	/**
	 *修改资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem, Integer loginUserId);

	/**
	 * 通过主键删除资产负债固定资产项明细
	 * @param ID 主键Id
	 */
	void deleteAssetsFixedItemById(Integer id);

	/**
	 * 通过主键得到资产负债固定资产项明细
	 * @param ID 主键Id
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
