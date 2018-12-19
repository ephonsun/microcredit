package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAssetsFixedItemDao;
import banger.domain.loan.LoanAssetsFixedItem;

/**
 * 资产负债固定资产项明细数据访问类
 */
@Repository
public class AssetsFixedItemDao extends PageSizeDao<LoanAssetsFixedItem> implements IAssetsFixedItemDao {

	/**
	 * 新增资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 */
	public void insertAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem){
		assetsFixedItem.setId(this.newId().intValue());
		this.execute("insertAssetsFixedItem",assetsFixedItem);
	}

	/**
	 *修改资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 */
	public void updateAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem){
		this.execute("updateAssetsFixedItem",assetsFixedItem);
	}

	/**
	 * 通过主键删除资产负债固定资产项明细
	 * @param id 主键Id
	 */
	public void deleteAssetsFixedItemById(Integer id){
		this.execute("deleteAssetsFixedItemById",id);
	}

	/**
	 * 通过主键得到资产负债固定资产项明细
	 * @param id 主键Id
	 */
	public LoanAssetsFixedItem getAssetsFixedItemById(Integer id){
		return (LoanAssetsFixedItem)this.queryEntity("getAssetsFixedItemById",id);
	}

	/**
	 * 查询资产负债固定资产项明细
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAssetsFixedItem> queryAssetsFixedItemList(Map<String,Object> condition){
		return (List<LoanAssetsFixedItem>)this.queryEntities("queryAssetsFixedItemList", condition);
	}

	/**
	 * 分页查询资产负债固定资产项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAssetsFixedItem> queryAssetsFixedItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAssetsFixedItem>)this.queryEntities("queryAssetsFixedItemList", page, condition);
	}

}
