package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 贷款还信催款息表
 */
public class LoanRepayPlanInfo implements Serializable {
	private static final long serialVersionUID = 3450379040208210425L;
	private Integer id;					//主键
	private Integer loanId;					//贷款ID
	private Date loanRepayPlanDate;					//计划还款日期
	private BigDecimal loanPrincipalAmount;					//还款本金
	private BigDecimal loanAccrualAmount;					//还款利息
	private BigDecimal loanRepayAmount;					//实际还款金额（本金）
	private BigDecimal loanRepayAccrualAmount;					//实际还款利息
	private Date loanRepayDate;					//实际还款日期
	private Integer loanRepayState;					//还款状态0 未还款1 已还款
	private Integer loanIsOverdue;					//是否逾期
	private Date loanCollectionDate;					//
	private String loanCollectionState;					//
	private Integer loanCollectionUserId;					//
	private Date createDate;					//
	private Date updateDate;					//
	private Integer createUser;					//
	private Integer updateUser;					//
	private BigDecimal loanRepayBalanceAmount;					//贷款上期剩余应还总金额
	private BigDecimal loanAccrualBalanceAmount;					//贷款实际剩余总金额
	private Integer loanAccrualDays;					//本期还款天数
	private String repaymentMode;					//还款方式
	private String loanProcessType;					//贷款流程

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

	public Date getLoanRepayPlanDate(){
		return this.loanRepayPlanDate;
	}

	public void setLoanRepayPlanDate(Date loanRepayPlanDate){
		this.loanRepayPlanDate=loanRepayPlanDate;
	}

	public BigDecimal getLoanPrincipalAmount(){
		return this.loanPrincipalAmount;
	}

	public void setLoanPrincipalAmount(BigDecimal loanPrincipalAmount){
		this.loanPrincipalAmount=loanPrincipalAmount;
	}

	public BigDecimal getLoanAccrualAmount(){
		return this.loanAccrualAmount;
	}

	public void setLoanAccrualAmount(BigDecimal loanAccrualAmount){
		this.loanAccrualAmount=loanAccrualAmount;
	}

	public BigDecimal getLoanRepayAmount(){
		return this.loanRepayAmount;
	}

	public void setLoanRepayAmount(BigDecimal loanRepayAmount){
		this.loanRepayAmount=loanRepayAmount;
	}

	public BigDecimal getLoanRepayAccrualAmount(){
		return this.loanRepayAccrualAmount;
	}

	public void setLoanRepayAccrualAmount(BigDecimal loanRepayAccrualAmount){
		this.loanRepayAccrualAmount=loanRepayAccrualAmount;
	}

	public Date getLoanRepayDate(){
		return this.loanRepayDate;
	}

	public void setLoanRepayDate(Date loanRepayDate){
		this.loanRepayDate=loanRepayDate;
	}

	public Integer getLoanRepayState(){
		return this.loanRepayState;
	}

	public void setLoanRepayState(Integer loanRepayState){
		this.loanRepayState=loanRepayState;
	}

	public Integer getLoanIsOverdue(){
		return this.loanIsOverdue;
	}

	public void setLoanIsOverdue(Integer loanIsOverdue){
		this.loanIsOverdue=loanIsOverdue;
	}

	public Date getLoanCollectionDate(){
		return this.loanCollectionDate;
	}

	public void setLoanCollectionDate(Date loanCollectionDate){
		this.loanCollectionDate=loanCollectionDate;
	}

	public String getLoanCollectionState(){
		return this.loanCollectionState;
	}

	public void setLoanCollectionState(String loanCollectionState){
		this.loanCollectionState=loanCollectionState;
	}

	public Integer getLoanCollectionUserId(){
		return this.loanCollectionUserId;
	}

	public void setLoanCollectionUserId(Integer loanCollectionUserId){
		this.loanCollectionUserId=loanCollectionUserId;
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

	public BigDecimal getLoanRepayBalanceAmount(){
		return this.loanRepayBalanceAmount;
	}

	public void setLoanRepayBalanceAmount(BigDecimal loanRepayBalanceAmount){
		this.loanRepayBalanceAmount=loanRepayBalanceAmount;
	}

	public BigDecimal getLoanAccrualBalanceAmount(){
		return this.loanAccrualBalanceAmount;
	}

	public void setLoanAccrualBalanceAmount(BigDecimal loanAccrualBalanceAmount){
		this.loanAccrualBalanceAmount=loanAccrualBalanceAmount;
	}

	public Integer getLoanAccrualDays(){
		return this.loanAccrualDays;
	}

	public void setLoanAccrualDays(Integer loanAccrualDays){
		this.loanAccrualDays=loanAccrualDays;
	}

	public String getRepaymentMode(){
		return this.repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode){
		this.repaymentMode=repaymentMode;
	}

	public String getLoanProcessType(){
		return this.loanProcessType;
	}

	public void setLoanProcessType(String loanProcessType){
		this.loanProcessType=loanProcessType;
	}

}
