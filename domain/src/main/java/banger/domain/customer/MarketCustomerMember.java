package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 营销客户分配表
 */
public class MarketCustomerMember implements Serializable {
	private static final long serialVersionUID = 7382033651275093518L;
	private Integer signUserId;					//分配人
	private Integer isDel;					//删除状态/作废状态
	private Integer customerId;					//客户id
	private Integer teamMemberId;					//团队id
	private Integer teamGroupId;					//
	private Integer applySate;					//转申请状态  1：未申请  2：已申请
	private Date signDate;					//分配时间

	public Integer getSignUserId(){
		return this.signUserId;
	}

	public void setSignUserId(Integer signUserId){
		this.signUserId=signUserId;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getCustomerId(){
		return this.customerId;
	}

	public void setCustomerId(Integer customerId){
		this.customerId=customerId;
	}

	public Integer getTeamMemberId(){
		return this.teamMemberId;
	}

	public void setTeamMemberId(Integer teamMemberId){
		this.teamMemberId=teamMemberId;
	}

	public Integer getTeamGroupId(){
		return this.teamGroupId;
	}

	public void setTeamGroupId(Integer teamGroupId){
		this.teamGroupId=teamGroupId;
	}

	public Integer getApplySate(){
		return this.applySate;
	}

	public void setApplySate(Integer applySate){
		this.applySate=applySate;
	}

	public Date getSignDate(){
		return this.signDate;
	}

	public void setSignDate(Date signDate){
		this.signDate=signDate;
	}

}
