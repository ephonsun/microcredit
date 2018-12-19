package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckSale;
import banger.moduleIntf.ILoanCrossCheckQuanyiquanProvider;
import banger.service.intf.ICrossCheckFormulaService;
import banger.service.intf.ILoanAnalysislBusinessAndConsumService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ICrossCheckQuanyiquanDao;
import banger.service.intf.ICrossCheckQuanyiquanService;
import banger.domain.loan.LoanCrossCheckQuanyiquan;

import javax.annotation.Resource;

/**
 * 交叉检验权益表业务访问类
 */
@Repository
public class CrossCheckQuanyiquanService implements ICrossCheckQuanyiquanService,ILoanCrossCheckQuanyiquanProvider {

	@Autowired
	private ICrossCheckQuanyiquanDao crossCheckQuanyiquanDao;
	@Autowired
	private ICrossCheckFormulaService crossCheckFormulaService;
	@Autowired
	private ILoanAnalysislBusinessAndConsumService analysislBusinessAndConsumService;

	/**
	 * 新增交叉检验权益表
	 * @param crossCheckQuanyiquan 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan,Integer loginUserId){
		crossCheckQuanyiquan.setCreateUser(loginUserId);
		crossCheckQuanyiquan.setCreateDate(DateUtil.getCurrentDate());
		crossCheckQuanyiquan.setUpdateUser(loginUserId);
		crossCheckQuanyiquan.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckQuanyiquanDao.insertCrossCheckQuanyiquan(crossCheckQuanyiquan);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(crossCheckQuanyiquan.getLoanId());
	}

	/**
	 *修改交叉检验权益表
	 * @param crossCheckQuanyiquan 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan,Integer loginUserId){
		crossCheckQuanyiquan.setUpdateUser(loginUserId);
		crossCheckQuanyiquan.setUpdateDate(DateUtil.getCurrentDate());
		this.crossCheckQuanyiquanDao.updateCrossCheckQuanyiquan(crossCheckQuanyiquan);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(crossCheckQuanyiquan.getLoanId());
	}

	/**
	 * 通过主键删除交叉检验权益表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckQuanyiquanById(Integer id){
		LoanCrossCheckQuanyiquan crossCheckQuanyiquan = this.crossCheckQuanyiquanDao.getCrossCheckQuanyiquanById(id);
		this.crossCheckQuanyiquanDao.deleteCrossCheckQuanyiquanById(id);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(crossCheckQuanyiquan.getLoanId());
	}

	/**
	 * 通过主键得到交叉检验权益表
	 * @param id 主键Id
	 */
	public LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanById(Integer id){
		return this.crossCheckQuanyiquanDao.getCrossCheckQuanyiquanById(id);
	}

	/**
	 * 查询交叉检验权益表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanCrossCheckQuanyiquan> queryCrossCheckQuanyiquanList(Map<String,Object> condition){
		return this.crossCheckQuanyiquanDao.queryCrossCheckQuanyiquanList(condition);
	}

	/**
	 * 分页查询交叉检验权益表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanCrossCheckQuanyiquan> queryCrossCheckQuanyiquanList(Map<String,Object> condition,IPageSize page){
		return this.crossCheckQuanyiquanDao.queryCrossCheckQuanyiquanList(condition,page);
	}



	/**
	 * 交叉权利表查询
	 * @param loanId
	 * @return
	 */
	public LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanByLoanId(Integer loanId) {
		return this.crossCheckQuanyiquanDao.getCrossCheckQuanyiquanByLoanId(loanId);
	}

	//交叉检验权益保存接口
	public void saveCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan, Integer loginUserId) {
		Integer id=crossCheckQuanyiquan.getId();

		if(null!=id && id.intValue()>0){
			this.updateCrossCheckQuanyiquan(crossCheckQuanyiquan,loginUserId);
		}else{
			this.insertCrossCheckQuanyiquan(crossCheckQuanyiquan,loginUserId);
		}
	}

	/**
	 * 计算并修改交叉检验权益表
	 * @param loanId 贷款id
	 */
	@Override
	public void updateQuanyiquanDev(Integer loanId) {
		this.crossCheckFormulaService.updateQuanyiquanDev(loanId);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(loanId);
	}

}
