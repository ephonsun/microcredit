package banger.dao.intf;

import banger.domain.loan.contract.LoanContractRelateItem;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IContractRelateItemDao {

	/**
	 * 新增
	 * @param contractRelateItem 实体对像
	 */
	void insertContractRelateItem(LoanContractRelateItem contractRelateItem);

	/**
	 *修改
	 * @param contractRelateItem 实体对像
	 */
	void updateContractRelateItem(LoanContractRelateItem contractRelateItem);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteContractRelateItemById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	LoanContractRelateItem getContractRelateItemById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractRelateItem> queryContractRelateItemList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractRelateItem> queryContractRelateItemList(Map<String, Object> condition, IPageSize page);

	void deleteContractRelateItemByLoanTypeId(Integer loanTypeId);

}
