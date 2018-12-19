package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAssetsAccountItemDao;
import banger.domain.loan.LoanAssetsAccountItem;

/**
 * 资产负债账款项明细数据访问类
 */
@Repository
public class AssetsAccountItemDao extends PageSizeDao<LoanAssetsAccountItem> implements IAssetsAccountItemDao {

	/**
	 * 新增资产负债账款项明细
	 * @param assetsAccountItem 实体对像
	 */
	public void insertAssetsAccountItem(LoanAssetsAccountItem assetsAccountItem){
		assetsAccountItem.setId(this.newId().intValue());
		this.execute("insertAssetsAccountItem",assetsAccountItem);
	}

	/**
	 *修改资产负债账款项明细
	 * @param assetsAccountItem 实体对像
	 */
	public void updateAssetsAccountItem(LoanAssetsAccountItem assetsAccountItem){
		this.execute("updateAssetsAccountItem",assetsAccountItem);
	}

	/**
	 * 通过主键删除资产负债账款项明细
	 * @param id 主键Id
	 */
	public void deleteAssetsAccountItemById(Integer id){
		this.execute("deleteAssetsAccountItemById",id);
	}

	/**
	 * 通过主键得到资产负债账款项明细
	 * @param id 主键Id
	 */
	public LoanAssetsAccountItem getAssetsAccountItemById(Integer id){
		return (LoanAssetsAccountItem)this.queryEntity("getAssetsAccountItemById",id);
	}

	/**
	 * 查询资产负债账款项明细
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAssetsAccountItem> queryAssetsAccountItemList(Map<String,Object> condition){
		return (List<LoanAssetsAccountItem>)this.queryEntities("queryAssetsAccountItemList", condition);
	}

	/**
	 * 分页查询资产负债账款项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAssetsAccountItem> queryAssetsAccountItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAssetsAccountItem>)this.queryEntities("queryAssetsAccountItemList", page, condition);
	}

}
