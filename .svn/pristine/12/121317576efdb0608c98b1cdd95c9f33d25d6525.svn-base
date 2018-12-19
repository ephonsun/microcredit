package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitLossRatioItem;

/**
 * 损益情况毛利率明细表业务访问接口
 */
public interface IProfitLossRatioItemService {

	/**
	 * 新增损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem, Integer loginUserId);

	/**
	 *修改损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem, Integer loginUserId);

	/**
	 * 通过主键删除损益情况毛利率明细表
	 * @param ID 主键Id
	 */
	void deleteProfitLossRatioItemById(Integer id);

	/**
	 * 通过主键得到损益情况毛利率明细表
	 * @param ID 主键Id
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

}
