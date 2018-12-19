package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProfitLossRatioItemDao;
import banger.domain.loan.LoanProfitLossRatioItem;

/**
 * 损益情况毛利率明细表数据访问类
 */
@Repository
public class ProfitLossRatioItemDao extends PageSizeDao<LoanProfitLossRatioItem> implements IProfitLossRatioItemDao {

	/**
	 * 新增损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 */
	public void insertProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem){
		profitLossRatioItem.setId(this.newId().intValue());
		this.execute("insertProfitLossRatioItem",profitLossRatioItem);
	}

	/**
	 *修改损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 */
	public void updateProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem){
		this.execute("updateProfitLossRatioItem",profitLossRatioItem);
	}

	/**
	 * 通过主键删除损益情况毛利率明细表
	 * @param id 主键Id
	 */
	public void deleteProfitLossRatioItemById(Integer id){
		this.execute("deleteProfitLossRatioItemById",id);
	}

	/**
	 * 通过主键得到损益情况毛利率明细表
	 * @param id 主键Id
	 */
	public LoanProfitLossRatioItem getProfitLossRatioItemById(Integer id){
		return (LoanProfitLossRatioItem)this.queryEntity("getProfitLossRatioItemById",id);
	}

	/**
	 * 查询损益情况毛利率明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanProfitLossRatioItem> queryProfitLossRatioItemList(Map<String,Object> condition){
		return (List<LoanProfitLossRatioItem>)this.queryEntities("queryProfitLossRatioItemList", condition);
	}

	/**
	 * 分页查询损益情况毛利率明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanProfitLossRatioItem> queryProfitLossRatioItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanProfitLossRatioItem>)this.queryEntities("queryProfitLossRatioItemList", page, condition);
	}
	@Override
	public List<LoanProfitLossRatioItem> queryGrossProfitMarginList(Integer loanId,String columnName) {
		return (List<LoanProfitLossRatioItem>)this.queryEntities("queryGrossProfitMarginList",loanId,columnName);
	}
}
