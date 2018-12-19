package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.moduleIntf.ILoanCrossCheckGrossProfitProvider;
import banger.service.intf.ICrossCheckFormulaService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ICrossCheckGrossProfitDao;
import banger.service.intf.ICrossCheckGrossProfitService;
import banger.domain.loan.LoanCrossCheckGrossProfit;

/**
 * 交叉检验毛利表业务访问类
 */
@Repository
public class CrossCheckGrossProfitService implements ICrossCheckGrossProfitService,ILoanCrossCheckGrossProfitProvider {

	@Autowired
	private ICrossCheckGrossProfitDao crossCheckGrossProfitDao;

	@Autowired
	private ICrossCheckFormulaService crossCheckFormulaService;

	/**
	 * 新增交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit,Integer loginUserId){
		crossCheckGrossProfit.setCreateUser(loginUserId);
		crossCheckGrossProfit.setCreateDate(DateUtil.getCurrentDate());
		crossCheckGrossProfit.setUpdateUser(loginUserId);
		crossCheckGrossProfit.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckGrossProfitDao.insertCrossCheckGrossProfit(crossCheckGrossProfit);
	}

	/**
	 *修改交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit,Integer loginUserId){
		crossCheckGrossProfit.setUpdateUser(loginUserId);
		crossCheckGrossProfit.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckGrossProfitDao.updateCrossCheckGrossProfit(crossCheckGrossProfit);
	}

	/**
	 * 通过主键删除交叉检验毛利表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckGrossProfitById(Integer id){
		this.crossCheckGrossProfitDao.deleteCrossCheckGrossProfitById(id);
	}

	/**
	 * 通过主键得到交叉检验毛利表
	 * @param id 主键Id
	 */
	public LoanCrossCheckGrossProfit getCrossCheckGrossProfitById(Integer id){
		return this.crossCheckGrossProfitDao.getCrossCheckGrossProfitById(id);
	}

	/**
	 * 查询交叉检验毛利表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanCrossCheckGrossProfit> queryCrossCheckGrossProfitList(Map<String,Object> condition){
		return this.crossCheckGrossProfitDao.queryCrossCheckGrossProfitList(condition);
	}

	/**
	 * 分页查询交叉检验毛利表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanCrossCheckGrossProfit> queryCrossCheckGrossProfitList(Map<String,Object> condition,IPageSize page){
		return this.crossCheckGrossProfitDao.queryCrossCheckGrossProfitList(condition,page);
	}

	/**
	 * 根据贷款id获取毛利表信息
	 * @param loanId
	 * @return
	 */
	public LoanCrossCheckGrossProfit getCrossCheckGrossProfitByLoanId(Integer loanId){
		return (LoanCrossCheckGrossProfit)this.crossCheckGrossProfitDao.getCrossCheckGrossProfitByLoanId(loanId);
	}

	/**
	 *保存毛利检验信息
	 *
	 * @param lccgp
	 * @param userId
	 */
	@Override
	public void saveLoanCrossCheckGrossProfit(LoanCrossCheckGrossProfit lccgp,Integer userId){
		Integer id = lccgp.getId();
		Integer loanId=lccgp.getLoanId();
		if(null != id && id.intValue()>0){
			this.updateNullCrossCheckGrossProfit(lccgp,userId);
		}else{
			this.insertCrossCheckGrossProfit(lccgp,userId);
		}
		this.updateGroProDev(loanId);
	}

	/**
	 * 计算并修改交叉检验毛利表偏差率（用在检查毛利率发生改变时使用）
	 * @param loanId 贷款id
	 */
	@Override
	public void updateGroProDev(Integer loanId) {
		this.crossCheckFormulaService.updateGroProDev(loanId);
	}

	/**
	 *修改交叉检验毛利表(app端传入空值时赋值字段为null)
	 * @param crossCheckGrossProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	@Override
	public void updateNullCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit,Integer loginUserId) {
		crossCheckGrossProfit.setUpdateUser(loginUserId);
		crossCheckGrossProfit.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckGrossProfitDao.updateNullCrossCheckGrossProfit(crossCheckGrossProfit);
	}

}
