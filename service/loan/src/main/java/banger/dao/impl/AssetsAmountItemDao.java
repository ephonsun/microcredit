package banger.dao.impl;

import banger.dao.intf.IAssetsAmountItemDao;
import banger.domain.loan.LoanAssetsAmountItem;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产负债金额项目明细表数据访问类
 */
@Repository
public class AssetsAmountItemDao extends PageSizeDao<LoanAssetsAmountItem> implements IAssetsAmountItemDao {

	/**
	 * 新增资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 */
	public void insertAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem){
		assetsAmountItem.setId(this.newId().intValue());
		this.execute("insertAssetsAmountItem",assetsAmountItem);
	}

	/**
	 *修改资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 */
	public void updateAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem){
		this.execute("updateAssetsAmountItem",assetsAmountItem);
	}

	/**
	 * 通过主键删除资产负债金额项目明细表
	 * @param id 主键Id
	 */
	public void deleteAssetsAmountItemById(Integer id){
		this.execute("deleteAssetsAmountItemById",id);
	}

	/**
	 * 通过主键得到资产负债金额项目明细表
	 * @param id 主键Id
	 */
	public LoanAssetsAmountItem getAssetsAmountItemById(Integer id){
		return (LoanAssetsAmountItem)this.queryEntity("getAssetsAmountItemById",id);
	}

	/**
	 * 查询资产负债金额项目明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAssetsAmountItem> queryAssetsAmountItemList(Map<String,Object> condition){
		return (List<LoanAssetsAmountItem>)this.queryEntities("queryAssetsAmountItemList", condition);
	}

	/**
	 * 分页查询资产负债金额项目明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAssetsAmountItem> queryAssetsAmountItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAssetsAmountItem>)this.queryEntities("queryAssetsAmountItemList", page, condition);
	}

	@Override
	public List<LoanAssetsAmountItem> getAssetsItemListByColumnName(Integer loanId, String columnName, String tableName) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("loanId",loanId);
		condition.put("columnName",columnName);
		condition.put("tableName",tableName);
		return (List<LoanAssetsAmountItem>)this.queryEntities("getAssetsItemListByColumnName", condition);
	}

}
