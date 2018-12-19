package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 潜在客户表
 */
public class CustPotentialCustomers implements Serializable {
	private static final long serialVersionUID = 2711812831311039798L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private String customerName;					//客户姓名
	private String telephoneNumber;					//联系电话
	private String cardType;					//证件类型
	private String cardNumber;					//证件号码
	private String customerSex;					//性别
	private Integer age;					//年龄
	private String address;					//居住地址
	private Integer loanIntention;					//贷款意向 0表示无意向,1表示有意向
	private Date intentionDate;					//意向时间
	private String loanUse;					//贷款用途
	private Integer attributionTeam;					//客户归属团队
	private Integer attributionManager;					//客户归属客户经理
	private Integer marketingSuccess;					//营销成功,0表示否,1表示成功
	private String remark;					//备注
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private Integer recordType;					//记录类型,0 导入记录,1 新建记录
	private Integer createUserTeam;				//创建用户团队
	private Integer productId;				//意向产品的id
	private String productCode;				//意向产品的编码

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getCreateUserTeam() {
		return createUserTeam;
	}

	public void setCreateUserTeam(Integer createUserTeam) {
		this.createUserTeam = createUserTeam;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

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

	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName=customerName;
	}

	public String getTelephoneNumber(){
		return this.telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber){
		this.telephoneNumber=telephoneNumber;
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

	public String getAddress(){
		return this.address;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public Integer getLoanIntention(){
		return this.loanIntention;
	}

	public void setLoanIntention(Integer loanIntention){
		this.loanIntention=loanIntention;
	}

	public Date getIntentionDate(){
		return this.intentionDate;
	}

	public void setIntentionDate(Date intentionDate){
		this.intentionDate=intentionDate;
	}

	public String getLoanUse(){
		return this.loanUse;
	}

	public void setLoanUse(String loanUse){
		this.loanUse=loanUse;
	}

	public Integer getAttributionTeam(){
		return this.attributionTeam;
	}

	public void setAttributionTeam(Integer attributionTeam){
		this.attributionTeam=attributionTeam;
	}

	public Integer getAttributionManager(){
		return this.attributionManager;
	}

	public void setAttributionManager(Integer attributionManager){
		this.attributionManager=attributionManager;
	}

	public Integer getMarketingSuccess(){
		return this.marketingSuccess;
	}

	public void setMarketingSuccess(Integer marketingSuccess){
		this.marketingSuccess=marketingSuccess;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
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
