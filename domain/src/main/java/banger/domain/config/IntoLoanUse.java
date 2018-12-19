package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 进件贷款用途
 */
public class IntoLoanUse implements Serializable {
	private static final long serialVersionUID = 6282697085897997929L;
	private Integer useId;					//主键
	private String useSelect;					//选项
	private Integer loanTypeId;					//贷款类型
	private String ruleId;					//初审规则
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户

	public Integer getUseId(){
		return this.useId;
	}

	public void setUseId(Integer useId){
		this.useId=useId;
	}

	public String getUseSelect(){
		return this.useSelect;
	}

	public void setUseSelect(String useSelect){
		this.useSelect=useSelect;
	}

	public Integer getLoanTypeId(){
		return this.loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId){
		this.loanTypeId=loanTypeId;
	}

	public String getRuleId(){
		return this.ruleId;
	}

	public void setRuleId(String ruleId){
		this.ruleId=ruleId;
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
