package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 调查流程步骤内容表
 */
public class LoanFlowStepContent implements Serializable {
	private static final long serialVersionUID = 5036726304029582727L;
	private Integer id;					//内容ID
	private Integer flowId;					//步骤ID
	private String substance;					//调查内容
	private Integer sortNo;					//内容序号
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

	public String getSubstance(){
		return this.substance;
	}

	public void setSubstance(String substance){
		this.substance=substance;
	}

	public Integer getSortNo(){
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo){
		this.sortNo=sortNo;
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
