package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProfitBizIncomeMonthDao;
import banger.domain.loan.LoanProfitBizIncomeMonth;

/**
 * 损益情况经营类收入支出明细月份项数据访问类
 */
@Repository
public class ProfitBizIncomeMonthDao extends PageSizeDao<LoanProfitBizIncomeMonth> implements IProfitBizIncomeMonthDao {

	/**
	 * 新增损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 */
	public void insertProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth){
		profitBizIncomeMonth.setId(this.newId().intValue());
		this.execute("insertProfitBizIncomeMonth",profitBizIncomeMonth);
	}

	/**
	 *修改损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 */
	public void updateProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth){
		this.execute("updateProfitBizIncomeMonth",profitBizIncomeMonth);
	}

	/**
	 * 通过主键删除损益情况经营类收入支出明细月份项
	 * @param id 主键Id
	 */
	public void deleteProfitBizIncomeMonthById(Integer id){
		this.execute("deleteProfitBizIncomeMonthById",id);
	}

	/**
	 * 通过主键得到损益情况经营类收入支出明细月份项
	 * @param id 主键Id
	 */
	public LoanProfitBizIncomeMonth getProfitBizIncomeMonthById(Integer id){
		return (LoanProfitBizIncomeMonth)this.queryEntity("getProfitBizIncomeMonthById",id);
	}

	/**
	 * 查询损益情况经营类收入支出明细月份项
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanProfitBizIncomeMonth> queryProfitBizIncomeMonthList(Map<String,Object> condition){
		return (List<LoanProfitBizIncomeMonth>)this.queryEntities("queryProfitBizIncomeMonthList", condition);
	}

	/**
	 * 分页查询损益情况经营类收入支出明细月份项
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanProfitBizIncomeMonth> queryProfitBizIncomeMonthList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanProfitBizIncomeMonth>)this.queryEntities("queryProfitBizIncomeMonthList", page, condition);
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:根据收入明细id删除收入明细月份项
	 * @Date: 11:10 2017/8/24
	 */
	@Override
	public void deleteProfitBizIncomeItemMonthByItemId(Integer id) {
		this.execute("deleteProfitBizIncomeItemMonthByItemId",id);
	}

	@Override
	public List<LoanProfitBizIncomeMonth> queryYearAndMonthByLoanId(Integer loanId) {
		return  (List<LoanProfitBizIncomeMonth>)this.queryEntities("queryYearAndMonthByLoanId",loanId);
	}
	/**
	 * 根据incomeId和年月删除明细
	 * @param incomeId
	 * @param year
	 * @param month
	 */
	@Override
	public void deleteProfitBizIncomeMonthByIncomeIdAndYM(Integer incomeId, Integer year, Integer month) {
		this.execute("deleteProfitBizIncomeMonthByIncomeIdAndYM",incomeId,year,month);
	}
	/**
	 * 根据incomeId查询年月收入明细
	 * @param incomeId
	 */

	public List<LoanProfitBizIncomeMonth> getProfitBizIncomeMonthByIncomeId(Integer incomeId) {
		return (List<LoanProfitBizIncomeMonth>)this.queryEntities("getProfitBizIncomeMonthByIncomeId",incomeId);
	}

}
