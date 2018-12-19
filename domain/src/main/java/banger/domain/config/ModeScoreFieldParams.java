package banger.domain.config;

import java.io.Serializable;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 评分模型评分项参数表
 */
public class ModeScoreFieldParams implements Serializable {
	private static final long serialVersionUID = 5735686372500992051L;
	private Integer paramId;					//主键
	private Integer modeId;					//模型ID
	private Integer templateId;					//模板ID
	private Integer fieldId;					//模板字段ID
	private Integer optionId;					//评分项ID
	private String optionParam1;					//参数1
	private String optionParam2;					//参数2
	private String optionParam3;					//参数3
	private String optionParam4;					//参数4
	private BigDecimal paramScore;					//分数

	public Integer getParamId(){
		return this.paramId;
	}

	public void setParamId(Integer paramId){
		this.paramId=paramId;
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

	public Integer getOptionId(){
		return this.optionId;
	}

	public void setOptionId(Integer optionId){
		this.optionId=optionId;
	}

	public String getOptionParam1(){
		return this.optionParam1;
	}

	public void setOptionParam1(String optionParam1){
		this.optionParam1=optionParam1;
	}

	public String getOptionParam2(){
		return this.optionParam2;
	}

	public void setOptionParam2(String optionParam2){
		this.optionParam2=optionParam2;
	}

	public String getOptionParam3(){
		return this.optionParam3;
	}

	public void setOptionParam3(String optionParam3){
		this.optionParam3=optionParam3;
	}

	public String getOptionParam4(){
		return this.optionParam4;
	}

	public void setOptionParam4(String optionParam4){
		this.optionParam4=optionParam4;
	}

	public BigDecimal getParamScore(){
		return this.paramScore;
	}

	public void setParamScore(BigDecimal paramScore){
		this.paramScore=paramScore;
	}

}
