package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户征信调查表
 */
public class CustCustomerCreditCheck implements Serializable {
	private static final long serialVersionUID = 5656779589014722642L;
	private Integer customerCreditCheckId;					//
	private Date updateDate;					//更新时间
	private Integer checkUserId;					//调查提交人
	private String customerName;					//客户姓名
	private Integer isDel;					//删除状态
	private String cardNo;					//证件号码
	private Integer updateUser;					//更新人
	private String checkRemark;					//调查备注
	private Integer applyUserId;					//申请人id
	private Date applyTime;					//申请提交时间
	private Date checkDate;					//调查提交时间
	private Integer checkStatus;					//调查状态
	private Integer customerId;					//客户id
	private String customerType;                //客户类型  借款人  共同借款人 担保人
	private Integer loanId;                 //贷款id

	public Integer getCustomerCreditCheckId(){
		return this.customerCreditCheckId;
	}

	public void setCustomerCreditCheckId(Integer customerCreditCheckId){
		this.customerCreditCheckId=customerCreditCheckId;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getCheckUserId(){
		return this.checkUserId;
	}

	public void setCheckUserId(Integer checkUserId){
		this.checkUserId=checkUserId;
	}

	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName=customerName;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public String getCardNo(){
		return this.cardNo;
	}

	public void setCardNo(String cardNo){
		this.cardNo=cardNo;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public String getCheckRemark(){
		return this.checkRemark;
	}

	public void setCheckRemark(String checkRemark){
		this.checkRemark=checkRemark;
	}

	public Integer getApplyUserId(){
		return this.applyUserId;
	}

	public void setApplyUserId(Integer applyUserId){
		this.applyUserId=applyUserId;
	}

	public Date getApplyTime(){
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime){
		this.applyTime=applyTime;
	}

	public Date getCheckDate(){
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate){
		this.checkDate=checkDate;
	}

	public Integer getCheckStatus(){
		return this.checkStatus;
	}

	public void setCheckStatus(Integer checkStatus){
		this.checkStatus=checkStatus;
	}

	public Integer getCustomerId(){
		return this.customerId;
	}

	public void setCustomerId(Integer customerId){
		this.customerId=customerId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	
}