package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAssetsAccountItemDao;
import banger.service.intf.IAssetsAccountItemService;
import banger.domain.loan.LoanAssetsAccountItem;

/**
 * 资产负债账款项明细业务访问类
 */
@Repository
public class AssetsAccountItemService implements IAssetsAccountItemService {

	@Autowired
	private IAssetsAccountItemDao assetsAccountItemDao;

	/**
	 * 新增资产负债账款项明细
	 * @param assetsAccountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAssetsAccountItem(LoanAssetsAccountItem assetsAccountItem,Integer loginUserId){
		assetsAccountItem.setCreateUser(loginUserId);
		assetsAccountItem.setCreateDate(DateUtil.getCurrentDate());
		assetsAccountItem.setUpdateUser(loginUserId);
		assetsAccountItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsAccountItemDao.insertAssetsAccountItem(assetsAccountItem);
	}

	/**
	 *修改资产负债账款项明细
	 * @param assetsAccountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAssetsAccountItem(LoanAssetsAccountItem assetsAccountItem,Integer loginUserId){
		Integer accountAge=assetsAccountItem.getAccountAge();
		if(null == accountAge){
			accountAge = new Integer(0);
			assetsAccountItem.setAccountAge(accountAge);
		}
		assetsAccountItem.setUpdateUser(loginUserId);
		assetsAccountItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsAccountItemDao.updateAssetsAccountItem(assetsAccountItem);
	}

	/**
	 * 通过主键删除资产负债账款项明细
	 * @param id 主键Id
	 */
	public void deleteAssetsAccountItemById(Integer id){
		this.assetsAccountItemDao.deleteAssetsAccountItemById(id);
	}

	/**
	 * 通过主键得到资产负债账款项明细
	 * @param id 主键Id
	 */
	public LoanAssetsAccountItem getAssetsAccountItemById(Integer id){
		return this.assetsAccountItemDao.getAssetsAccountItemById(id);
	}

	/**
	 * 查询资产负债账款项明细
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAssetsAccountItem> queryAssetsAccountItemList(Map<String,Object> condition){
		return this.assetsAccountItemDao.queryAssetsAccountItemList(condition);
	}

	/**
	 * 分页查询资产负债账款项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAssetsAccountItem> queryAssetsAccountItemList(Map<String,Object> condition,IPageSize page){
		return this.assetsAccountItemDao.queryAssetsAccountItemList(condition,page);
	}

}
