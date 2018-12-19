package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 营销客户表
 */
public class CustMarketCustomer implements Serializable {
	private static final long serialVersionUID = 6499817358145576727L;
	private Integer marketCustomerId;					//主键ID
	private Integer loanId;					//贷款ID
	private String customerName;					//客户姓名
	private String customerSex;					//性别
	private Integer age;					//年龄
	private String cardType;					//证件类型
	private String cardNumber;					//证件号码
	private Integer customerType;					//客户类型1：预进件 2：营销客户
	private BigDecimal loanMoney;					//意向金额
	private String loanUse;					//贷款用途
	private String phoneNumber;					//手机号码
	private Integer isDel;					//作废状态0：正常 1：删除
	private Integer signSate;					//分配状态1：初始导入 2：预申请分配 3：预申请
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private Date signDate;					//分配时间

	public Integer getMarketCustomerId(){
		return this.marketCustomerId;
	}

	public void setMarketCustomerId(Integer marketCustomerId){
		this.marketCustomerId=marketCustomerId;
	}

	public Integer getLoanId(){
		return this.loanId;
	}

	public void setLoanId(Integer loanId){
		this.loanId=loanId;
	}

	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName=customerName;
	}

	public String getCustomerSex(){
		return this.customerSex;
	}

	public void setCustomerSex(String customerSex){
		this.customerSex=customerSex;
	}

	public Integer getAge(){
		return this.age;
	}

	public void setAge(Integer age){
		this.age=age;
	}

	public String getCardType(){
		return this.cardType;
	}

	public void setCardType(String cardType){
		this.cardType=cardType;
	}

	public String getCardNumber(){
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber){
		this.cardNumber=cardNumber;
	}

	public Integer getCustomerType(){
		return this.customerType;
	}

	public void setCustomerType(Integer customerType){
		this.customerType=customerType;
	}

	public BigDecimal getLoanMoney(){
		return this.loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney){
		this.loanMoney=loanMoney;
	}

	public String getLoanUse(){
		return this.loanUse;
	}

	public void setLoanUse(String loanUse){
		this.loanUse=loanUse;
	}

	public String getPhoneNumber(){
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber=phoneNumber;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getSignSate(){
		return this.signSate;
	}

	public void setSignSate(Integer signSate){
		this.signSate=signSate;
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

	public Date getSignDate(){
		return this.signDate;
	}

	public void setSignDate(Date signDate){
		this.signDate=signDate;
	}

}
