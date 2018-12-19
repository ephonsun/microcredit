package banger.dao.impl;

import banger.dao.intf.IContractRelateItemDao;
import banger.domain.loan.contract.LoanContractRelateItem;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class ContractRelateItemDao extends PageSizeDao<LoanContractRelateItem> implements IContractRelateItemDao {

	/**
	 * 新增
	 * @param contractRelateItem 实体对像
	 */
	public void insertContractRelateItem(LoanContractRelateItem contractRelateItem){
		contractRelateItem.setId(this.newId().intValue());
		this.execute("insertContractRelateItem",contractRelateItem);
	}

	/**
	 *修改
	 * @param contractRelateItem 实体对像
	 */
	public void updateContractRelateItem(LoanContractRelateItem contractRelateItem){
		this.execute("updateContractRelateItem",contractRelateItem);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteContractRelateItemById(Integer id){
		this.execute("deleteContractRelateItemById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public LoanContractRelateItem getContractRelateItemById(Integer id){
		return (LoanContractRelateItem)this.queryEntity("getContractRelateItemById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanContractRelateItem> queryContractRelateItemList(Map<String,Object> condition){
		return (List<LoanContractRelateItem>)this.queryEntities("queryContractRelateItemList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanContractRelateItem> queryContractRelateItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanContractRelateItem>)this.queryEntities("queryContractRelateItemList", page, condition);
	}

	@Override
	public void deleteContractRelateItemByLoanTypeId(Integer loanTypeId) {
		this.execute("deleteContractRelateItemByLoanTypeId",loanTypeId);
	}

}
