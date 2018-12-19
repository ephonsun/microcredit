package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.ILoanCrossCheckIncomeProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ICrossCheckIncomeDao;
import banger.service.intf.ICrossCheckIncomeService;
import banger.domain.loan.LoanCrossCheckIncome;

/**
 * 交叉检验收入表业务访问类
 */
@Repository
public class CrossCheckIncomeService implements ICrossCheckIncomeService,ILoanCrossCheckIncomeProvider {

	@Autowired
	private ICrossCheckIncomeDao crossCheckIncomeDao;

	/**
	 * 新增交叉检验收入表
	 * @param crossCheckIncome 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCrossCheckIncome(LoanCrossCheckIncome crossCheckIncome,Integer loginUserId){
		crossCheckIncome.setCreateUser(loginUserId);
		crossCheckIncome.setCreateDate(DateUtil.getCurrentDate());
		crossCheckIncome.setUpdateUser(loginUserId);
		crossCheckIncome.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckIncomeDao.insertCrossCheckIncome(crossCheckIncome);
	}

	/**
	 *修改交叉检验收入表
	 * @param crossCheckIncome 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCrossCheckIncome(LoanCrossCheckIncome crossCheckIncome,Integer loginUserId){
		crossCheckIncome.setUpdateUser(loginUserId);
		crossCheckIncome.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckIncomeDao.updateCrossCheckIncome(crossCheckIncome);
	}

	/**
	 * 通过主键删除交叉检验收入表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckIncomeById(Integer id){
		this.crossCheckIncomeDao.deleteCrossCheckIncomeById(id);
	}

	/**
	 * 通过主键得到交叉检验收入表
	 * @param id 主键Id
	 */
	public LoanCrossCheckIncome getCrossCheckIncomeById(Integer id){
		return this.crossCheckIncomeDao.getCrossCheckIncomeById(id);
	}

	/**
	 * 查询交叉检验收入表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanCrossCheckIncome> queryCrossCheckIncomeList(Map<String,Object> condition){
		return this.crossCheckIncomeDao.queryCrossCheckIncomeList(condition);
	}

	/**
	 * 分页查询交叉检验收入表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanCrossCheckIncome> queryCrossCheckIncomeList(Map<String,Object> condition,IPageSize page){
		return this.crossCheckIncomeDao.queryCrossCheckIncomeList(condition,page);
	}


	/**
	 * 通过贷款id获取交叉检验 收入检验详情
	 * @param loanId
	 * @return
	 */
	@Override
	public LoanCrossCheckIncome getCrossCheckIncomeByLoanId(Integer loanId) {
		return crossCheckIncomeDao.getCrossCheckIncomeByLoanId(loanId);
	}

	/**
	 * 保存收入检验信息
	 * @param lcci
	 */
	@Override
	public void saveLoanCrossCheckIncome(LoanCrossCheckIncome lcci,Integer userId) {
		Integer id=lcci.getId();
		if(null != id && id.intValue()>0){
			this.updateNullCrossCheckIncome(lcci,userId);
		}else{
			this.insertCrossCheckIncome(lcci,userId);
		}
	}

	/**
	 *修改交叉检验收入表(app端传入空值时赋值字段为null)
	 * @param crossCheckIncome 实体对像
	 * @param loginUserId 登入用户Id
	 */
	@Override
	public void updateNullCrossCheckIncome(LoanCrossCheckIncome crossCheckIncome,Integer loginUserId){
		crossCheckIncome.setUpdateUser(loginUserId);
		crossCheckIncome.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckIncomeDao.updateNullCrossCheckIncome(crossCheckIncome);
	}
}
