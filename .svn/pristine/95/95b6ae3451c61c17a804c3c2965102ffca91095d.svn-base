package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAssetsDebtsItemDao;
import banger.domain.loan.LoanAssetsDebtsItem;

/**
 * 资产负债项明细数据访问类
 */
@Repository
public class AssetsDebtsItemDao extends PageSizeDao<LoanAssetsDebtsItem> implements IAssetsDebtsItemDao {

	/**
	 * 新增资产负债项明细
	 * @param assetsDebtsItem 实体对像
	 */
	public void insertAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem){
		assetsDebtsItem.setId(this.newId().intValue());
		this.execute("insertAssetsDebtsItem",assetsDebtsItem);
	}

	/**
	 *修改资产负债项明细
	 * @param assetsDebtsItem 实体对像
	 */
	public void updateAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem){
		this.execute("updateAssetsDebtsItem",assetsDebtsItem);
	}

	/**
	 * 通过主键删除资产负债项明细
	 * @param id 主键Id
	 */
	public void deleteAssetsDebtsItemById(Integer id){
		this.execute("deleteAssetsDebtsItemById",id);
	}

	/**
	 * 通过主键得到资产负债项明细
	 * @param id 主键Id
	 */
	public LoanAssetsDebtsItem getAssetsDebtsItemById(Integer id){
		return (LoanAssetsDebtsItem)this.queryEntity("getAssetsDebtsItemById",id);
	}

	/**
	 * 查询资产负债项明细
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAssetsDebtsItem> queryAssetsDebtsItemList(Map<String,Object> condition){
		return (List<LoanAssetsDebtsItem>)this.queryEntities("queryAssetsDebtsItemList", condition);
	}

	/**
	 * 分页查询资产负债项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAssetsDebtsItem> queryAssetsDebtsItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAssetsDebtsItem>)this.queryEntities("queryAssetsDebtsItemList", page, condition);
	}

}
