package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 资产负债项明细
 */
public class LoanAssetsDebtsItem implements Serializable {
	private static final long serialVersionUID = 6595298778938055489L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private Integer loanClassId;					//贷款分类ID1 经营类2 消费类
	private String columnName;					//合计项列名
	private String tableName;					//合计项表名
	private String debtsSource;					//来源
	private BigDecimal bebtsAmount;					//金额
	private Integer termLimit;					//期限
	private String debtsUsed;					//用途
	private Date issueDate;					//发放日期
	private BigDecimal balanceAmount;					//余额
	private String ensureMode;					//保证方式
	private String remark;					//备注
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

	public String getDebtsSource(){
		return this.debtsSource;
	}

	public void setDebtsSource(String debtsSource){
		this.debtsSource=debtsSource;
	}

	public BigDecimal getBebtsAmount(){
		return this.bebtsAmount;
	}

	public void setBebtsAmount(BigDecimal bebtsAmount){
		this.bebtsAmount=bebtsAmount;
	}

	public Integer getTermLimit(){
		return this.termLimit;
	}

	public void setTermLimit(Integer termLimit){
		this.termLimit=termLimit;
	}

	public String getDebtsUsed(){
		return this.debtsUsed;
	}

	public void setDebtsUsed(String debtsUsed){
		this.debtsUsed=debtsUsed;
	}

	public Date getIssueDate(){
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate){
		this.issueDate=issueDate;
	}

	public BigDecimal getBalanceAmount(){
		return this.balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount){
		this.balanceAmount=balanceAmount;
	}

	public String getEnsureMode(){
		return this.ensureMode;
	}

	public void setEnsureMode(String ensureMode){
		this.ensureMode=ensureMode;
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
