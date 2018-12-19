package banger.domain.loan.finance;

import java.math.BigDecimal;

/**
 * 经营类损益分析
 * Created by Administrator on 2017/8/21.
 */
public class ManageBreakEvenAnalysis {

    //    总销售额	总销售额=各项营业收入总和
    private BigDecimal totalSales;
    //    毛利率	毛利率=各项加权毛利率总和
    private BigDecimal grossProfitRate;
    //    总毛利润	总毛利润=总销售额*毛利率（四舍五入，不保留小数）
    private BigDecimal grossProfit;
    //    总净利润	总净利润=总毛利润+其他收入-固定支出-所得税-其他支出
    private BigDecimal totalNetProfit;
    //    可变成本支出	可变成本支出=总销售额-总毛利润
    private BigDecimal variableCostExpenditure;
    //    月平均月可支	月平均月可支=总净利润/月数（四舍五入，不保留小数）
    private BigDecimal monthlyAverageAvailable;
    //    净利率	净利率=总净利润/总销售额*100%（四舍五入，保留1位小数）	比较行业指引数据
    private BigDecimal netInterestRate;
    //    净利率	比较行业指引数据说明
    private String netInterestRateExp;
    //    净利率	比较行业指引数据等级
    private String netInterestRateGrade;
    //    净利率	比较行业指引数据等级英文
    private String netInterestRateGradeEn;
    //    净资产收益率	净资产收益率=总净利润/实际权益*100%，	比较行业指引数据
    private BigDecimal returnOnEquity;
    //    净资产收益率 比较行业指引数据说明
    private String returnOnEquityExp;
    //    净资产收益率 比较行业指引数据等级
    private String returnOnEquityGrade;
    //    净资产收益率 比较行业指引数据等级英文
    private String returnOnEquityGradeEn;

    public String getNetInterestRateGradeEn() {
        return netInterestRateGradeEn;
    }

    public void setNetInterestRateGradeEn(String netInterestRateGradeEn) {
        this.netInterestRateGradeEn = netInterestRateGradeEn;
    }

    public String getReturnOnEquityGradeEn() {
        return returnOnEquityGradeEn;
    }

    public void setReturnOnEquityGradeEn(String returnOnEquityGradeEn) {
        this.returnOnEquityGradeEn = returnOnEquityGradeEn;
    }

    public String getNetInterestRateExp() {
        return netInterestRateExp;
    }

    public void setNetInterestRateExp(String netInterestRateExp) {
        this.netInterestRateExp = netInterestRateExp;
    }

    public String getNetInterestRateGrade() {
        return netInterestRateGrade;
    }

    public void setNetInterestRateGrade(String netInterestRateGrade) {
        this.netInterestRateGrade = netInterestRateGrade;
    }

    public String getReturnOnEquityExp() {
        return returnOnEquityExp;
    }

    public void setReturnOnEquityExp(String returnOnEquityExp) {
        this.returnOnEquityExp = returnOnEquityExp;
    }

    public String getReturnOnEquityGrade() {
        return returnOnEquityGrade;
    }

    public void setReturnOnEquityGrade(String returnOnEquityGrade) {
        this.returnOnEquityGrade = returnOnEquityGrade;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getGrossProfitRate() {
        return grossProfitRate == null ? new BigDecimal("0") : grossProfitRate;
    }

    public void setGrossProfitRate(BigDecimal grossProfitRate) {
        this.grossProfitRate = grossProfitRate;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public BigDecimal getTotalNetProfit() {
        return totalNetProfit;
    }

    public void setTotalNetProfit(BigDecimal totalNetProfit) {
        this.totalNetProfit = totalNetProfit;
    }

    public BigDecimal getVariableCostExpenditure() {
        return variableCostExpenditure;
    }

    public void setVariableCostExpenditure(BigDecimal variableCostExpenditure) {
        this.variableCostExpenditure = variableCostExpenditure;
    }

    public BigDecimal getMonthlyAverageAvailable() {
        return monthlyAverageAvailable;
    }

    public void setMonthlyAverageAvailable(BigDecimal monthlyAverageAvailable) {
        this.monthlyAverageAvailable = monthlyAverageAvailable;
    }

    public BigDecimal getNetInterestRate() {
        return netInterestRate;
    }

    public void setNetInterestRate(BigDecimal netInterestRate) {
        this.netInterestRate = netInterestRate;
    }

    public BigDecimal getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(BigDecimal returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }
}
