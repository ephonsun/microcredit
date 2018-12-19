package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 资产负责表信息表
 */
public class LoanAssetsInfo implements Serializable {
	private static final long serialVersionUID = 7130412823852260652L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private Integer loanClassId;					//贷款分类ID1 经营类2 消费类
	private BigDecimal assetsTotalAmount;					//总资产
	private BigDecimal assetsCashAmount;					//现金
	private BigDecimal assetsStockAmount;					//存货
	private BigDecimal assetsReceivableAmount;					//应收账款
	private BigDecimal assetsPaymentAmount;					//预付账款
	private BigDecimal assetsOperatingAmount;					//其他经营资产
	private BigDecimal assetsNonOperatingAmount;					//其他非经营资产
	private BigDecimal assetsFixedAmount;					//固定资产
	private BigDecimal assetsOtherAmount;					//其他资产
	private BigDecimal assetsInvestAmount;					//投资性资产
	private BigDecimal assetsExternalClaims;					//对外债权
	private BigDecimal debtsTotalAmount;					//总负债
	private BigDecimal debtsPayableAmount;					//应付账款
	private BigDecimal debtsReceivedAmount;					//预收账款
	private BigDecimal debtsShortAmount;					//短期负债
	private BigDecimal debtsLongAmount;					//长期负债
	private BigDecimal debtsInvestAmount;					//投资性负债
	private BigDecimal debtsSelfUserAmount;					//自用性负债
	private BigDecimal debtsConsumeAmount;					//消费性负债
	private BigDecimal debtsOtherAmount;					//其他负债
	private BigDecimal offAssetsAmount;					//表外资产
	private BigDecimal offDebtsAmount;					//表外负债
	private String offRemark;					//表外备注
	private Date surveyDate;					//调查日期
	private BigDecimal assetsAdvancePaymentAmount;					//预付款
	private BigDecimal debtsBizOtherAmount;					//经营类其他负债
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户

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

	public BigDecimal getAssetsStockAmount(){
		return this.assetsStockAmount;
	}

	public void setAssetsStockAmount(BigDecimal assetsStockAmount){
		this.assetsStockAmount=assetsStockAmount;
	}

	public BigDecimal getAssetsReceivableAmount(){
		return this.assetsReceivableAmount;
	}

	public void setAssetsReceivableAmount(BigDecimal assetsReceivableAmount){
		this.assetsReceivableAmount=assetsReceivableAmount;
	}

	public BigDecimal getAssetsPaymentAmount(){
		return this.assetsPaymentAmount;
	}

	public void setAssetsPaymentAmount(BigDecimal assetsPaymentAmount){
		this.assetsPaymentAmount=assetsPaymentAmount;
	}

	public BigDecimal getAssetsOperatingAmount(){
		return this.assetsOperatingAmount;
	}

	public void setAssetsOperatingAmount(BigDecimal assetsOperatingAmount){
		this.assetsOperatingAmount=assetsOperatingAmount;
	}

	public BigDecimal getAssetsNonOperatingAmount(){
		return this.assetsNonOperatingAmount;
	}

	public void setAssetsNonOperatingAmount(BigDecimal assetsNonOperatingAmount){
		this.assetsNonOperatingAmount=assetsNonOperatingAmount;
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

	public BigDecimal getDebtsTotalAmount(){
		return this.debtsTotalAmount;
	}

	public void setDebtsTotalAmount(BigDecimal debtsTotalAmount){
		this.debtsTotalAmount=debtsTotalAmount;
	}

	public BigDecimal getDebtsPayableAmount(){
		return this.debtsPayableAmount;
	}

	public void setDebtsPayableAmount(BigDecimal debtsPayableAmount){
		this.debtsPayableAmount=debtsPayableAmount;
	}

	public BigDecimal getDebtsReceivedAmount(){
		return this.debtsReceivedAmount;
	}

	public void setDebtsReceivedAmount(BigDecimal debtsReceivedAmount){
		this.debtsReceivedAmount=debtsReceivedAmount;
	}

	public BigDecimal getDebtsShortAmount(){
		return this.debtsShortAmount;
	}

	public void setDebtsShortAmount(BigDecimal debtsShortAmount){
		this.debtsShortAmount=debtsShortAmount;
	}

	public BigDecimal getDebtsLongAmount(){
		return this.debtsLongAmount;
	}

	public void setDebtsLongAmount(BigDecimal debtsLongAmount){
		this.debtsLongAmount=debtsLongAmount;
	}

	public BigDecimal getDebtsInvestAmount(){
		return this.debtsInvestAmount;
	}

	public void setDebtsInvestAmount(BigDecimal debtsInvestAmount){
		this.debtsInvestAmount=debtsInvestAmount;
	}

	public BigDecimal getDebtsSelfUserAmount(){
		return this.debtsSelfUserAmount;
	}

	public void setDebtsSelfUserAmount(BigDecimal debtsSelfUserAmount){
		this.debtsSelfUserAmount=debtsSelfUserAmount;
	}

	public BigDecimal getDebtsConsumeAmount(){
		return this.debtsConsumeAmount;
	}

	public void setDebtsConsumeAmount(BigDecimal debtsConsumeAmount){
		this.debtsConsumeAmount=debtsConsumeAmount;
	}

	public BigDecimal getDebtsOtherAmount(){
		return this.debtsOtherAmount;
	}

	public void setDebtsOtherAmount(BigDecimal debtsOtherAmount){
		this.debtsOtherAmount=debtsOtherAmount;
	}

	public BigDecimal getOffAssetsAmount(){
		return this.offAssetsAmount;
	}

	public void setOffAssetsAmount(BigDecimal offAssetsAmount){
		this.offAssetsAmount=offAssetsAmount;
	}

	public BigDecimal getOffDebtsAmount(){
		return this.offDebtsAmount;
	}

	public void setOffDebtsAmount(BigDecimal offDebtsAmount){
		this.offDebtsAmount=offDebtsAmount;
	}

	public String getOffRemark(){
		return this.offRemark;
	}

	public void setOffRemark(String offRemark){
		this.offRemark=offRemark;
	}

	public Date getSurveyDate(){
		return this.surveyDate;
	}

	public void setSurveyDate(Date surveyDate){
		this.surveyDate=surveyDate;
	}

	public BigDecimal getAssetsAdvancePaymentAmount(){
		return this.assetsAdvancePaymentAmount;
	}

	public void setAssetsAdvancePaymentAmount(BigDecimal assetsAdvancePaymentAmount){
		this.assetsAdvancePaymentAmount=assetsAdvancePaymentAmount;
	}

	public BigDecimal getDebtsBizOtherAmount(){
		return this.debtsBizOtherAmount;
	}

	public void setDebtsBizOtherAmount(BigDecimal debtsBizOtherAmount){
		this.debtsBizOtherAmount=debtsBizOtherAmount;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

}
