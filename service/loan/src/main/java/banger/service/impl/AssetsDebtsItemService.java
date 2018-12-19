package banger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAssetsDebtsItemDao;
import banger.service.intf.IAssetsDebtsItemService;
import banger.domain.loan.LoanAssetsDebtsItem;

/**
 * 资产负债项明细业务访问类
 */
@Repository
public class AssetsDebtsItemService implements IAssetsDebtsItemService {

	@Autowired
	private IAssetsDebtsItemDao assetsDebtsItemDao;

	/**
	 * 新增资产负债项明细
	 * @param assetsDebtsItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem,Integer loginUserId){
		assetsDebtsItem.setCreateUser(loginUserId);
		assetsDebtsItem.setCreateDate(DateUtil.getCurrentDate());
		assetsDebtsItem.setUpdateUser(loginUserId);
		assetsDebtsItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsDebtsItemDao.insertAssetsDebtsItem(assetsDebtsItem);
	}

	/**
	 *修改资产负债项明细
	 * @param assetsDebtsItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem,Integer loginUserId){
		BigDecimal bebtsAmount=assetsDebtsItem.getBebtsAmount();
		Integer termLimit=assetsDebtsItem.getTermLimit();
		if(null == bebtsAmount){
			bebtsAmount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
			assetsDebtsItem.setBebtsAmount(bebtsAmount);
		}
		if(null == termLimit){
			termLimit = new Integer(0);
			assetsDebtsItem.setTermLimit(termLimit);
		}
		assetsDebtsItem.setUpdateUser(loginUserId);
		assetsDebtsItem.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsDebtsItemDao.updateAssetsDebtsItem(assetsDebtsItem);
	}

	/**
	 * 通过主键删除资产负债项明细
	 * @param id 主键Id
	 */
	public void deleteAssetsDebtsItemById(Integer id){
		this.assetsDebtsItemDao.deleteAssetsDebtsItemById(id);
	}

	/**
	 * 通过主键得到资产负债项明细
	 * @param id 主键Id
	 */
	public LoanAssetsDebtsItem getAssetsDebtsItemById(Integer id){
		return this.assetsDebtsItemDao.getAssetsDebtsItemById(id);
	}

	/**
	 * 查询资产负债项明细
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanAssetsDebtsItem> queryAssetsDebtsItemList(Map<String,Object> condition){
		return this.assetsDebtsItemDao.queryAssetsDebtsItemList(condition);
	}

	/**
	 * 分页查询资产负债项明细
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAssetsDebtsItem> queryAssetsDebtsItemList(Map<String,Object> condition,IPageSize page){
		return this.assetsDebtsItemDao.queryAssetsDebtsItemList(condition,page);
	}

}
