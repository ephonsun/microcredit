package banger.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IProfitLossInfoDao;
import banger.domain.loan.LoanCrossCheckQuanyiquan;
import banger.domain.loan.LoanProfitLossInfo;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.ILoanCrossCheckGrossProfitProvider;
import banger.moduleIntf.ILoanCrossCheckProvider;
import banger.moduleIntf.ILoanCrossCheckQuanyiquanProvider;
import banger.moduleIntf.ILoanProfitLossRatioItemProvider;
import banger.service.intf.ICrossCheckQuanyiquanService;
import banger.service.intf.ILoanAnalysislBusinessAndConsumService;
import banger.service.intf.ILoanStatService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IProfitLossRatioItemDao;
import banger.service.intf.IProfitLossRatioItemService;
import banger.domain.loan.LoanProfitLossRatioItem;

import javax.annotation.Resource;

/**
 * 损益情况毛利率明细表业务访问类
 */
@Repository
public class ProfitLossRatioItemService implements IProfitLossRatioItemService,ILoanProfitLossRatioItemProvider {

	@Autowired
	private IProfitLossRatioItemDao profitLossRatioItemDao;

	@Autowired
	private IProfitLossInfoDao profitLossInfoDao;
	@Resource
	private ILoanCrossCheckGrossProfitProvider loanCrossCheckGrossProfitProvider;
	@Resource
	private ILoanProfitLossRatioItemProvider loanProfitLossRatioItemProvider;
	@Resource
	private ILoanCrossCheckProvider iLoanCrossCheckProvider;
	@Resource
	private ILoanCrossCheckQuanyiquanProvider loanCrossCheckQuanyiquanProvider;
	@Resource
	ICrossCheckQuanyiquanService iCrossCheckQuanyiquanService;
	@Autowired
	private ILoanAnalysislBusinessAndConsumService analysislBusinessAndConsumService;
	/**
	 * 新增损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem,Integer loginUserId){
		profitLossRatioItem.setCreateUser(loginUserId);
		profitLossRatioItem.setCreateDate(DateUtil.getCurrentDate());
		profitLossRatioItem.setUpdateUser(loginUserId);
		profitLossRatioItem.setUpdateDate(DateUtil.getCurrentDate());
		setValue(profitLossRatioItem);
		//主表的毛利率
		BigDecimal grossProfitRatio=profitLossInfoDao.getProfitLossInfoByLoanId(profitLossRatioItem.getLoanId()).getGrossProfitRatio();
		LoanProfitLossInfo loanProfitLossInfo=new LoanProfitLossInfo();
		loanProfitLossInfo.setLoanId(profitLossRatioItem.getLoanId());
		loanProfitLossInfo.setGrossProfitRatio(OperationUtil.plus(2,grossProfitRatio,profitLossRatioItem.getWeightingProfitRatio()));
		//加上新加的加权毛利率后更新主表毛利率
		profitLossInfoDao.updateProfitLossInfoByLoanId(loanProfitLossInfo);
		this.profitLossRatioItemDao.insertProfitLossRatioItem(profitLossRatioItem);
		//loanCrossCheckGrossProfitProvider.updateGroProDev(profitLossRatioItem.getLoanId());
		iLoanCrossCheckProvider.updateGroProAndNetProDeviation(profitLossRatioItem.getLoanId());
//		//修改
//		loanCrossCheckQuanyiquanProvider.updateQuanyiquanDev(profitLossRatioItem.getLoanId());
		LoanCrossCheckQuanyiquan lccq=iCrossCheckQuanyiquanService.getCrossCheckQuanyiquanByLoanId(profitLossRatioItem.getLoanId());
		if(lccq==null){//如果权益表字段为空 先新增字段
			lccq=new LoanCrossCheckQuanyiquan();
			lccq.setLoanClassId(1);
			lccq.setLoanId(profitLossRatioItem.getLoanId());
			iCrossCheckQuanyiquanService.saveCrossCheckQuanyiquan(lccq,loginUserId);
		}
		iLoanCrossCheckProvider.updateSaleDev(profitLossRatioItem.getLoanId());
		iLoanCrossCheckProvider.updateQuanyiquanDev(profitLossRatioItem.getLoanId());
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossRatioItem.getLoanId());
	}

	/**
	 *修改损益情况毛利率明细表
	 * @param profitLossRatioItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem,Integer loginUserId){
		profitLossRatioItem.setUpdateUser(loginUserId);
		profitLossRatioItem.setUpdateDate(DateUtil.getCurrentDate());

		//更改之前的加权毛利率
		BigDecimal profitRatioBerfor=profitLossRatioItemDao.getProfitLossRatioItemById(profitLossRatioItem.getId()).getWeightingProfitRatio();
		setValue(profitLossRatioItem);
		//主表
		BigDecimal grossProfitRatio=profitLossInfoDao.getProfitLossInfoByLoanId(profitLossRatioItem.getLoanId()).getGrossProfitRatio();
		//更改之后的毛利率
		BigDecimal profitRatioAfter=profitLossRatioItem.getWeightingProfitRatio();
		BigDecimal update=OperationUtil.plus(2,OperationUtil.minus(grossProfitRatio,2,profitRatioBerfor),profitRatioAfter);
		LoanProfitLossInfo loanProfitLossInfo=new LoanProfitLossInfo();
		loanProfitLossInfo.setLoanId(profitLossRatioItem.getLoanId());
		loanProfitLossInfo.setGrossProfitRatio(update);
		//更新主表
		profitLossInfoDao.updateProfitLossInfoByLoanId(loanProfitLossInfo);
		this.profitLossRatioItemDao.updateProfitLossRatioItem(profitLossRatioItem);
		//loanCrossCheckGrossProfitProvider.updateGroProDev(profitLossRatioItem.getLoanId());
		iLoanCrossCheckProvider.updateGroProAndNetProDeviation(profitLossRatioItem.getLoanId());
//		loanCrossCheckQuanyiquanProvider.updateQuanyiquanDev(profitLossRatioItem.getLoanId());

		LoanCrossCheckQuanyiquan lccq=iCrossCheckQuanyiquanService.getCrossCheckQuanyiquanByLoanId(profitLossRatioItem.getLoanId());
		if(lccq==null){//如果权益表字段为空 先新增字段
			lccq=new LoanCrossCheckQuanyiquan();
			lccq.setLoanClassId(1);
			lccq.setLoanId(profitLossRatioItem.getLoanId());
			iCrossCheckQuanyiquanService.saveCrossCheckQuanyiquan(lccq,loginUserId);
		}
		iLoanCrossCheckProvider.updateSaleDev(profitLossRatioItem.getLoanId());
		iLoanCrossCheckProvider.updateQuanyiquanDev(profitLossRatioItem.getLoanId());
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossRatioItem.getLoanId());
	}

	/**
	 * 通过主键删除损益情况毛利率明细表
	 * @param id 主键Id
	 */
	public void deleteProfitLossRatioItemById(Integer id){
		LoanProfitLossRatioItem loanProfitLossRatioItem = loanProfitLossRatioItemProvider.getProfitLossRatioItemById(id);
		//loanCrossCheckGrossProfitProvider.updateGroProDev(loanProfitLossRatioItem.getLoanId());
		iLoanCrossCheckProvider.updateGroProAndNetProDeviation(loanProfitLossRatioItem.getLoanId());
//		loanCrossCheckQuanyiquanProvider.updateQuanyiquanDev(loanProfitLossRatioItem.getLoanId());
		LoanCrossCheckQuanyiquan lccq=iCrossCheckQuanyiquanService.getCrossCheckQuanyiquanByLoanId(loanProfitLossRatioItem.getLoanId());
		if(lccq==null){//如果权益表字段为空 先新增字段
			lccq=new LoanCrossCheckQuanyiquan();
			lccq.setLoanClassId(1);
			lccq.setLoanId(loanProfitLossRatioItem.getLoanId());
			iCrossCheckQuanyiquanService.saveCrossCheckQuanyiquan(lccq,0);
		}
		iLoanCrossCheckProvider.updateSaleDev(loanProfitLossRatioItem.getLoanId());
		iLoanCrossCheckProvider.updateQuanyiquanDev(loanProfitLossRatioItem.getLoanId());
		this.profitLossRatioItemDao.deleteProfitLossRatioItemById(id);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(loanProfitLossRatioItem.getLoanId());
	}

