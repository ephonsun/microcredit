package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAssetsStockItemDao;
import banger.domain.loan.LoanAssetsStockItem;

/**
 * 资产负债存货项明细数据访问类
 */
@Repository
public class AssetsStockItemDao extends PageSizeDao<LoanAssetsStockItem> implements IAssetsStockItemDao {

	/**
	 * 新增资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 */
	public void insertAssetsStockItem(LoanAssetsStockItem assetsStockItem){
		assetsStockItem.setId(this.newId().intValue());
		this.execute("insertAssetsStockItem",assetsStockItem);
	}

	/**
	 *修改资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 */
	public void updateAssetsStockItem(LoanAssetsStockItem assetsStockItem){
		this.execute("updateAssetsStockItem",assetsStockItem);
	}

	/**
	 * 通过主键删除资产负债存货项明细
	 * @param id 主键Id
	 */
	public void deleteAssetsStockItemById(Integer id){
		this.execute("deleteAssetsStockItemById",id);
	}

	/**
	 * 通过主键得到资产负债存货项明细
	 * @param id 主键Id
	 */
	public LoanAssetsStockItem getAssetsStockItemById(Integer id){
		return (LoanAssetsStockItem)this.queryEntity("getAssetsStockItemById",id);
	}

	/**
	 * 查询资产负债存货项明细
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAssetsStockItem> queryAssetsStockItemList(Map<String,Object> condition){
		return (List<LoanAssetsStockItem>)this.queryEntities("queryAssetsStockItemList", condition);
	}

	/**
	 * 分页查询资产负债存货项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAssetsStockItem> queryAssetsStockItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAssetsStockItem>)this.queryEntities("queryAssetsStockItemList", page, condition);
	}

}
