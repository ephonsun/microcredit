package banger.domain.config;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 评分模型评分项明细表
 */
public class ModeScoreField implements Serializable {
	private static final long serialVersionUID = 6966254582657326968L;
	private Integer optionId;					//主键
	private Integer modeId;					//模型ID
	private Integer templateId;					//模板ID
	private Integer fieldId;					//模板字段ID
	private String fieldName;					//模板字段名称
	private String params;					//参数列表
	private String fieldNumberUnit;					//参数单位

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getOptionId(){
		return this.optionId;
	}

	public void setOptionId(Integer optionId){
		this.optionId=optionId;
	}

	public Integer getModeId(){
		return this.modeId;
	}

	public void setModeId(Integer modeId){
		this.modeId=modeId;
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

	public String getFieldNumberUnit() {
		return fieldNumberUnit;
	}

	public void setFieldNumberUnit(String fieldNumberUnit) {
		this.fieldNumberUnit = fieldNumberUnit;
	}
}
