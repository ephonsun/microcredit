package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsAccountItem;

/**
 * 资产负债账款项明细业务访问接口
 */
public interface IAssetsAccountItemService {

	/**
	 * 新增资产负债账款项明细
	 * @param assetsAccountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAssetsAccountItem(LoanAssetsAccountItem assetsAccountItem, Integer loginUserId);

	/**
	 *修改资产负债账款项明细
	 * @param assetsAccountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAssetsAccountItem(LoanAssetsAccountItem assetsAccountItem, Integer loginUserId);

	/**
	 * 通过主键删除资产负债账款项明细
	 * @param ID 主键Id
	 */
	void deleteAssetsAccountItemById(Integer id);

	/**
	 * 通过主键得到资产负债账款项明细
	 * @param ID 主键Id
	 */
	LoanAssetsAccountItem getAssetsAccountItemById(Integer id);

	/**
	 * 查询资产负债账款项明细
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsAccountItem> queryAssetsAccountItemList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债账款项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsAccountItem> queryAssetsAccountItemList(Map<String, Object> condition, IPageSize page);

}
