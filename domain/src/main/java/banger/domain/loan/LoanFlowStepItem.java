package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 流程步骤明细表
 */
public class LoanFlowStepItem implements Serializable {
	private static final long serialVersionUID = 5332309468546835054L;
	private Integer id;					//步骤ID
	private Integer flowId;					//流程ID
	private Integer sortNo;					//步骤序号
	private String stepName;					//步骤名称
	private String stepContent;					//调查内容
	private Integer isDel;					//是否删除
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getFlowId(){
		return this.flowId;
	}

	public void setFlowId(Integer flowId){
		this.flowId=flowId;
	}

	public Integer getSortNo(){
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo){
		this.sortNo=sortNo;
	}

	public String getStepName(){
		return this.stepName;
	}

	public void setStepName(String stepName){
		this.stepName=stepName;
	}

	public String getStepContent(){
		return this.stepContent;
	}

	public void setStepContent(String stepContent){
		this.stepContent=stepContent;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
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

}
