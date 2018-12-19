package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAssetsFixedItemDao;
import banger.service.intf.IAssetsFixedItemService;
import banger.domain.loan.LoanAssetsFixedItem;

/**
 * 资产负债固定资产项明细业务访问类
 */
@Repository
public class AssetsFixedItemService implements IAssetsFixedItemService {

	@Autowired
	private IAssetsFixedItemDao assetsFixedItemDao;

	/**
	 * 新增资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem,Integer loginUserId){
		assetsFixedItem.setCreateUser(loginUserId);
		assetsFixedItem.setCreateDate(DateUtil.getCurrentDate());
		assetsFixedItem.setUpdateUser(loginUserId);
		assetsFixedItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsFixedItemDao.insertAssetsFixedItem(assetsFixedItem);
	}

	/**
	 *修改资产负债固定资产项明细
	 * @param assetsFixedItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem,Integer loginUserId){
		assetsFixedItem.setUpdateUser(loginUserId);
		assetsFixedItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsFixedItemDao.updateAssetsFixedItem(assetsFixedItem);
	}

	/**
	 * 通过主键删除资产负债固定资产项明细
	 * @param ID 主键Id
	 */
	public void deleteAssetsFixedItemById(Integer id){
		this.assetsFixedItemDao.deleteAssetsFixedItemById(id);
	}

	/**
	 * 通过主键得到资产负债固定资产项明细
	 * @param ID 主键Id
	 */
	public LoanAssetsFixedItem getAssetsFixedItemById(Integer id){
		return this.assetsFixedItemDao.getAssetsFixedItemById(id);
	}

	/**
	 * 查询资产负债固定资产项明细
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAssetsFixedItem> queryAssetsFixedItemList(Map<String,Object> condition){
		return this.assetsFixedItemDao.queryAssetsFixedItemList(condition);
	}

	/**
	 * 分页查询资产负债固定资产项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAssetsFixedItem> queryAssetsFixedItemList(Map<String,Object> condition,IPageSize page){
		return this.assetsFixedItemDao.queryAssetsFixedItemList(condition,page);
	}

}
