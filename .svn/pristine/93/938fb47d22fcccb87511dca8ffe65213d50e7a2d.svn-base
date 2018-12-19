package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.ILoanCrossCheckNetProfitProvider;
import banger.service.intf.ICrossCheckFormulaService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ICrossCheckNetProfitDao;
import banger.service.intf.ICrossCheckNetProfitService;
import banger.domain.loan.LoanCrossCheckNetProfit;

/**
 * 交叉检验净利表业务访问类
 */
@Repository
public class CrossCheckNetProfitService implements ICrossCheckNetProfitService,ILoanCrossCheckNetProfitProvider {

	@Autowired
	private ICrossCheckNetProfitDao crossCheckNetProfitDao;

	@Autowired
	private ICrossCheckFormulaService crossCheckFormulaService;

	/**
	 * 新增交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit,Integer loginUserId){
		crossCheckNetProfit.setCreateUser(loginUserId);
		crossCheckNetProfit.setCreateDate(DateUtil.getCurrentDate());
		crossCheckNetProfit.setUpdateUser(loginUserId);
		crossCheckNetProfit.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckNetProfitDao.insertCrossCheckNetProfit(crossCheckNetProfit);
	}

	/**
	 *修改交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit,Integer loginUserId){
		crossCheckNetProfit.setUpdateUser(loginUserId);
		crossCheckNetProfit.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckNetProfitDao.updateCrossCheckNetProfit(crossCheckNetProfit);
	}

	/**
	 * 通过主键删除交叉检验净利表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckNetProfitById(Integer id){
		this.crossCheckNetProfitDao.deleteCrossCheckNetProfitById(id);
	}

	/**
	 * 通过主键得到交叉检验净利表
	 * @param id 主键Id
	 */
	public LoanCrossCheckNetProfit getCrossCheckNetProfitById(Integer id){
		return this.crossCheckNetProfitDao.getCrossCheckNetProfitById(id);
	}

	/**
	 * 查询交叉检验净利表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanCrossCheckNetProfit> queryCrossCheckNetProfitList(Map<String,Object> condition){
		return this.crossCheckNetProfitDao.queryCrossCheckNetProfitList(condition);
	}

	/**
	 * 分页查询交叉检验净利表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanCrossCheckNetProfit> queryCrossCheckNetProfitList(Map<String,Object> condition,IPageSize page){
		return this.crossCheckNetProfitDao.queryCrossCheckNetProfitList(condition,page);
	}

	/**
	 * app端通过贷款id和贷款类别获取净利检验详情
	 * @param loanId 贷款id ,
	 * @return
	 */
	public LoanCrossCheckNetProfit getCrossCheckNetProfitByLoanId(Integer loanId){
		return this.crossCheckNetProfitDao.getCrossCheckNetProfitByLoanId(loanId);
	}

	/**
	 * app端保存净利检验详情（第一次时的插入表）
	 * @param loginUserId 登入用户Id
	 * @return
	 */
	public void saveCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit,Integer loginUserId){
		Integer id=crossCheckNetProfit.getId();
		if(null!=id && id.intValue()>0){
			this.updateNullCrossCheckNetProfit(crossCheckNetProfit,loginUserId);
		}else{
			this.insertCrossCheckNetProfit(crossCheckNetProfit,loginUserId);
		}
	}

	/**
	 * 同时修改损益表毛利率之后更新毛利表和净利表中的毛利净利偏差
	 * @param loanId
	 */
	@Override
	public void updateGroProAndNetProDeviation(Integer loanId) {
		this.crossCheckFormulaService.updateGroProAndNetProDeviation(loanId);
	}

	/**
	 * 计算并修改交叉检验净利表偏差率 （用在检查净利率发生改变时使用）
	 * @param loanId 贷款id
	 */
	@Override
	public void updateNetProDev(Integer loanId) {
		this.crossCheckFormulaService.updateNetProDev(loanId);
	}

	/**
	 *修改交叉检验净利表(app端传入空值时赋值字段为null)
	 * @param crossCheckNetProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateNullCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit, Integer loginUserId){
		crossCheckNetProfit.setUpdateUser(loginUserId);
		crossCheckNetProfit.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckNetProfitDao.updateNullCrossCheckNetProfit(crossCheckNetProfit);
	}

}
