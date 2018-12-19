package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAssetsStockItemDao;
import banger.service.intf.IAssetsStockItemService;
import banger.domain.loan.LoanAssetsStockItem;

/**
 * 资产负债存货项明细业务访问类
 */
@Repository
public class AssetsStockItemService implements IAssetsStockItemService {

	@Autowired
	private IAssetsStockItemDao assetsStockItemDao;

	/**
	 * 新增资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAssetsStockItem(LoanAssetsStockItem assetsStockItem,Integer loginUserId){
		assetsStockItem.setCreateUser(loginUserId);
		assetsStockItem.setCreateDate(DateUtil.getCurrentDate());
		assetsStockItem.setUpdateUser(loginUserId);
		assetsStockItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsStockItemDao.insertAssetsStockItem(assetsStockItem);
	}

	/**
	 *修改资产负债存货项明细
	 * @param assetsStockItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAssetsStockItem(LoanAssetsStockItem assetsStockItem,Integer loginUserId){
		assetsStockItem.setUpdateUser(loginUserId);
		assetsStockItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsStockItemDao.updateAssetsStockItem(assetsStockItem);
	}

	/**
	 * 通过主键删除资产负债存货项明细
	 * @param ID 主键Id
	 */
	public void deleteAssetsStockItemById(Integer id){
		this.assetsStockItemDao.deleteAssetsStockItemById(id);
	}

	/**
	 * 通过主键得到资产负债存货项明细
	 * @param ID 主键Id
	 */
	public LoanAssetsStockItem getAssetsStockItemById(Integer id){
		return this.assetsStockItemDao.getAssetsStockItemById(id);
	}

	/**
	 * 查询资产负债存货项明细
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAssetsStockItem> queryAssetsStockItemList(Map<String,Object> condition){
		return this.assetsStockItemDao.queryAssetsStockItemList(condition);
	}

	/**
	 * 分页查询资产负债存货项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAssetsStockItem> queryAssetsStockItemList(Map<String,Object> condition,IPageSize page){
		return this.assetsStockItemDao.queryAssetsStockItemList(condition,page);
	}

}
