package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IProfitBizIncomeMonthProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IProfitBizIncomeMonthDao;
import banger.service.intf.IProfitBizIncomeMonthService;
import banger.domain.loan.LoanProfitBizIncomeMonth;

/**
 * 损益情况经营类收入支出明细月份项业务访问类
 */
@Repository
public class ProfitBizIncomeMonthService implements IProfitBizIncomeMonthService,IProfitBizIncomeMonthProvider {

	@Autowired
	private IProfitBizIncomeMonthDao profitBizIncomeMonthDao;

	/**
	 * 新增损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth){
		this.profitBizIncomeMonthDao.insertProfitBizIncomeMonth(profitBizIncomeMonth);
	}

	/**
	 *修改损益情况经营类收入支出明细月份项
	 * @param profitBizIncomeMonth 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProfitBizIncomeMonth(LoanProfitBizIncomeMonth profitBizIncomeMonth,Integer loginUserId){
		this.profitBizIncomeMonthDao.updateProfitBizIncomeMonth(profitBizIncomeMonth);
	}

	/**
	 * 通过主键删除损益情况经营类收入支出明细月份项
	 * @param ID 主键Id
	 */
	public void deleteProfitBizIncomeMonthById(Integer id){
		this.profitBizIncomeMonthDao.deleteProfitBizIncomeMonthById(id);
	}

	/**
	 * 通过主键得到损益情况经营类收入支出明细月份项
	 * @param ID 主键Id
	 */
	public LoanProfitBizIncomeMonth getProfitBizIncomeMonthById(Integer id){
		return this.profitBizIncomeMonthDao.getProfitBizIncomeMonthById(id);
	}

	/**
	 * 查询损益情况经营类收入支出明细月份项
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanProfitBizIncomeMonth> queryProfitBizIncomeMonthList(Map<String,Object> condition){
		return this.profitBizIncomeMonthDao.queryProfitBizIncomeMonthList(condition);
	}

	/**
	 * 分页查询损益情况经营类收入支出明细月份项
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanProfitBizIncomeMonth> queryProfitBizIncomeMonthList(Map<String,Object> condition,IPageSize page){
		return this.profitBizIncomeMonthDao.queryProfitBizIncomeMonthList(condition,page);
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:根据收入明细id删除收入明细月份项
	 * @Date: 11:10 2017/8/24
	 */

	public void deleteProfitBizIncomeItemMonthByItemId(Integer id) {
		this.profitBizIncomeMonthDao.deleteProfitBizIncomeItemMonthByItemId(id);
	}

	/**
	 * 根据贷款id查询年月数组
	 * @param loanId
	 * @return
	 */
	public List<LoanProfitBizIncomeMonth> queryYearAndMonthByLoanId(Integer loanId) {
		return this.profitBizIncomeMonthDao.queryYearAndMonthByLoanId(loanId);
	}

	/**
	 * 根据明细id,年，月删除年月明细
	 * @param incomeId
	 * @param year
	 * @param month
	 */
	public void deleteProfitBizIncomeMonthByIncomeIdAndYM(Integer incomeId, Integer year, Integer month) {
		this.profitBizIncomeMonthDao.deleteProfitBizIncomeMonthByIncomeIdAndYM(incomeId, year, month);
	}

	/**
	 * 根据incomeId查询年月收入明细
	 * @param incomeId
	 */
	public List<LoanProfitBizIncomeMonth> getProfitBizIncomeMonthByIncomeId(Integer incomeId) {
		return this.profitBizIncomeMonthDao.getProfitBizIncomeMonthByIncomeId(incomeId);
	}
}
