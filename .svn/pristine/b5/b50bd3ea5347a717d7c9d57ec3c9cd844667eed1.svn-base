package banger.service.impl;

import banger.dao.intf.IAnalysislBusinessDao;
import banger.dao.intf.IAnalysislConsumptionDao;
import banger.dao.intf.IApplyInfoDao;
import banger.domain.enumerate.LoanClassEnum;
import banger.domain.loan.*;
import banger.domain.loan.finance.*;
import banger.moduleIntf.ILoanAnalysislBusinessAndConsumProvider;
import banger.moduleIntf.ILoanFinanceAnalysisService;
import banger.moduleIntf.ILoanIncomeStatementProvider;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.ILoanAnalysislBusinessAndConsumService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangdw
 * @Description:
 * @Date: Created in 14:44 2017/11/6.
 */
@Repository
public class AnalysislBusinessAndConsumService implements ILoanAnalysislBusinessAndConsumService, ILoanAnalysislBusinessAndConsumProvider {

	private static final Logger logger = LoggerFactory.getLogger(AnalysislBusinessAndConsumService.class);
	@Autowired
	private IApplyInfoDao applyInfoDao;
	@Autowired
	private ILoanModule loanModule;
	@Autowired
	private ILoanIncomeStatementProvider loanIncomeStatementProvider;
	@Autowired
	private ILoanFinanceAnalysisService loanFinanceAnalysisService;

	@Autowired
	private IAnalysislBusinessDao analysislBusinessDao;//经营类

	@Autowired
	private IAnalysislConsumptionDao analysislConsumptionDao;//消费类

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description:根据贷款id处理三表数据
	 * @Date: 14:11 2017/11/6
	 */
	@Override
	public void saveAnalysislBusinessOrConsum(Integer loanId) {
		try {
			if (loanId == null) {
				logger.info("saveAnalysislBusinessOrConsum接口处理三表字段时,贷款id为null");
				return;
			}
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("loanId", loanId);
			LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(loanId);
			if (applyInfo == null) {
				logger.info("saveAnalysislBusinessOrConsum接口处理三表字段时,贷款不存在,贷款id" + loanId);
				return;
			}
			//不在计算行业分类等级
			applyInfo.setLoanBusinessCatalog(null);
			if (applyInfo.getLoanClassId() !=null && LoanClassEnum.LOAN_BUSINESS_CLASS.value == applyInfo.getLoanClassId()) {
				LoanAnalysislBusiness analysislBusines = new LoanAnalysislBusiness();
				saveAnalysislBusiness(analysislBusines,applyInfo);
				//操作经营贷财务分析详情总表
				List<LoanAnalysislBusiness> loanAnalysislBusinessList = analysislBusinessDao.queryAnalysislBusinessList(condition);
				if (CollectionUtils.isNotEmpty(loanAnalysislBusinessList)) {
					analysislBusines.setId(loanAnalysislBusinessList.get(0).getId());
					//更新
					analysislBusinessDao.updateAnalysislBusiness(analysislBusines);
				}else{
					//新增
					analysislBusinessDao.insertAnalysislBusiness(analysislBusines);
				}
			} else if (applyInfo.getLoanClassId() !=null && LoanClassEnum.LOAN_CONSUMER_CLASS.value == applyInfo.getLoanClassId()){
				LoanAnalysislConsumption analysislConsumption = new LoanAnalysislConsumption();
				saveAnalysislConsumption(analysislConsumption,applyInfo);
				//操作消费类财务分析详情总表
				List<LoanAnalysislConsumption> loanAnalysislConsumList = analysislConsumptionDao.queryAnalysislConsumptionList(condition);
				if (CollectionUtils.isNotEmpty(loanAnalysislConsumList)) {
					analysislConsumption.setId(loanAnalysislConsumList.get(0).getId());
					//更新
					analysislConsumptionDao.updateAnalysislConsumption(analysislConsumption);
				}else{
					//新增
					analysislConsumptionDao.insertAnalysislConsumption(analysislConsumption);
				}
			}
		}catch (Exception e){
			logger.error("saveAnalysislBusinessOrConsum报错", e);
		}


	}

