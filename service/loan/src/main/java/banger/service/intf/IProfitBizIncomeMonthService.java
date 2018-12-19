package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitBizIncomeMonth;

/**
 * 损益情况经营类收入支出明细月份项业务访问接口
 */
public interface IProfitBizIncomeMonthService {

	/**
	 * 新增损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth);

	/**
	 *修改损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth, Integer loginUserId);

	/**
	 * 通过主键删除损益情况经营类收入支出明细月份项
	 * @param ID 主键Id
	 */
	void deleteProfitBizIncomeMonthById(Integer id);

	/**
	 * 通过主键得到损益情况经营类收入支出明细月份项
	 * @param ID 主键Id
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
	 * 根据贷款id查询年月数组
	 * @param loanId
	 * @return
	 */
	List<LoanProfitBizIncomeMonth> queryYearAndMonthByLoanId(Integer loanId);

	/**
	 * 根据明细id,年，月删除年月明细
	 * @param incomeId
	 * @param year
	 * @param month
	 */
	void deleteProfitBizIncomeMonthByIncomeIdAndYM(Integer incomeId,Integer year,Integer month);

}
