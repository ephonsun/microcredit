package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 贷款类型表单字段配置
 */
public class LoanTypeTableField implements Serializable {
	private static final long serialVersionUID = 8943480252591006991L;
	private Integer id;					//主键
	private Integer loanTypeId;					//贷款类型主键
	private String loanPrecType;					//贷款过程类型
	private Integer tableId;					//自定义表单主键
	private Integer fieldId;					//自定义字段主键
	private Integer fieldAppIsShow;					//APP端是否可显示默是显示的
	private Integer fieldWebIsShow;					//web端是否可显示
	private Integer fieldIsRequired;					//是否必填
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private Integer fieldIsCondition;					//是否为审批条件

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getLoanTypeId(){
		return this.loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId){
		this.loanTypeId=loanTypeId;
	}

	public String getLoanPrecType(){
		return this.loanPrecType;
	}

	public void setLoanPrecType(String loanPrecType){
		this.loanPrecType=loanPrecType;
	}

	public Integer getTableId(){
		return this.tableId;
	}

	public void setTableId(Integer tableId){
		this.tableId=tableId;
	}

	public Integer getFieldId(){
		return this.fieldId;
	}

	public void setFieldId(Integer fieldId){
		this.fieldId=fieldId;
	}

	public Integer getFieldAppIsShow(){
		return this.fieldAppIsShow;
	}

	public void setFieldAppIsShow(Integer fieldAppIsShow){
		this.fieldAppIsShow=fieldAppIsShow;
	}

	public Integer getFieldWebIsShow(){
		return this.fieldWebIsShow;
	}

	public void setFieldWebIsShow(Integer fieldWebIsShow){
		this.fieldWebIsShow=fieldWebIsShow;
	}

	public Integer getFieldIsRequired(){
		return this.fieldIsRequired;
	}

	public void setFieldIsRequired(Integer fieldIsRequired){
		this.fieldIsRequired=fieldIsRequired;
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

	public Integer getFieldIsCondition(){
		return this.fieldIsCondition;
	}

	public void setFieldIsCondition(Integer fieldIsCondition){
		this.fieldIsCondition=fieldIsCondition;
	}

}