	private void saveAnalysislBusiness(LoanAnalysislBusiness analysislBusines, LoanApplyInfo applyInfo) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanId", applyInfo.getLoanId());
		//资产分析
		LoanAssetsInfo loanAssetsInfo = loanModule.getAssetsInfoProvider().queryOneAssetsInfoList(condition);
		ManageAssetAnalysis manageAssetAnalysis = loanFinanceAnalysisService.getManageAssetAnalysis(loanAssetsInfo,applyInfo);
		//损益分析
		LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(applyInfo.getLoanId());
		//LoanCrossCheckQuanyiquan loanCrossCheckQuanyiquan = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(loanId);
		ManageBreakEvenAnalysis manageBreakEvenAnalysis = loanFinanceAnalysisService.getManageBreakEvenAnalysis(loanProfitLossInfo, manageAssetAnalysis,applyInfo);
		//偿还能力分析
		ManageSolvencyAnalysis manageSolvencyAnalysis = loanFinanceAnalysisService.getManageSolvencyAnalysis(manageAssetAnalysis, loanAssetsInfo, loanProfitLossInfo,applyInfo);
		//周转率分析
		ManageTurnoverRatioAnalysis manageTurnoverRatioAnalysis = loanFinanceAnalysisService.getManageTurnoverRatioAnalysis(manageAssetAnalysis, manageBreakEvenAnalysis, loanAssetsInfo,applyInfo);

		analysislBusines.setLoanId(applyInfo.getLoanId());
		analysislBusines.setLoanClassId(applyInfo.getLoanClassId());
		analysislBusines.setAssetsTotalAmount(loanAssetsInfo.getAssetsTotalAmount());
		analysislBusines.setAssetsFlowAmount(manageAssetAnalysis.getFlow());
		analysislBusines.setOffAssetsAmount(loanAssetsInfo.getOffAssetsAmount());
		analysislBusines.setAssetsCashAmount(loanAssetsInfo.getAssetsCashAmount());
		analysislBusines.setAssetsStockAmount(loanAssetsInfo.getAssetsStockAmount());
		analysislBusines.setAssetsReceivableAmount(loanAssetsInfo.getAssetsReceivableAmount());
		analysislBusines.setAssetsPaymentAmount(loanAssetsInfo.getAssetsPaymentAmount());
		analysislBusines.setAssetsOperatingAmount(loanAssetsInfo.getAssetsOperatingAmount());
		analysislBusines.setAssetsNonOperatingAmount(loanAssetsInfo.getAssetsNonOperatingAmount());
		analysislBusines.setAssetsFixedAmount(loanAssetsInfo.getAssetsFixedAmount());
		analysislBusines.setAssetsOtherAmount(loanAssetsInfo.getAssetsOtherAmount());
		analysislBusines.setActualInterest(manageAssetAnalysis.getActualInterest());
		analysislBusines.setDebtsTotalAmount(loanAssetsInfo.getDebtsTotalAmount());
		analysislBusines.setDebtsFlowAmount(manageAssetAnalysis.getFlowLiabilities());
		analysislBusines.setOffDebtsAmount(loanAssetsInfo.getOffDebtsAmount());
		analysislBusines.setDebtsPayableAmount(loanAssetsInfo.getDebtsPayableAmount());
		analysislBusines.setDebtsReceivedAmount(loanAssetsInfo.getDebtsReceivedAmount());
		analysislBusines.setDebtsShortAmount(loanAssetsInfo.getDebtsShortAmount());
		analysislBusines.setDebtsLongAmount(loanAssetsInfo.getDebtsLongAmount());
		analysislBusines.setDebtsBizOtherAmount(loanAssetsInfo.getDebtsBizOtherAmount());
		analysislBusines.setAssetLiabilityRatio(manageAssetAnalysis.getAssetLiabilityRatio());

		analysislBusines.setBusinessIncomeAmount(loanProfitLossInfo.getBusinessIncomeAmount());
		analysislBusines.setOtherIncomeAmount(loanProfitLossInfo.getOtherIncomeAmount());
		analysislBusines.setFixedCostDefrayAmount(loanProfitLossInfo.getFixedCostDefrayAmount());
		analysislBusines.setTexDefrayAmount(loanProfitLossInfo.getTexDefrayAmount());
		analysislBusines.setOtherDefrayAmount(loanProfitLossInfo.getOtherDefrayAmount());
		analysislBusines.setTotalSales(manageBreakEvenAnalysis.getTotalSales());
//		analysislBusines.setGrossProfiRate(manageBreakEvenAnalysis.getGrossProfitRate());
		analysislBusines.setGrossProfiRate(loanProfitLossInfo.getGrossProfitRatio());
		analysislBusines.setGrossProfit(manageBreakEvenAnalysis.getGrossProfit());
		analysislBusines.setTotalNetProfit(manageBreakEvenAnalysis.getTotalNetProfit());
		analysislBusines.setVariableCostExpenditure(manageBreakEvenAnalysis.getVariableCostExpenditure());
		analysislBusines.setMonthlyAverageAvailable(manageBreakEvenAnalysis.getMonthlyAverageAvailable());
		analysislBusines.setNetInterestRate(manageBreakEvenAnalysis.getNetInterestRate());
		analysislBusines.setReturnOnEquity(manageBreakEvenAnalysis.getReturnOnEquity());

