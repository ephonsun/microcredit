package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 进件自定义字段表
 */
public class IntoAutoTableColumn implements Serializable {
	private static final long serialVersionUID = 2256281279749578738L;
	private Integer fieldId;					//主键ID
	private String tableName;					//表名
	private String fieldColumn;					//字段列名
	private Integer fieldIsRequired;					//是否必填
	private Integer isActived;					//是否启用
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private Integer fieldSortno;					//字段排序

	public Integer getFieldId(){
		return this.fieldId;
	}

	public void setFieldId(Integer fieldId){
		this.fieldId=fieldId;
	}

	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName=tableName;
	}

	public String getFieldColumn(){
		return this.fieldColumn;
	}

	public void setFieldColumn(String fieldColumn){
		this.fieldColumn=fieldColumn;
	}

	public Integer getFieldIsRequired(){
		return this.fieldIsRequired;
	}

	public void setFieldIsRequired(Integer fieldIsRequired){
		this.fieldIsRequired=fieldIsRequired;
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

	public Integer getFieldSortno(){
		return this.fieldSortno;
	}

	public void setFieldSortno(Integer fieldSortno){
		this.fieldSortno=fieldSortno;
	}

}
