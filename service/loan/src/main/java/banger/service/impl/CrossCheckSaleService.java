package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.domain.loan.LoanCrossCheckNetProfit;
import banger.domain.loan.LoanCrossCheckQuanyiquan;
import banger.moduleIntf.ILoanCrossCheckProvider;
import banger.moduleIntf.ILoanCrossCheckSaleProvider;
import banger.service.intf.ICrossCheckFormulaService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ICrossCheckSaleDao;
import banger.service.intf.ICrossCheckSaleService;
import banger.domain.loan.LoanCrossCheckSale;

/**
 * 交叉检验销售额表业务访问类
 */
@Repository
public class CrossCheckSaleService implements ICrossCheckSaleService,ILoanCrossCheckSaleProvider {


	@Autowired
	private ICrossCheckSaleDao crossCheckSaleDao;

	@Autowired
	private ICrossCheckFormulaService crossCheckFormulaService;

	/**
	 * 新增交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCrossCheckSale(LoanCrossCheckSale crossCheckSale,Integer loginUserId){
		crossCheckSale.setCreateUser(loginUserId);
		crossCheckSale.setCreateDate(DateUtil.getCurrentDate());
		crossCheckSale.setUpdateUser(loginUserId);
		crossCheckSale.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckSaleDao.insertCrossCheckSale(crossCheckSale);
	}

	/**
	 *修改交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCrossCheckSale(LoanCrossCheckSale crossCheckSale,Integer loginUserId){
		crossCheckSale.setUpdateUser(loginUserId);
		crossCheckSale.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckSaleDao.updateCrossCheckSale(crossCheckSale);
	}

	/**
	 * 通过主键删除交叉检验销售额表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckSaleById(Integer id){
		this.crossCheckSaleDao.deleteCrossCheckSaleById(id);
	}

	/**
	 * 通过主键得到交叉检验销售额表
	 * @param id 主键Id
	 */
	public LoanCrossCheckSale getCrossCheckSaleById(Integer id){
		return this.crossCheckSaleDao.getCrossCheckSaleById(id);
	}

	/**
	 * 查询交叉检验销售额表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanCrossCheckSale> queryCrossCheckSaleList(Map<String,Object> condition){
		return this.crossCheckSaleDao.queryCrossCheckSaleList(condition);
	}

	/**
	 * 分页查询交叉检验销售额表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanCrossCheckSale> queryCrossCheckSaleList(Map<String,Object> condition,IPageSize page){
		return this.crossCheckSaleDao.queryCrossCheckSaleList(condition,page);
	}


	//查询销售额表
	public LoanCrossCheckSale getCrossCheckSaleByLoanId(Integer loanId) {
		return this.crossCheckSaleDao.getCrossCheckSaleByLoanId(loanId) ;
	}

	//交叉检验销售额保存接口
	public void saveCrossCheckSale(LoanCrossCheckSale crossCheckSale, Integer loginUserId) {
		Integer id=crossCheckSale.getId();

		if(null!=id && id.intValue()>0){
			this.updateCrossCheckSale(crossCheckSale,loginUserId);
		}else{
			this.insertCrossCheckSale(crossCheckSale,loginUserId);
		}
	}

	/**
	 *修改交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCrossCheckNetProfit(LoanCrossCheckSale crossCheckSale, Integer loginUserId){
		crossCheckSale.setUpdateUser(loginUserId);
		crossCheckSale.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckSaleDao.updateCrossCheckSale(crossCheckSale);
	}

	/**
	 * 计算并修改交叉检验销售表表偏差率
	 * @param loanId 贷款id
	 */
	@Override
	public void updateSaleDev(Integer loanId) {
		this.crossCheckFormulaService.updateSaleDev(loanId);
	}
}
