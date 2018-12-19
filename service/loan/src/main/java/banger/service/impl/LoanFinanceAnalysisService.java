package banger.service.impl;

import banger.common.tools.StringUtil;
import banger.domain.enumerate.LoanIndustryGradeEnum;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.*;
import banger.domain.loan.finance.*;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.ILoanFinanceAnalysisService;
import banger.service.intf.IIndustryGradeexpService;
import banger.service.intf.IIndustryGuidelinesService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
@Repository
public class LoanFinanceAnalysisService implements ILoanFinanceAnalysisService {

    @Resource
    IIndustryGuidelinesService industryGuidelinesService;
    @Resource
    IIndustryGradeexpService industryGradeexpService;


    private BigDecimal changeInt(BigDecimal value) {
        if (value != null)
            return new BigDecimal(value.intValue());
        else
            return null;
    }

    public ConsumeAssetAnalysis getConsumeAssetAnalysis(LoanAssetsInfo loanAssetsInfo) {
        ConsumeAssetAnalysis consumeAssetAnalysis = new ConsumeAssetAnalysis();
        if (loanAssetsInfo != null) {
            consumeAssetAnalysis.setTotal(changeInt(loanAssetsInfo.getAssetsTotalAmount()));
            consumeAssetAnalysis.setMoney(changeInt(loanAssetsInfo.getAssetsCashAmount()));
            consumeAssetAnalysis.setInvestmentAssets(changeInt(loanAssetsInfo.getAssetsInvestAmount()));
            consumeAssetAnalysis.setExternalClaims(changeInt(loanAssetsInfo.getAssetsExternalClaims()));
            consumeAssetAnalysis.setAdvanceCharge(changeInt(loanAssetsInfo.getAssetsAdvancePaymentAmount()));
            consumeAssetAnalysis.setFixedAssets(changeInt(loanAssetsInfo.getAssetsFixedAmount()));
            consumeAssetAnalysis.setOtherAssets(changeInt(loanAssetsInfo.getAssetsOtherAmount()));
            consumeAssetAnalysis.setLiabilities(changeInt(loanAssetsInfo.getDebtsTotalAmount()));
            consumeAssetAnalysis.setPersonalLiabilities(changeInt(loanAssetsInfo.getDebtsSelfUserAmount()));
            consumeAssetAnalysis.setInvestmentLiability(changeInt(loanAssetsInfo.getDebtsInvestAmount()));
            consumeAssetAnalysis.setOtherLiability(changeInt(loanAssetsInfo.getDebtsOtherAmount()));
            consumeAssetAnalysis.setConsumerLiabilities(changeInt(loanAssetsInfo.getDebtsConsumeAmount()));
            consumeAssetAnalysis.setActualInterest(OperationUtil.minus(consumeAssetAnalysis.getTotal(), consumeAssetAnalysis.getLiabilities()));
            consumeAssetAnalysis.setAssetLiabilityRatio(OperationUtil.depreciation(consumeAssetAnalysis.getTotal(), 1, consumeAssetAnalysis.getLiabilities()));

        }
        return consumeAssetAnalysis;
    }

    public ConsumeBreakEvenAnalysis getConsumeBreakEvenAnalysis(LoanProfitLossInfo loanProfitLossInfo) {
        ConsumeBreakEvenAnalysis consumeBreakEvenAnalysis = new ConsumeBreakEvenAnalysis();
        if (loanProfitLossInfo != null) {
            consumeBreakEvenAnalysis.setHouseholdIncome(changeInt(loanProfitLossInfo.getHomeIncomeAmount()));
            consumeBreakEvenAnalysis.setOtherIncome(changeInt(loanProfitLossInfo.getOtherIncomeAmount()));
            consumeBreakEvenAnalysis.setFixedExpenditure(changeInt(loanProfitLossInfo.getFixedDefrayAmount()));
            consumeBreakEvenAnalysis.setIndividualIncomeTax(changeInt(loanProfitLossInfo.getTexPersonalAmount()));
            consumeBreakEvenAnalysis.setOtherExpenditure(changeInt(loanProfitLossInfo.getOtherDefrayAmount()));
            consumeBreakEvenAnalysis.setGrossIncome(changeInt(loanProfitLossInfo.getHomeIncomeAmount()));
            consumeBreakEvenAnalysis.setMonthlyAverageAvailable(OperationUtil.getMonthlyAverageAvailable(consumeBreakEvenAnalysis.getHouseholdIncome(),
                    consumeBreakEvenAnalysis.getOtherIncome(), consumeBreakEvenAnalysis.getFixedExpenditure(),
                    consumeBreakEvenAnalysis.getIndividualIncomeTax(), consumeBreakEvenAnalysis.getOtherExpenditure(), getMonth(loanProfitLossInfo)));
        }
        return consumeBreakEvenAnalysis;
    }

