package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProfitBizIncomeItemDao;
import banger.domain.loan.LoanProfitBizIncomeItem;

/**
 * 损益情况经营类收入支出明细表数据访问类
 */
@Repository
public class ProfitBizIncomeItemDao extends PageSizeDao<LoanProfitBizIncomeItem> implements IProfitBizIncomeItemDao {

	/**
	 * 新增损益情况经营类收入支出明细表
	 * @param profitBizIncomeItem 实体对像
	 */
	public void insertProfitBizIncomeItem(LoanProfitBizIncomeItem profitBizIncomeItem){
		profitBizIncomeItem.setId(this.newId().intValue());
		this.execute("insertProfitBizIncomeItem",profitBizIncomeItem);
	}

	/**
	 *修改损益情况经营类收入支出明细表
	 * @param profitBizIncomeItem 实体对像
	 */
	public void updateProfitBizIncomeItem(LoanProfitBizIncomeItem profitBizIncomeItem){
		this.execute("updateProfitBizIncomeItem",profitBizIncomeItem);
	}

	/**
	 * 通过主键删除损益情况经营类收入支出明细表
	 * @param id 主键Id
	 */
	public void deleteProfitBizIncomeItemById(Integer id){
		this.execute("deleteProfitBizIncomeItemById",id);
	}

	/**
	 * 通过主键得到损益情况经营类收入支出明细表
	 * @param id 主键Id
	 */
	public LoanProfitBizIncomeItem getProfitBizIncomeItemById(Integer id){
		return (LoanProfitBizIncomeItem)this.queryEntity("getProfitBizIncomeItemById",id);
	}

	/**
	 * 查询损益情况经营类收入支出明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String,Object> condition){
		return (List<LoanProfitBizIncomeItem>)this.queryEntities("queryProfitBizIncomeItemList", condition);
	}

	/**
	 * 分页查询损益情况经营类收入支出明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanProfitBizIncomeItem> queryProfitBizIncomeItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanProfitBizIncomeItem>)this.queryEntities("queryProfitBizIncomeItemList", page, condition);
	}

}
