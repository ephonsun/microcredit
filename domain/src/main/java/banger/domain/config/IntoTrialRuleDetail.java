package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 初审规则明细表
 */
public class IntoTrialRuleDetail implements Serializable {
	private static final long serialVersionUID = 4592175797004258639L;
	private Integer optionId;					//主键
	private Integer ruleId;					//模型ID
	private Integer templateId;					//模板ID
	private Integer fieldId;					//模板字段ID
	private String passOption;					//评分项ID
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户

	public Integer getOptionId(){
		return this.optionId;
	}

	public void setOptionId(Integer optionId){
		this.optionId=optionId;
	}

	public Integer getRuleId(){
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId){
		this.ruleId=ruleId;
	}

	public Integer getTemplateId(){
		return this.templateId;
	}

	public void setTemplateId(Integer templateId){
		this.templateId=templateId;
	}

	public Integer getFieldId(){
		return this.fieldId;
	}

	public void setFieldId(Integer fieldId){
		this.fieldId=fieldId;
	}

	public String getPassOption(){
		return this.passOption;
	}

	public void setPassOption(String passOption){
		this.passOption=passOption;
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