    public ConsumeSolvencyAnalysis getConsumeSolvencyAnalysis(ConsumeAssetAnalysis consumeAssetAnalysis) {
        ConsumeSolvencyAnalysis consumeSolvencyAnalysis = new ConsumeSolvencyAnalysis();
        if (consumeAssetAnalysis != null) {
            consumeSolvencyAnalysis.setCashRatio(OperationUtil.depreciation(consumeAssetAnalysis.getLiabilities(), 1, consumeAssetAnalysis.getMoney()));
        }
        return consumeSolvencyAnalysis;
    }

    public ManageAssetAnalysis getManageAssetAnalysis(LoanAssetsInfo loanAssetsInfo, LoanApplyInfo applyInfo) {
        ManageAssetAnalysis manageAssetAnalysis = new ManageAssetAnalysis();
        if (loanAssetsInfo != null){
            manageAssetAnalysis.setTotal(changeInt(loanAssetsInfo.getAssetsTotalAmount()));
            manageAssetAnalysis.setFlow(changeInt(OperationUtil.plus(loanAssetsInfo.getAssetsCashAmount(), loanAssetsInfo.getAssetsStockAmount(), loanAssetsInfo.getAssetsReceivableAmount(), loanAssetsInfo.getAssetsPaymentAmount())));
            manageAssetAnalysis.setFlowRatio(OperationUtil.depreciation(manageAssetAnalysis.getTotal(),1, manageAssetAnalysis.getFlow()));
            manageAssetAnalysis.setLiabilities(changeInt(loanAssetsInfo.getDebtsTotalAmount()));
            manageAssetAnalysis.setFlowLiabilities(changeInt(OperationUtil.plus(loanAssetsInfo.getDebtsPayableAmount(), loanAssetsInfo.getDebtsReceivedAmount(), loanAssetsInfo.getDebtsShortAmount())));
            manageAssetAnalysis.setFlowLiabilitiesRatio(OperationUtil.depreciation(manageAssetAnalysis.getLiabilities(), 1, manageAssetAnalysis.getFlowLiabilities()));
            manageAssetAnalysis.setActualInterest(changeInt(OperationUtil.minus(manageAssetAnalysis.getTotal(), manageAssetAnalysis.getLiabilities())));
            manageAssetAnalysis.setAssetLiabilityRatio(OperationUtil.depreciation(manageAssetAnalysis.getTotal(), 1, manageAssetAnalysis.getLiabilities()));
            Map<String, Object> map = new HashedMap();
            if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                //调查阶段需要实时计算
                map.put("gradeTerms",applyInfo.getLoanBusinessCatalog());
                map.put("itemName", LoanIndustryGradeEnum.资产_负债率.name);
                //根据行业总称,itemName查询行业指引表
                List<LoanIndustryGuidelines> loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                //最多一条
                BigDecimal ratio = manageAssetAnalysis.getAssetLiabilityRatio();
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);
                    //资产负债率
                    if (ratio.doubleValue() <= industry.getValue1().doubleValue()) {
                        //优秀
                        manageAssetAnalysis.setAssetLiabilityRatioExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("优秀");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("great");
                    } else if (ratio.doubleValue() <= industry.getValue2().doubleValue() && ratio.doubleValue() > industry.getValue1().doubleValue()) {
                        //良好
                        manageAssetAnalysis.setAssetLiabilityRatioExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("良好");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("good");
                    } else if (ratio.doubleValue() < industry.getValue3().doubleValue() && ratio.doubleValue() > industry.getValue2().doubleValue()) {
                        //一般
                        manageAssetAnalysis.setAssetLiabilityRatioExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("一般");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("well");
                    } else if (ratio.doubleValue() == industry.getValue3().doubleValue()) {
                        //一般
                        manageAssetAnalysis.setAssetLiabilityRatioExp("与平均值持平");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("一般");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("well");
                    } else if (ratio.doubleValue() <= industry.getValue4().doubleValue() && ratio.doubleValue() > industry.getValue3().doubleValue()) {
                        //一般
                        manageAssetAnalysis.setAssetLiabilityRatioExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("一般");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("well");
                    } else if (ratio.doubleValue() <= industry.getValue5().doubleValue() && ratio.doubleValue() > industry.getValue4().doubleValue()) {
                        //较低
                        manageAssetAnalysis.setAssetLiabilityRatioExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("较低");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("bad");
                    } else if (ratio.doubleValue() > industry.getValue5().doubleValue()) {
                        //较差
                        manageAssetAnalysis.setAssetLiabilityRatioExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageAssetAnalysis.setAssetLiabilityRatioGrade("较差");
                        manageAssetAnalysis.setAssetLiabilityRatioGradeEn("trouble");
                    }
                }
            }else if(StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    !LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())){
                map.put("loanId", applyInfo.getLoanId());
                map.put("itemColumn", LoanIndustryGradeEnum.资产_负债率.item);
                List<LoanIndustryGradeexp> loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                //最多一条
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageAssetAnalysis.setAssetLiabilityRatioExp(gradeexp.getItemGradeexp());
                    manageAssetAnalysis.setAssetLiabilityRatioGrade(gradeexp.getItemGradecn());
                    manageAssetAnalysis.setAssetLiabilityRatioGradeEn(gradeexp.getItemGradeen());
                }
            }
        }
        return manageAssetAnalysis;
    }

    public ManageBreakEvenAnalysis getManageBreakEvenAnalysis(LoanProfitLossInfo loanProfitLossInfo, ManageAssetAnalysis manageAssetAnalysis, LoanApplyInfo applyInfo) {
        ManageBreakEvenAnalysis manageBreakEvenAnalysis = new ManageBreakEvenAnalysis();
        if (loanProfitLossInfo != null) {
            //    总销售额	总销售额=各项营业收入总和
            manageBreakEvenAnalysis.setTotalSales(changeInt(loanProfitLossInfo.getBusinessIncomeAmount()));
            //    毛利率	毛利率=各项加权毛利率总和
            manageBreakEvenAnalysis.setGrossProfitRate(changeInt(loanProfitLossInfo.getGrossProfitRatio()));
            //    总毛利润	总毛利润=总销售额*毛利率（四舍五入，不保留小数）
            manageBreakEvenAnalysis.setGrossProfit(changeInt(OperationUtil.multiplyByRatio(manageBreakEvenAnalysis.getTotalSales(), manageBreakEvenAnalysis.getGrossProfitRate())));
            //    总净利润	总净利润=总毛利润+其他收入-固定支出-所得税-其他支出
            manageBreakEvenAnalysis.setTotalNetProfit(changeInt(
                    OperationUtil.getTotalNetProfit(manageBreakEvenAnalysis.getGrossProfit(), loanProfitLossInfo.getOtherIncomeAmount(),
                            loanProfitLossInfo.getFixedCostDefrayAmount(), loanProfitLossInfo.getTexDefrayAmount(), loanProfitLossInfo.getOtherDefrayAmount())));
            //    可变成本支出	可变成本支出=总销售额-总毛利润
            manageBreakEvenAnalysis.setVariableCostExpenditure(changeInt(OperationUtil.minus(manageBreakEvenAnalysis.getTotalSales(),manageBreakEvenAnalysis.getGrossProfit())));
            //    月平均月可支	月平均月可支=总净利润/月数（四舍五入，不保留小数）
            manageBreakEvenAnalysis.setMonthlyAverageAvailable(changeInt(OperationUtil.divide(new BigDecimal(getMonth(loanProfitLossInfo)), 0, manageBreakEvenAnalysis.getTotalNetProfit())));
            //    净利率	净利率=总净利润/总销售额*100%（四舍五入，保留1位小数）	比较行业指引数据
            manageBreakEvenAnalysis.setNetInterestRate(OperationUtil.depreciation(manageBreakEvenAnalysis.getTotalSales(),1, manageBreakEvenAnalysis.getTotalNetProfit()));

            Map<String, Object> map = new HashedMap();
            if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog())&&
                    LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                map.put("gradeTerms",applyInfo.getLoanBusinessCatalog());
                map.put("itemName", LoanIndustryGradeEnum.净利率.name);
                //根据行业总称,itemName查询行业指引表
                List<LoanIndustryGuidelines> loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                //最多一条
                BigDecimal ratio = manageBreakEvenAnalysis.getNetInterestRate();
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);
                    //净利率
                    if (ratio.doubleValue() >= industry.getValue1().doubleValue()) {
                        //优秀
                        manageBreakEvenAnalysis.setNetInterestRateExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) + "%");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("优秀");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("great");
                    } else if (ratio.doubleValue() >= industry.getValue2().doubleValue() && ratio.doubleValue() < industry.getValue1().doubleValue()) {
                        //良好
                        manageBreakEvenAnalysis.setNetInterestRateExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) + "%");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("良好");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("good");
                    } else if (ratio.doubleValue() > industry.getValue3().doubleValue() && ratio.doubleValue() < industry.getValue2().doubleValue()) {
                        //一般
                        manageBreakEvenAnalysis.setNetInterestRateExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) + "%");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("一般");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("well");
                    } else if (ratio.doubleValue() == industry.getValue3().doubleValue()) {
                        //一般
                        manageBreakEvenAnalysis.setNetInterestRateExp("与平均值持平");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("一般");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue4().doubleValue() && ratio.doubleValue() < industry.getValue3().doubleValue()) {
                        //一般
                        manageBreakEvenAnalysis.setNetInterestRateExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) + "%");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("一般");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue5().doubleValue() && ratio.doubleValue() < industry.getValue4().doubleValue()) {
                        //较低
                        manageBreakEvenAnalysis.setNetInterestRateExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) + "%");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("较低");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("bad");
                    } else if (ratio.doubleValue() < industry.getValue5().doubleValue()) {
                        //较差
                        manageBreakEvenAnalysis.setNetInterestRateExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) + "%");
                        manageBreakEvenAnalysis.setNetInterestRateGrade("较差");
                        manageBreakEvenAnalysis.setNetInterestRateGradeEn("trouble");
                    }
                }
            }else if(StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    !LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())){
                map.put("loanId", applyInfo.getLoanId());
                map.put("itemColumn", LoanIndustryGradeEnum.净利率.item);
                List<LoanIndustryGradeexp> loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                //最多一条
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageBreakEvenAnalysis.setNetInterestRateExp(gradeexp.getItemGradeexp());
                    manageBreakEvenAnalysis.setNetInterestRateGrade(gradeexp.getItemGradecn());
                    manageBreakEvenAnalysis.setNetInterestRateGradeEn(gradeexp.getItemGradeen());
                }
            }
        }
        if (manageAssetAnalysis != null) {
            //    净资产收益率	净资产收益率=总净利润/实际权益*100%，	比较行业指引数据
            manageBreakEvenAnalysis.setReturnOnEquity(OperationUtil.depreciation(manageAssetAnalysis.getActualInterest(), 0, manageBreakEvenAnalysis.getTotalNetProfit()));

            Map<String, Object> map = new HashedMap();
            if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                map.put("gradeTerms",applyInfo.getLoanBusinessCatalog());
                map.put("itemName", LoanIndustryGradeEnum.净资产收益率.name);
                //根据行业总称,itemName查询行业指引表
                List<LoanIndustryGuidelines> loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                //最多一条
                BigDecimal ratio = manageBreakEvenAnalysis.getReturnOnEquity();
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);
                    //净资产收益率
                    if (ratio.doubleValue() >= industry.getValue1().doubleValue()) {
                        //优秀
                        manageBreakEvenAnalysis.setReturnOnEquityExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("优秀");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("great");
                    } else if (ratio.doubleValue() >= industry.getValue2().doubleValue() && ratio.doubleValue() < industry.getValue1().doubleValue()) {
                        //良好
                        manageBreakEvenAnalysis.setReturnOnEquityExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("良好");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("good");
                    } else if (ratio.doubleValue() > industry.getValue3().doubleValue() && ratio.doubleValue() < industry.getValue2().doubleValue()) {
                        //一般
                        manageBreakEvenAnalysis.setReturnOnEquityExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("一般");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("well");
                    } else if (ratio.doubleValue() == industry.getValue3().doubleValue()) {
                        //一般
                        manageBreakEvenAnalysis.setReturnOnEquityExp("与平均值持平");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("一般");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue4().doubleValue() && ratio.doubleValue() < industry.getValue3().doubleValue()) {
                        //一般
                        manageBreakEvenAnalysis.setReturnOnEquityExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("一般");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue5().doubleValue() && ratio.doubleValue() < industry.getValue4().doubleValue()) {
                        //较低
                        manageBreakEvenAnalysis.setReturnOnEquityExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("较低");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("bad");
                    } else if (ratio.doubleValue() < industry.getValue5().doubleValue()) {
                        //较差
                        manageBreakEvenAnalysis.setReturnOnEquityExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageBreakEvenAnalysis.setReturnOnEquityGrade("较差");
                        manageBreakEvenAnalysis.setReturnOnEquityGradeEn("trouble");
                    }
                }
            } else if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    !LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                map.put("loanId", applyInfo.getLoanId());
                map.put("itemColumn", LoanIndustryGradeEnum.净资产收益率.item);
                List<LoanIndustryGradeexp> loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                //最多一条
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageBreakEvenAnalysis.setReturnOnEquityExp(gradeexp.getItemGradeexp());
                    manageBreakEvenAnalysis.setReturnOnEquityGrade(gradeexp.getItemGradecn());
                    manageBreakEvenAnalysis.setReturnOnEquityGradeEn(gradeexp.getItemGradeen());
                }
            }
        }
        return manageBreakEvenAnalysis;
    }

    private int getMonth(LoanProfitLossInfo loanProfitLossInfo){
        int result = 0;
        try {
            Calendar c1 = Calendar.getInstance();
            c1.set(loanProfitLossInfo.getYearStart(), loanProfitLossInfo.getMonthStart(), 1);
            Calendar c2 = Calendar.getInstance();
            c2.set(loanProfitLossInfo.getYearEnd(), loanProfitLossInfo.getMonthEnd(), 1);
            int i = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
            int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
            result = Math.abs(month + i);
            //TODO bug
        } catch (Exception e) {
            return 0;
        }
        return result == 0 ? 1 : Math.abs(result);
    }

    public ManageSolvencyAnalysis getManageSolvencyAnalysis(ManageAssetAnalysis manageAssetAnalysis, LoanAssetsInfo loanAssetsInfo, LoanProfitLossInfo loanProfitLossInfo, LoanApplyInfo applyInfo) {
        ManageSolvencyAnalysis manageSolvencyAnalysis = new ManageSolvencyAnalysis();
        if (manageAssetAnalysis != null && loanAssetsInfo != null && loanProfitLossInfo != null) {
            manageSolvencyAnalysis.setCashRatio(OperationUtil.depreciation(manageAssetAnalysis.getFlowLiabilities(), 1, loanAssetsInfo.getAssetsCashAmount()));
            manageSolvencyAnalysis.setFlowRatio(OperationUtil.depreciation(manageAssetAnalysis.getFlowLiabilities(), 1, manageAssetAnalysis.getFlow()));
            manageSolvencyAnalysis.setSalesRatio(OperationUtil.getSalesRatio(loanAssetsInfo.getDebtsShortAmount(), loanAssetsInfo.getDebtsLongAmount(), loanProfitLossInfo.getBusinessIncomeAmount()));
            manageSolvencyAnalysis.setQuickRatio(OperationUtil.getQuickRatio(manageAssetAnalysis.getFlow(), loanAssetsInfo.getAssetsStockAmount(), manageAssetAnalysis.getFlowLiabilities()));

            Map<String, Object> map = new HashedMap();
            if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                map.put("gradeTerms",applyInfo.getLoanBusinessCatalog());
                map.put("itemName", LoanIndustryGradeEnum.速动比.name);
                //根据行业总称,itemName查询行业指引表
                List<LoanIndustryGuidelines> loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                //最多一条
                BigDecimal ratio = manageSolvencyAnalysis.getQuickRatio();
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);
                    //速动比
                    if (ratio.doubleValue() >= industry.getValue1().doubleValue()) {
                        //优秀
                        manageSolvencyAnalysis.setQuickRatioExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageSolvencyAnalysis.setQuickRatioGrade("优秀");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("great");
                    } else if (ratio.doubleValue() >= industry.getValue2().doubleValue() && ratio.doubleValue() < industry.getValue1().doubleValue()) {
                        //良好
                        manageSolvencyAnalysis.setQuickRatioExp("高于平均值" + OperationUtil.minus(ratio, 1,  industry.getValue3()) +"%");
                        manageSolvencyAnalysis.setQuickRatioGrade("良好");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("good");
                    } else if (ratio.doubleValue() > industry.getValue3().doubleValue() && ratio.doubleValue() < industry.getValue2().doubleValue()) {
                        //一般
                        manageSolvencyAnalysis.setQuickRatioExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()) +"%");
                        manageSolvencyAnalysis.setQuickRatioGrade("一般");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("well");
                    } else if (ratio.doubleValue() == industry.getValue3().doubleValue()) {
                        //一般
                        manageSolvencyAnalysis.setQuickRatioExp("与平均值持平");
                        manageSolvencyAnalysis.setQuickRatioGrade("一般");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("well");
                    }else if (ratio.doubleValue() >= industry.getValue4().doubleValue() && ratio.doubleValue() < industry.getValue3().doubleValue()) {
                        //一般
                        manageSolvencyAnalysis.setQuickRatioExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageSolvencyAnalysis.setQuickRatioGrade("一般");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue5().doubleValue() && ratio.doubleValue() < industry.getValue4().doubleValue()) {
                        //较低
                        manageSolvencyAnalysis.setQuickRatioExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageSolvencyAnalysis.setQuickRatioGrade("较低");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("bad");
                    } else if (ratio.doubleValue() < industry.getValue5().doubleValue()) {
                        //较差
                        manageSolvencyAnalysis.setQuickRatioExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio) +"%");
                        manageSolvencyAnalysis.setQuickRatioGrade("较差");
                        manageSolvencyAnalysis.setQuickRatioGradeEn("trouble");
                    }
                }
            } else if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    !LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                map.put("loanId", applyInfo.getLoanId());
                map.put("itemColumn", LoanIndustryGradeEnum.净资产收益率.item);
                List<LoanIndustryGradeexp> loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                //最多一条
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageSolvencyAnalysis.setQuickRatioExp(gradeexp.getItemGradeexp());
                    manageSolvencyAnalysis.setQuickRatioGrade(gradeexp.getItemGradecn());
                    manageSolvencyAnalysis.setQuickRatioGradeEn(gradeexp.getItemGradeen());
                }
            }
        }
        return manageSolvencyAnalysis;
    }

    public ManageTurnoverRatioAnalysis getManageTurnoverRatioAnalysis(ManageAssetAnalysis manageAssetAnalysis, ManageBreakEvenAnalysis manageBreakEvenAnalysis, LoanAssetsInfo loanAssetsInfo, LoanApplyInfo applyInfo) {
        ManageTurnoverRatioAnalysis manageTurnoverRatioAnalysis = new ManageTurnoverRatioAnalysis();
        if (manageAssetAnalysis != null && loanAssetsInfo != null && manageBreakEvenAnalysis != null) {
            manageTurnoverRatioAnalysis.setAssetTurnover(OperationUtil.divide(loanAssetsInfo.getAssetsTotalAmount(), 1, manageBreakEvenAnalysis.getTotalSales()));
            manageTurnoverRatioAnalysis.setAssetTurnoverDays(OperationUtil.divide(manageTurnoverRatioAnalysis.getAssetTurnover(), 0, new BigDecimal("360")));
            manageTurnoverRatioAnalysis.setAccountsTeceivableTurnover(OperationUtil.divide(loanAssetsInfo.getAssetsReceivableAmount(), 1, manageBreakEvenAnalysis.getTotalSales()));
            manageTurnoverRatioAnalysis.setDaysSalesOutstanding(OperationUtil.divide(manageTurnoverRatioAnalysis.getAccountsTeceivableTurnover(), 0, new BigDecimal("360")));
            manageTurnoverRatioAnalysis.setInventoryTurnover(OperationUtil.divide(loanAssetsInfo.getAssetsStockAmount(),1,manageBreakEvenAnalysis.getVariableCostExpenditure()));
            manageTurnoverRatioAnalysis.setInventoryTurnoverDays(OperationUtil.divide(manageTurnoverRatioAnalysis.getInventoryTurnover(), 0, new BigDecimal("360")));

            Map<String, Object> map = new HashedMap();
            if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                List<LoanIndustryGuidelines> loanIndustryGuidelines = null;

                map.put("gradeTerms",applyInfo.getLoanBusinessCatalog());
                map.put("itemName", LoanIndustryGradeEnum.总资产周转率.name);

                //根据行业总称,itemName查询行业指引表
                loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                //最多一条
                BigDecimal ratio = manageTurnoverRatioAnalysis.getAssetTurnover();
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);
                    //资产周转率
                    if (ratio.doubleValue() >= industry.getValue1().doubleValue()) {
                        //优秀
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("优秀");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("great");
                    } else if (ratio.doubleValue() >= industry.getValue2().doubleValue() && ratio.doubleValue() < industry.getValue1().doubleValue()) {
                        //良好
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("良好");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("good");
                    } else if (ratio.doubleValue() > industry.getValue3().doubleValue() && ratio.doubleValue() < industry.getValue2().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("well");
                    } else if (ratio.doubleValue()== industry.getValue3().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("与平均值持平");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue4().doubleValue() && ratio.doubleValue() < industry.getValue3().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue5().doubleValue() && ratio.doubleValue() < industry.getValue4().doubleValue()) {
                        //较低
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("较低");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("bad");
                    } else if (ratio.doubleValue() < industry.getValue5().doubleValue()) {
                        //较差
                        manageTurnoverRatioAnalysis.setAssetTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setAssetTurnoverGrade("较差");
                        manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn("trouble");
                    }
                }

                //应收账款周转率
                map.put("itemName", LoanIndustryGradeEnum.应收账款周转率.name);
                //根据行业总称,itemName查询行业指引表
                loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                ratio = manageTurnoverRatioAnalysis.getAccountsTeceivableTurnover();
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);
                    if (ratio.doubleValue() >= industry.getValue1().doubleValue()) {
                        //优秀
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("优秀");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("great");
                    } else if (ratio.doubleValue() >= industry.getValue2().doubleValue() && ratio.doubleValue() < industry.getValue1().doubleValue()) {
                        //良好
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("良好");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("good");
                    } else if (ratio.doubleValue() > industry.getValue3().doubleValue() && ratio.doubleValue() < industry.getValue2().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() == industry.getValue3().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("与平均值持平");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue4().doubleValue() && ratio.doubleValue() < industry.getValue3().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue5().doubleValue() && ratio.doubleValue() < industry.getValue4().doubleValue()) {
                        //较低
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("较低");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("bad");
                    } else if (ratio.doubleValue() < industry.getValue5().doubleValue()) {
                        //较差
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade("较差");
                        manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn("trouble");
                    }
                }

                //存货周转率
                map.put("itemName", LoanIndustryGradeEnum.存货周转率.name);
                //根据行业总称,itemName查询行业指引表
                ratio = manageTurnoverRatioAnalysis.getInventoryTurnover();
                loanIndustryGuidelines = industryGuidelinesService.queryLoanIndustryGuidelinesListByCondition(map);
                if (loanIndustryGuidelines.size() > 0 && ratio != null) {
                    LoanIndustryGuidelines industry = loanIndustryGuidelines.get(0);

                    if (ratio.doubleValue() >= industry.getValue1().doubleValue()) {
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("高于平均值" + OperationUtil.minus(ratio, 1,  industry.getValue3()));
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("优秀");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("great");
                    } else if (ratio.doubleValue() >= industry.getValue2().doubleValue() && ratio.doubleValue() < industry.getValue1().doubleValue()) {
                        //良好
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("高于平均值" + OperationUtil.minus(ratio,  1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("良好");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("good");
                    } else if (ratio.doubleValue() > industry.getValue3().doubleValue() && ratio.doubleValue() < industry.getValue2().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("高于平均值" + OperationUtil.minus(ratio,  1, industry.getValue3()));
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("well");
                    }else if (ratio.doubleValue() == industry.getValue3().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("与平均值持平");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue4().doubleValue() && ratio.doubleValue() < industry.getValue3().doubleValue()) {
                        //一般
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("一般");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("well");
                    } else if (ratio.doubleValue() >= industry.getValue5().doubleValue() && ratio.doubleValue() < industry.getValue4().doubleValue()) {
                        //较低
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("较低");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("bad");
                    } else if (ratio.doubleValue() < industry.getValue5().doubleValue()) {
                        //较差
                        manageTurnoverRatioAnalysis.setInventoryTurnoverExp("低于平均值" + OperationUtil.minus(industry.getValue3(), 1, ratio));
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGrade("较差");
                        manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn("trouble");
                    }
                }
            }else if (StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()) &&
                    !LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())) {
                List<LoanIndustryGradeexp> loanIndustryGradeexps = null;

                //资产周转率
                map.put("loanId", applyInfo.getLoanId());
                map.put("itemColumn", LoanIndustryGradeEnum.总资产周转率.item);
                loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                //最多一条
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageTurnoverRatioAnalysis.setAssetTurnoverExp(gradeexp.getItemGradeexp());
                    manageTurnoverRatioAnalysis.setAssetTurnoverGrade(gradeexp.getItemGradecn());
                    manageTurnoverRatioAnalysis.setAssetTurnoverGradeEn(gradeexp.getItemGradeen());
                }
                //应收账款周转率
                map.put("itemColumn", LoanIndustryGradeEnum.应收账款周转率.item);
                loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverExp(gradeexp.getItemGradeexp());
                    manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGrade(gradeexp.getItemGradecn());
                    manageTurnoverRatioAnalysis.setAccountsTeceivableTurnoverGradeEn(gradeexp.getItemGradeen());
                }

                //存货周转率
                map.put("itemColumn", LoanIndustryGradeEnum.存货周转率.item);
                loanIndustryGradeexps = industryGradeexpService.queryIndustryGradeexpList(map);
                if (loanIndustryGradeexps.size() > 0) {
                    LoanIndustryGradeexp gradeexp = loanIndustryGradeexps.get(0);
                    manageTurnoverRatioAnalysis.setInventoryTurnoverExp(gradeexp.getItemGradeexp());
                    manageTurnoverRatioAnalysis.setInventoryTurnoverGrade(gradeexp.getItemGradecn());
                    manageTurnoverRatioAnalysis.setInventoryTurnoverGradeEn(gradeexp.getItemGradeen());
                }
            }
        }
        return manageTurnoverRatioAnalysis;
    }
}
