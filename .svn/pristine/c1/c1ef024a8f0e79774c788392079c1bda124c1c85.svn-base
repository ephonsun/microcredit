package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IProfitConsumIncomeItemProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IProfitConsumIncomeItemDao;
import banger.service.intf.IProfitConsumIncomeItemService;
import banger.domain.loan.LoanProfitConsumIncomeItem;

/**
 * 损益情况消费类收入支出明细表业务访问类
 */
@Repository
public class ProfitConsumIncomeItemService implements IProfitConsumIncomeItemService ,IProfitConsumIncomeItemProvider {

	@Autowired
	private IProfitConsumIncomeItemDao profitConsumIncomeItemDao;

	/**
	 * 新增损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem,Integer loginUserId){
		profitConsumIncomeItem.setCreateUser(loginUserId);
		profitConsumIncomeItem.setCreateDate(DateUtil.getCurrentDate());
		profitConsumIncomeItem.setUpdateUser(loginUserId);
		profitConsumIncomeItem.setUpdateDate(DateUtil.getCurrentDate());
		this.profitConsumIncomeItemDao.insertProfitConsumIncomeItem(profitConsumIncomeItem);
	}

	/**
	 *修改损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem,Integer loginUserId){
		profitConsumIncomeItem.setUpdateUser(loginUserId);
		profitConsumIncomeItem.setUpdateDate(DateUtil.getCurrentDate());
		this.profitConsumIncomeItemDao.updateProfitConsumIncomeItem(profitConsumIncomeItem);
	}

	/**
	 * 通过主键删除损益情况消费类收入支出明细表
	 * @param ID 主键Id
	 */
	public void deleteProfitConsumIncomeItemById(Integer id){
		this.profitConsumIncomeItemDao.deleteProfitConsumIncomeItemById(id);
	}

	/**
	 * 通过主键得到损益情况消费类收入支出明细表
	 * @param ID 主键Id
	 */
	public LoanProfitConsumIncomeItem getProfitConsumIncomeItemById(Integer id){
		return this.profitConsumIncomeItemDao.getProfitConsumIncomeItemById(id);
	}

	/**
	 * 查询损益情况消费类收入支出明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String,Object> condition){
		return this.profitConsumIncomeItemDao.queryProfitConsumIncomeItemList(condition);
	}

	/**
	 * 分页查询损益情况消费类收入支出明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String,Object> condition,IPageSize page){
		return this.profitConsumIncomeItemDao.queryProfitConsumIncomeItemList(condition,page);
	}

}
