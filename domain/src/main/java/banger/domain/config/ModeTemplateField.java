package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 评分模型积分项模板字段表
 */
public class ModeTemplateField implements Serializable {
	private static final long serialVersionUID = 8689499367474534160L;
	private Integer fieldId;					//主键
	private Integer templateId;					//模板ID
	private String tableName;					//表名
	private String talbeColumn;					//列名
	private String fieldName;					//字段名称
	private String fieldDisplay;					//字段别名
	private String templateModule;					//所属模块
	private String templateComment;					//模板备注
	private Integer isActived;					//是否启用1 启用0 禁用
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户

	public Integer getFieldId(){
		return this.fieldId;
	}

	public void setFieldId(Integer fieldId){
		this.fieldId=fieldId;
	}

	public Integer getTemplateId(){
		return this.templateId;
	}

	public void setTemplateId(Integer templateId){
		this.templateId=templateId;
	}

	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName=tableName;
	}

	public String getTalbeColumn(){
		return this.talbeColumn;
	}

	public void setTalbeColumn(String talbeColumn){
		this.talbeColumn=talbeColumn;
	}

	public String getFieldName(){
		return this.fieldName;
	}

	public void setFieldName(String fieldName){
		this.fieldName=fieldName;
	}

	public String getFieldDisplay(){
		return this.fieldDisplay;
	}

	public void setFieldDisplay(String fieldDisplay){
		this.fieldDisplay=fieldDisplay;
	}

	public String getTemplateModule(){
		return this.templateModule;
	}

	public void setTemplateModule(String templateModule){
		this.templateModule=templateModule;
	}

	public String getTemplateComment(){
		return this.templateComment;
	}

	public void setTemplateComment(String templateComment){
		this.templateComment=templateComment;
	}

	public Integer getIsActived(){
		return this.isActived;
	}

	public void setIsActived(Integer isActived){
		this.isActived=isActived;
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