	/**
	 * 通过主键得到损益情况毛利率明细表
	 * @param id 主键Id
	 */
	public LoanProfitLossRatioItem getProfitLossRatioItemById(Integer id){
		return this.profitLossRatioItemDao.getProfitLossRatioItemById(id);
	}

	/**
	 * 查询损益情况毛利率明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanProfitLossRatioItem> queryProfitLossRatioItemList(Map<String,Object> condition){
		return this.profitLossRatioItemDao.queryProfitLossRatioItemList(condition);
	}

	/**
	 * 分页查询损益情况毛利率明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanProfitLossRatioItem> queryProfitLossRatioItemList(Map<String,Object> condition,IPageSize page){
		return this.profitLossRatioItemDao.queryProfitLossRatioItemList(condition,page);
	}

	/**
	 * 查询毛利率列表
	 * @param loanId
	 * @param columnName
	 * @return
	 */
	public List<LoanProfitLossRatioItem> queryGrossProfitMarginList(Integer loanId,String columnName) {
		return this.profitLossRatioItemDao.queryGrossProfitMarginList(loanId,columnName);
	}

	/**
	 * 计算毛利率
	 * @param loanProfitLossRatioItem
	 */
	private void setValue(LoanProfitLossRatioItem loanProfitLossRatioItem) {
		//毛利润
		BigDecimal crossProfit = OperationUtil.minus(loanProfitLossRatioItem.getSalePrice(), loanProfitLossRatioItem.getCostPrice());
		//毛利率
		BigDecimal profitRatio = OperationUtil.getProfitRatio(loanProfitLossRatioItem.getSalePrice(), loanProfitLossRatioItem.getCostPrice());
		//加权毛利率
		BigDecimal weightingProfitRatio = OperationUtil.getProfitRatio(loanProfitLossRatioItem.getSalePrice(), loanProfitLossRatioItem.getCostPrice(), loanProfitLossRatioItem.getSaleRatio());
		if (profitRatio != null) {
			loanProfitLossRatioItem.setProfitRatio(profitRatio);
		} else {
			loanProfitLossRatioItem.setProfitRatio(null);
		}
		if (weightingProfitRatio != null) {
			loanProfitLossRatioItem.setWeightingProfitRatio(weightingProfitRatio);
		} else {
			loanProfitLossRatioItem.setWeightingProfitRatio(null);
		}
		loanProfitLossRatioItem.setCrossProfit(crossProfit);
	}
}
