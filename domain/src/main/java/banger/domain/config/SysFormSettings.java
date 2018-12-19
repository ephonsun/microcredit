package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 表单关联设置表
 */
public class SysFormSettings implements Serializable {
	private static final long serialVersionUID = 1016276067948085468L;
	private Integer id;					//表单设置ID
	private String controlForm;					//控制表单
	private String controlFormName;					//控制表单名称
	private String controlItem;					//控制项
	private String controlItemName;					//控制项名称
	private String controlValue;					//控制值
	private String controlValueName;					//控制值名称
	private String hiddenForm;					//隐藏表单
	private String hiddenFormName;					//隐藏表单名称
	private String controlItemColumn;					//控制项列
	private Integer createUser;					//
	private Date createDate;					//
	private Integer updateUser;					//
	private Date updateDate;					//

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getControlForm(){
		return this.controlForm;
	}

	public void setControlForm(String controlForm){
		this.controlForm=controlForm;
	}

	public String getControlFormName(){
		return this.controlFormName;
	}

	public void setControlFormName(String controlFormName){
		this.controlFormName=controlFormName;
	}

	public String getControlItem(){
		return this.controlItem;
	}

	public void setControlItem(String controlItem){
		this.controlItem=controlItem;
	}

	public String getControlItemName(){
		return this.controlItemName;
	}

	public void setControlItemName(String controlItemName){
		this.controlItemName=controlItemName;
	}

	public String getControlValue(){
		return this.controlValue;
	}

	public void setControlValue(String controlValue){
		this.controlValue=controlValue;
	}

	public String getControlValueName(){
		return this.controlValueName;
	}

	public void setControlValueName(String controlValueName){
		this.controlValueName=controlValueName;
	}

	public String getHiddenForm(){
		return this.hiddenForm;
	}

	public void setHiddenForm(String hiddenForm){
		this.hiddenForm=hiddenForm;
	}

	public String getHiddenFormName(){
		return this.hiddenFormName;
	}

	public void setHiddenFormName(String hiddenFormName){
		this.hiddenFormName=hiddenFormName;
	}

	public String getControlItemColumn(){
		return this.controlItemColumn;
	}

	public void setControlItemColumn(String controlItemColumn){
		this.controlItemColumn=controlItemColumn;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

}