		analysislBusines.setCashRatio(manageSolvencyAnalysis.getCashRatio());
		analysislBusines.setFlowRatio(manageSolvencyAnalysis.getFlowRatio());
		analysislBusines.setSalesRatio(manageSolvencyAnalysis.getSalesRatio());
		analysislBusines.setQuickRatio(manageSolvencyAnalysis.getQuickRatio());

		analysislBusines.setAssetTurnover(manageTurnoverRatioAnalysis.getAssetTurnover());
		analysislBusines.setAssetTurnoverDays(manageTurnoverRatioAnalysis.getAssetTurnoverDays());
		analysislBusines.setAccountsTeceivableTurnover(manageTurnoverRatioAnalysis.getAccountsTeceivableTurnover());
		analysislBusines.setSalesOutstandingDays(manageTurnoverRatioAnalysis.getDaysSalesOutstanding());
		analysislBusines.setInventoryTurnover(manageTurnoverRatioAnalysis.getInventoryTurnover());
		analysislBusines.setInventoryTurnoverDays(manageTurnoverRatioAnalysis.getInventoryTurnoverDays());
	}

	private void saveAnalysislConsumption(LoanAnalysislConsumption analysislConsumption, LoanApplyInfo applyInfo) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanId", applyInfo.getLoanId());
		//资产分析
		LoanAssetsInfo loanAssetsInfo = loanModule.getAssetsInfoProvider().queryOneAssetsInfoList(condition);
		ConsumeAssetAnalysis consumeAssetAnalysis = loanFinanceAnalysisService.getConsumeAssetAnalysis(loanAssetsInfo);
		//损益分析
		LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(applyInfo.getLoanId());
		ConsumeBreakEvenAnalysis consumeBreakEvenAnalysis = loanFinanceAnalysisService.getConsumeBreakEvenAnalysis(loanProfitLossInfo);
		//偿还能力分析
		ConsumeSolvencyAnalysis consumeSolvencyAnalysis = loanFinanceAnalysisService.getConsumeSolvencyAnalysis(consumeAssetAnalysis);

		analysislConsumption.setLoanId(applyInfo.getLoanId());
		analysislConsumption.setLoanClassId(applyInfo.getLoanClassId());
		analysislConsumption.setAssetsTotalAmount(loanAssetsInfo.getAssetsTotalAmount());
		analysislConsumption.setAssetsCashAmount(loanAssetsInfo.getAssetsCashAmount());
		analysislConsumption.setAssetsInvestAmount(loanAssetsInfo.getAssetsInvestAmount());
		analysislConsumption.setAssetsExternalClaims(loanAssetsInfo.getAssetsExternalClaims());
		analysislConsumption.setAssetsPaymentAmount(loanAssetsInfo.getAssetsPaymentAmount());
		analysislConsumption.setAssetsFixedAmount(loanAssetsInfo.getAssetsFixedAmount());
		analysislConsumption.setAssetsOtherAmount(loanAssetsInfo.getAssetsOtherAmount());
		analysislConsumption.setActualInterest(consumeAssetAnalysis.getActualInterest());
		analysislConsumption.setDebtsTotalAmount(loanAssetsInfo.getDebtsTotalAmount());
		analysislConsumption.setDebtsConsumeAmount(loanAssetsInfo.getDebtsConsumeAmount());
		analysislConsumption.setDebtsSelfUserAmount(loanAssetsInfo.getDebtsSelfUserAmount());
		analysislConsumption.setDebtsInvestAmount(loanAssetsInfo.getDebtsInvestAmount());
		analysislConsumption.setDebtsOtherAmount(loanAssetsInfo.getDebtsOtherAmount());
		analysislConsumption.setAssetLiabilityRatio(consumeAssetAnalysis.getAssetLiabilityRatio());

		analysislConsumption.setHomeIncomeAmount(loanProfitLossInfo.getHomeIncomeAmount());
		analysislConsumption.setOtherIncomeAmount(loanProfitLossInfo.getOtherIncomeAmount());
		analysislConsumption.setFixedCostDefrayAmount(loanProfitLossInfo.getFixedCostDefrayAmount());
		analysislConsumption.setTexPersonalAmount(loanProfitLossInfo.getTexPersonalAmount());
		analysislConsumption.setOtherDefrayAmount(loanProfitLossInfo.getOtherDefrayAmount());
		analysislConsumption.setGrossIncome(consumeBreakEvenAnalysis.getGrossIncome());
		analysislConsumption.setMonthlyAverageAvailable(consumeBreakEvenAnalysis.getMonthlyAverageAvailable());

		analysislConsumption.setQuickRatio(consumeSolvencyAnalysis.getCashRatio());
	}

}
