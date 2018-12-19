package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 行业指引表
 */
public class LoanIndustryGuidelines implements Serializable {
	private static final long serialVersionUID = 8670808816203342456L;
	private Integer id;					//主键
	private String gradeFirst;					//行业一级
	private String gradeSecond;					//行业二级
	private String gradeThird;					//行业三级
	private String gradeTerms;					//行业总称
	private String itemName;					//项目名称
	private BigDecimal value1;					//优秀值
	private BigDecimal value2;					//良好值
	private BigDecimal value3;					//平均值
	private BigDecimal value4;					//较低值
	private BigDecimal value5;					//较差值
	private Integer valueType;					//value类型,0表示优秀值到较差值降序,1表示优秀值到较差值升序
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getGradeFirst(){
		return this.gradeFirst;
	}

	public void setGradeFirst(String gradeFirst){
		this.gradeFirst=gradeFirst;
	}

	public String getGradeSecond(){
		return this.gradeSecond;
	}

	public void setGradeSecond(String gradeSecond){
		this.gradeSecond=gradeSecond;
	}

	public String getGradeThird(){
		return this.gradeThird;
	}

	public void setGradeThird(String gradeThird){
		this.gradeThird=gradeThird;
	}

	public String getGradeTerms() {
		return gradeTerms;
	}

	public void setGradeTerms(String gradeTerms) {
		this.gradeTerms = gradeTerms;
	}

	public String getItemName(){
		return this.itemName;
	}

	public void setItemName(String itemName){
		this.itemName=itemName;
	}

	public BigDecimal getValue1(){
		return this.value1;
	}

	public void setValue1(BigDecimal value1){
		this.value1=value1;
	}

	public BigDecimal getValue2(){
		return this.value2;
	}

	public void setValue2(BigDecimal value2){
		this.value2=value2;
	}

	public BigDecimal getValue3(){
		return this.value3;
	}

	public void setValue3(BigDecimal value3){
		this.value3=value3;
	}

	public BigDecimal getValue4(){
		return this.value4;
	}

	public void setValue4(BigDecimal value4){
		this.value4=value4;
	}

	public BigDecimal getValue5(){
		return this.value5;
	}

	public void setValue5(BigDecimal value5){
		this.value5=value5;
	}

	public Integer getValueType(){
		return this.valueType;
	}

	public void setValueType(Integer valueType){
		this.valueType=valueType;
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
