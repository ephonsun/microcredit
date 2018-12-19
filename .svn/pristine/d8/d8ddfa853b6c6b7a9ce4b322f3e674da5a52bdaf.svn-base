package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsAmountItem;

/**
 * 资产负债金额项目明细表业务访问接口
 */
public interface IAssetsAmountItemService {

	/**
	 * 新增资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem, Integer loginUserId);

	/**
	 *修改资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem, Integer loginUserId);

	/**
	 * 通过主键删除资产负债金额项目明细表
	 * @param ID 主键Id
	 */
	void deleteAssetsAmountItemById(Integer id);

	/**
	 * 通过主键得到资产负债金额项目明细表
	 * @param ID 主键Id
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

}
