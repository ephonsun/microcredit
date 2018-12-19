package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitBizIncomeMonth;

/**
 * 损益情况经营类收入支出明细月份项数据访问接口
 */
public interface IProfitBizIncomeMonthDao {

	/**
	 * 新增损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 */
	void insertProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth);

	/**
	 *修改损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 */
	void updateProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth);

	/**
	 * 通过主键删除损益情况经营类收入支出明细月份项
	 * @param id 主键Id
	 */
	void deleteProfitBizIncomeMonthById(Integer id);

	/**
	 * 通过主键得到损益情况经营类收入支出明细月份项
	 * @param id 主键Id
	 */
	LoanProfitBizIncomeMonth getProfitBizIncomeMonthById(Integer id);

	/**
	 * 查询损益情况经营类收入支出明细月份项
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanProfitBizIncomeMonth> queryProfitBizIncomeMonthList(Map<String, Object> condition);

	/**
	 * 分页查询损益情况经营类收入支出明细月份项
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanProfitBizIncomeMonth> queryProfitBizIncomeMonthList(Map<String, Object> condition, IPageSize page);


	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:根据收入明细id删除收入明细月份项
	 * @Date: 11:10 2017/8/24
	 */
	void deleteProfitBizIncomeItemMonthByItemId(Integer id);

	/**
	 * 根据id查询出年月
	 * @param loanId
	 * @return
	 */
	List<LoanProfitBizIncomeMonth> queryYearAndMonthByLoanId(Integer loanId);

	/**
	 * 根据incomeId和年月删除明细
	 * @param incomeId
	 * @param year
	 * @param month
	 */
	void deleteProfitBizIncomeMonthByIncomeIdAndYM(Integer incomeId,Integer year,Integer month);

	/**
	 * 根据incomeId查询年月收入明细
	 * @param incomeId
	 */
	public List<LoanProfitBizIncomeMonth> getProfitBizIncomeMonthByIncomeId(Integer incomeId);


}
