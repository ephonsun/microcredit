package banger.domain.loan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import banger.framework.entity.Entity;

/**
 * 损益情况经营类收入支出明细表
 */
public class LoanProfitBizIncomeItem implements Serializable {
	private static final long serialVersionUID = 2593780781494419476L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private Integer loanClassId;					//贷款分类ID1 经营类2 消费类
	private String columnName;					//合计项列名
	private String tableName;					//合计项表名
	private String itemName;					//项目
	private BigDecimal averageAmount;					//平均值
	private BigDecimal totalAmount;					//总金额
	private String remark;					//备注
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private List<LoanProfitBizIncomeMonth> months = new ArrayList();					//月份

	public List<LoanProfitBizIncomeMonth> getMonths() {
		return months;
	}

	public void setMonths(List<LoanProfitBizIncomeMonth> months) {
		this.months = months;
	}

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

	public String getColumnName(){
		return this.columnName;
	}

	public void setColumnName(String columnName){
		this.columnName=columnName;
	}

	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName=tableName;
	}

	public String getItemName(){
		return this.itemName;
	}

	public void setItemName(String itemName){
		this.itemName=itemName;
	}

	public BigDecimal getAverageAmount(){
		return this.averageAmount;
	}

	public void setAverageAmount(BigDecimal averageAmount){
		this.averageAmount=averageAmount;
	}

	public BigDecimal getTotalAmount(){
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount){
		this.totalAmount=totalAmount;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
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
