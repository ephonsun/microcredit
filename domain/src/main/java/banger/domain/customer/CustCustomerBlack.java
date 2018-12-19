package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 客户黑名单表
 */
public class CustCustomerBlack implements Serializable {
	private static final long serialVersionUID = 3875627882435949646L;
	private Integer customerBlackId;					//客户黑名单表id
	private Integer customerId;					//客户id
	private Integer approveUserId;					//审核人
	private Integer isDel;					//删除状态 0：正常    1：已删除
	private Integer approveStatus;					//审核状态  1：未审核  2：已审核  3：已拒绝
	private String cardType;					//证件类型
	private Date approveDate;					//审核时间
	private Integer createUser;					//创建人
	private String cardNo;					//证件号码
	private String customerName;					//客户姓名
	private Date createDate;					//创建时间

	public Integer getCustomerBlackId(){
		return this.customerBlackId;
	}

	public void setCustomerBlackId(Integer customerBlackId){
		this.customerBlackId=customerBlackId;
	}

	public Integer getCustomerId(){
		return this.customerId;
	}

	public void setCustomerId(Integer customerId){
		this.customerId=customerId;
	}

	public Integer getApproveUserId(){
		return this.approveUserId;
	}

	public void setApproveUserId(Integer approveUserId){
		this.approveUserId=approveUserId;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getApproveStatus(){
		return this.approveStatus;
	}

	public void setApproveStatus(Integer approveStatus){
		this.approveStatus=approveStatus;
	}

	public String getCardType(){
		return this.cardType;
	}

	public void setCardType(String cardType){
		this.cardType=cardType;
	}

	public Date getApproveDate(){
		return this.approveDate;
	}

	public void setApproveDate(Date approveDate){
		this.approveDate=approveDate;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public String getCardNo(){
		return this.cardNo;
	}

	public void setCardNo(String cardNo){
		this.cardNo=cardNo;
	}

	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName=customerName;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

}
