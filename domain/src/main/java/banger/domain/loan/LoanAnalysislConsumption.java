package banger.domain.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 消费贷财务分析详情总表
 */
public class LoanAnalysislConsumption implements Serializable {
	private static final long serialVersionUID = 1517765550306325717L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private Integer loanClassId;					//贷款分类 1：经营贷，2：消费贷
	private BigDecimal assetsTotalAmount;					//总资产
	private BigDecimal assetsCashAmount;					//现金
	private BigDecimal assetsInvestAmount;					//投资性资产
	private BigDecimal assetsExternalClaims;					//对外债权
	private BigDecimal assetsPaymentAmount;					//预付账款
	private BigDecimal assetsFixedAmount;					//固定资产
	private BigDecimal assetsOtherAmount;					//其他资产
	private BigDecimal actualInterest;					//实际权益
	private BigDecimal debtsTotalAmount;					//总负债
	private BigDecimal debtsConsumeAmount;					//消费性负债
	private BigDecimal debtsSelfUserAmount;					//自用性负债
	private BigDecimal debtsInvestAmount;					//投资性负债
	private BigDecimal debtsOtherAmount;					//其他负债
	private BigDecimal assetLiabilityRatio;					//资产负债率
	private BigDecimal homeIncomeAmount;					//家庭收入
	private BigDecimal otherIncomeAmount;					//其他收入
	private BigDecimal fixedCostDefrayAmount;					//固定成本支出
	private BigDecimal texPersonalAmount;					//个人所得税
	private BigDecimal otherDefrayAmount;					//其他支出
	private BigDecimal grossIncome;					//总收入
	private BigDecimal monthlyAverageAvailable;					//月平均月可支
	private BigDecimal quickRatio;					//现金比

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getLoanId(){
		return this.loanId;
	}

	public void setLoanId(Integer loanId){
		this.loanId=loanId;
	}

	public Integer getLoanClassId(){
		return this.loanClassId;
	}

	public void setLoanClassId(Integer loanClassId){
		this.loanClassId=loanClassId;
	}

	public BigDecimal getAssetsTotalAmount(){
		return this.assetsTotalAmount;
	}

	public void setAssetsTotalAmount(BigDecimal assetsTotalAmount){
		this.assetsTotalAmount=assetsTotalAmount;
	}

	public BigDecimal getAssetsCashAmount(){
		return this.assetsCashAmount;
	}

	public void setAssetsCashAmount(BigDecimal assetsCashAmount){
		this.assetsCashAmount=assetsCashAmount;
	}

	public BigDecimal getAssetsInvestAmount(){
		return this.assetsInvestAmount;
	}

	public void setAssetsInvestAmount(BigDecimal assetsInvestAmount){
		this.assetsInvestAmount=assetsInvestAmount;
	}

	public BigDecimal getAssetsExternalClaims(){
		return this.assetsExternalClaims;
	}

	public void setAssetsExternalClaims(BigDecimal assetsExternalClaims){
		this.assetsExternalClaims=assetsExternalClaims;
	}

	public BigDecimal getAssetsPaymentAmount(){
		return this.assetsPaymentAmount;
	}

	public void setAssetsPaymentAmount(BigDecimal assetsPaymentAmount){
		this.assetsPaymentAmount=assetsPaymentAmount;
	}

	public BigDecimal getAssetsFixedAmount(){
		return this.assetsFixedAmount;
	}

	public void setAssetsFixedAmount(BigDecimal assetsFixedAmount){
		this.assetsFixedAmount=assetsFixedAmount;
	}

	public BigDecimal getAssetsOtherAmount(){
		return this.assetsOtherAmount;
	}

	public void setAssetsOtherAmount(BigDecimal assetsOtherAmount){
		this.assetsOtherAmount=assetsOtherAmount;
	}

	public BigDecimal getActualInterest(){
		return this.actualInterest;
	}

	public void setActualInterest(BigDecimal actualInterest){
		this.actualInterest=actualInterest;
	}

	public BigDecimal getDebtsTotalAmount(){
		return this.debtsTotalAmount;
	}

	public void setDebtsTotalAmount(BigDecimal debtsTotalAmount){
		this.debtsTotalAmount=debtsTotalAmount;
	}

	public BigDecimal getDebtsConsumeAmount(){
		return this.debtsConsumeAmount;
	}

	public void setDebtsConsumeAmount(BigDecimal debtsConsumeAmount){
		this.debtsConsumeAmount=debtsConsumeAmount;
	}

	public BigDecimal getDebtsSelfUserAmount(){
		return this.debtsSelfUserAmount;
	}

	public void setDebtsSelfUserAmount(BigDecimal debtsSelfUserAmount){
		this.debtsSelfUserAmount=debtsSelfUserAmount;
	}

	public BigDecimal getDebtsInvestAmount(){
		return this.debtsInvestAmount;
	}

	public void setDebtsInvestAmount(BigDecimal debtsInvestAmount){
		this.debtsInvestAmount=debtsInvestAmount;
	}

	public BigDecimal getDebtsOtherAmount(){
		return this.debtsOtherAmount;
	}

	public void setDebtsOtherAmount(BigDecimal debtsOtherAmount){
		this.debtsOtherAmount=debtsOtherAmount;
	}

	public BigDecimal getAssetLiabilityRatio(){
		return this.assetLiabilityRatio;
	}

	public void setAssetLiabilityRatio(BigDecimal assetLiabilityRatio){
		this.assetLiabilityRatio=assetLiabilityRatio;
	}

	public BigDecimal getHomeIncomeAmount(){
		return this.homeIncomeAmount;
	}

	public void setHomeIncomeAmount(BigDecimal homeIncomeAmount){
		this.homeIncomeAmount=homeIncomeAmount;
	}

	public BigDecimal getOtherIncomeAmount(){
		return this.otherIncomeAmount;
	}

	public void setOtherIncomeAmount(BigDecimal otherIncomeAmount){
		this.otherIncomeAmount=otherIncomeAmount;
	}

	public BigDecimal getFixedCostDefrayAmount(){
		return this.fixedCostDefrayAmount;
	}

	public void setFixedCostDefrayAmount(BigDecimal fixedCostDefrayAmount){
		this.fixedCostDefrayAmount=fixedCostDefrayAmount;
	}

	public BigDecimal getTexPersonalAmount(){
		return this.texPersonalAmount;
	}

	public void setTexPersonalAmount(BigDecimal texPersonalAmount){
		this.texPersonalAmount=texPersonalAmount;
	}

	public BigDecimal getOtherDefrayAmount(){
		return this.otherDefrayAmount;
	}

	public void setOtherDefrayAmount(BigDecimal otherDefrayAmount){
		this.otherDefrayAmount=otherDefrayAmount;
	}

	public BigDecimal getGrossIncome(){
		return this.grossIncome;
	}

	public void setGrossIncome(BigDecimal grossIncome){
		this.grossIncome=grossIncome;
	}

	public BigDecimal getMonthlyAverageAvailable(){
		return this.monthlyAverageAvailable;
	}

	public void setMonthlyAverageAvailable(BigDecimal monthlyAverageAvailable){
		this.monthlyAverageAvailable=monthlyAverageAvailable;
	}

	public BigDecimal getQuickRatio(){
		return this.quickRatio;
	}

	public void setQuickRatio(BigDecimal quickRatio){
		this.quickRatio=quickRatio;
	}

}
