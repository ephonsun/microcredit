package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProfitConsumIncomeItemDao;
import banger.domain.loan.LoanProfitConsumIncomeItem;

/**
 * 损益情况消费类收入支出明细表数据访问类
 */
@Repository
public class ProfitConsumIncomeItemDao extends PageSizeDao<LoanProfitConsumIncomeItem> implements IProfitConsumIncomeItemDao {

	/**
	 * 新增损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 */
	public void insertProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem){
		profitConsumIncomeItem.setId(this.newId().intValue());
		this.execute("insertProfitConsumIncomeItem",profitConsumIncomeItem);
	}

	/**
	 *修改损益情况消费类收入支出明细表
	 * @param profitConsumIncomeItem 实体对像
	 */
	public void updateProfitConsumIncomeItem(LoanProfitConsumIncomeItem profitConsumIncomeItem){
		this.execute("updateProfitConsumIncomeItem",profitConsumIncomeItem);
	}

	/**
	 * 通过主键删除损益情况消费类收入支出明细表
	 * @param id 主键Id
	 */
	public void deleteProfitConsumIncomeItemById(Integer id){
		this.execute("deleteProfitConsumIncomeItemById",id);
	}

	/**
	 * 通过主键得到损益情况消费类收入支出明细表
	 * @param id 主键Id
	 */
	public LoanProfitConsumIncomeItem getProfitConsumIncomeItemById(Integer id){
		return (LoanProfitConsumIncomeItem)this.queryEntity("getProfitConsumIncomeItemById",id);
	}

	/**
	 * 查询损益情况消费类收入支出明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String,Object> condition){
		return (List<LoanProfitConsumIncomeItem>)this.queryEntities("queryProfitConsumIncomeItemList", condition);
	}

	/**
	 * 分页查询损益情况消费类收入支出明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanProfitConsumIncomeItem> queryProfitConsumIncomeItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanProfitConsumIncomeItem>)this.queryEntities("queryProfitConsumIncomeItemList", page, condition);
	}

}
