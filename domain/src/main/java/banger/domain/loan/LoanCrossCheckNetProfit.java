package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 交叉检验净利表
 */
public class LoanCrossCheckNetProfit implements Serializable {
	private static final long serialVersionUID = 5660004329283023240L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private Integer loanClassId;					//贷款分类ID1 经营类2 消费类
	private BigDecimal checkNetProfitRatio1;					//检验净利率
	private BigDecimal deviationRatio1;					//偏差率
	private String infomation1;					//说明
	private BigDecimal checkNetProfitRatio2;					//检验净利率
	private BigDecimal deviationRatio2;					//偏差率
	private String infomation2;					//说明
	private BigDecimal checkNetProfitRatio3;					//检验净利率
	private BigDecimal deviationRatio3;					//偏差率
	private String infomation3;					//说明
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户



	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getLoanId(){
		return this.loanId;
	}

	public void setLoanId(Integer loanId){
		this.loanId=loanId;
	}

	public Integer getLoanClassId(){
		return this.loanClassId;
	}

	public void setLoanClassId(Integer loanClassId){
		this.loanClassId=loanClassId;
	}

	public BigDecimal getCheckNetProfitRatio1(){
		return this.checkNetProfitRatio1;
	}

	public void setCheckNetProfitRatio1(BigDecimal checkNetProfitRatio1){
		this.checkNetProfitRatio1=checkNetProfitRatio1;
	}

	public BigDecimal getDeviationRatio1(){
		return this.deviationRatio1;
	}

	public void setDeviationRatio1(BigDecimal deviationRatio1){
		this.deviationRatio1=deviationRatio1;
	}

	public String getInfomation1(){
		return this.infomation1;
	}

	public void setInfomation1(String infomation1){
		this.infomation1=infomation1;
	}

	public BigDecimal getCheckNetProfitRatio2(){
		return this.checkNetProfitRatio2;
	}

	public void setCheckNetProfitRatio2(BigDecimal checkNetProfitRatio2){
		this.checkNetProfitRatio2=checkNetProfitRatio2;
	}

	public BigDecimal getDeviationRatio2(){
		return this.deviationRatio2;
	}

	public void setDeviationRatio2(BigDecimal deviationRatio2){
		this.deviationRatio2=deviationRatio2;
	}

	public String getInfomation2(){
		return this.infomation2;
	}

	public void setInfomation2(String infomation2){
		this.infomation2=infomation2;
	}

	public BigDecimal getCheckNetProfitRatio3(){
		return this.checkNetProfitRatio3;
	}

	public void setCheckNetProfitRatio3(BigDecimal checkNetProfitRatio3){
		this.checkNetProfitRatio3=checkNetProfitRatio3;
	}

	public BigDecimal getDeviationRatio3(){
		return this.deviationRatio3;
	}

	public void setDeviationRatio3(BigDecimal deviationRatio3){
		this.deviationRatio3=deviationRatio3;
	}

	public String getInfomation3(){
		return this.infomation3;
	}

	public void setInfomation3(String infomation3){
		this.infomation3=infomation3;
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
