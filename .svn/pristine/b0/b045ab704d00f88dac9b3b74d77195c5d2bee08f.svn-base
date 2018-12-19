package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckQuanyiquan;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICrossCheckIncomeDao;
import banger.domain.loan.LoanCrossCheckIncome;

/**
 * 交叉检验收入表数据访问类
 */
@Repository
public class CrossCheckIncomeDao extends PageSizeDao<LoanCrossCheckIncome> implements ICrossCheckIncomeDao {

	/**
	 * 新增交叉检验收入表
	 * @param crossCheckIncome 实体对像
	 */
	public void insertCrossCheckIncome(LoanCrossCheckIncome crossCheckIncome){
		crossCheckIncome.setId(this.newId().intValue());
		this.execute("insertCrossCheckIncome",crossCheckIncome);
	}

	/**
	 *修改交叉检验收入表
	 * @param crossCheckIncome 实体对像
	 */
	public void updateCrossCheckIncome(LoanCrossCheckIncome crossCheckIncome){
		this.execute("updateCrossCheckIncome",crossCheckIncome);
	}

	/**
	 * 通过主键删除交叉检验收入表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckIncomeById(Integer id){
		this.execute("deleteCrossCheckIncomeById",id);
	}

	/**
	 * 通过主键得到交叉检验收入表
	 * @param id 主键Id
	 */
	public LoanCrossCheckIncome getCrossCheckIncomeById(Integer id){
		return (LoanCrossCheckIncome)this.queryEntity("getCrossCheckIncomeById",id);
	}

	/**
	 * 查询交叉检验收入表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanCrossCheckIncome> queryCrossCheckIncomeList(Map<String,Object> condition){
		return (List<LoanCrossCheckIncome>)this.queryEntities("queryCrossCheckIncomeList", condition);
	}

	/**
	 * 分页查询交叉检验收入表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanCrossCheckIncome> queryCrossCheckIncomeList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanCrossCheckIncome>)this.queryEntities("queryCrossCheckIncomeList", page, condition);
	}

	/**
     * 通过贷款id获取交叉检验 收入检验详情
     * @param loanId
     * @return
     */
    @Override
    public LoanCrossCheckIncome getCrossCheckIncomeByLoanId(Integer loanId) {
        return (LoanCrossCheckIncome)this.queryEntity("getCrossCheckIncomeByLoanId",loanId);
    }

	/**
	 *修改交叉检验收入表 (app端传入空值时赋值字段为null)
	 * @param crossCheckIncome 实体对像
	 */
	public void updateNullCrossCheckIncome(LoanCrossCheckIncome crossCheckIncome){
		this.execute("updateNullCrossCheckIncome",crossCheckIncome);
	}

}
