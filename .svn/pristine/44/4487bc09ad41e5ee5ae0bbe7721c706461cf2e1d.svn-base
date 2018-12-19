package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAssetsAmountItemDao;
import banger.service.intf.IAssetsAmountItemService;
import banger.domain.loan.LoanAssetsAmountItem;

/**
 * 资产负债金额项目明细表业务访问类
 */
@Repository
public class AssetsAmountItemService implements IAssetsAmountItemService {

	@Autowired
	private IAssetsAmountItemDao assetsAmountItemDao;

	/**
	 * 新增资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem,Integer loginUserId){
		assetsAmountItem.setCreateUser(loginUserId);
		assetsAmountItem.setCreateDate(DateUtil.getCurrentDate());
		assetsAmountItem.setUpdateUser(loginUserId);
		assetsAmountItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsAmountItemDao.insertAssetsAmountItem(assetsAmountItem);
	}

	/**
	 *修改资产负债金额项目明细表
	 * @param assetsAmountItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAssetsAmountItem(LoanAssetsAmountItem assetsAmountItem,Integer loginUserId){
		assetsAmountItem.setUpdateUser(loginUserId);
		assetsAmountItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsAmountItemDao.updateAssetsAmountItem(assetsAmountItem);
	}

	/**
	 * 通过主键删除资产负债金额项目明细表
	 * @param ID 主键Id
	 */
	public void deleteAssetsAmountItemById(Integer id){
		this.assetsAmountItemDao.deleteAssetsAmountItemById(id);
	}

	/**
	 * 通过主键得到资产负债金额项目明细表
	 * @param ID 主键Id
	 */
	public LoanAssetsAmountItem getAssetsAmountItemById(Integer id){
		return this.assetsAmountItemDao.getAssetsAmountItemById(id);
	}

	/**
	 * 查询资产负债金额项目明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAssetsAmountItem> queryAssetsAmountItemList(Map<String,Object> condition){
		return this.assetsAmountItemDao.queryAssetsAmountItemList(condition);
	}

	/**
	 * 分页查询资产负债金额项目明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAssetsAmountItem> queryAssetsAmountItemList(Map<String,Object> condition,IPageSize page){
		return this.assetsAmountItemDao.queryAssetsAmountItemList(condition,page);
	}

}
