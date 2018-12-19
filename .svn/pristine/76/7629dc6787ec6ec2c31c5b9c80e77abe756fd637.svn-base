package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsDebtsItem;

/**
 * 资产负债项明细业务访问接口
 */
public interface IAssetsDebtsItemService {

	/**
	 * 新增资产负债项明细
	 * @param assetsDebtsItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem, Integer loginUserId);

	/**
	 *修改资产负债项明细
	 * @param assetsDebtsItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem, Integer loginUserId);

	/**
	 * 通过主键删除资产负债项明细
	 * @param ID 主键Id
	 */
	void deleteAssetsDebtsItemById(Integer id);

	/**
	 * 通过主键得到资产负债项明细
	 * @param ID 主键Id
	 */
	LoanAssetsDebtsItem getAssetsDebtsItemById(Integer id);

	/**
	 * 查询资产负债项明细
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsDebtsItem> queryAssetsDebtsItemList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsDebtsItem> queryAssetsDebtsItemList(Map<String, Object> condition, IPageSize page);

}
