package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import banger.framework.entity.Entity;

/**
 * 资产负债固定资产项明细
 */
public class LoanAssetsFixedItem implements Serializable {
	private static final long serialVersionUID = 4703302535788895488L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private Integer loanClassId;					//贷款分类ID1 经营类2 消费类
	private String columnName;					//合计项列名
	private String tableName;					//合计项表名
	private String itemType;					//类别
	private String itemName;					//名称
	private Integer itemAccount;					//数量
	private BigDecimal oldAmount;					//原值
	private BigDecimal depreciationAmount;					//折旧金额
	private BigDecimal newAmount;					//现值
	private BigDecimal amount;					//金额
	private BigDecimal depreciationRatio;					//折旧率
	private String itemRemark;					//项目备注
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

	public String getItemType(){
		return this.itemType;
	}

	public void setItemType(String itemType){
		this.itemType=itemType;
	}

	public String getItemName(){
		return this.itemName;
	}

	public void setItemName(String itemName){
		this.itemName=itemName;
	}

	public Integer getItemAccount(){
		return this.itemAccount;
	}

	public void setItemAccount(Integer itemAccount){
		this.itemAccount=itemAccount;
	}

	public BigDecimal getOldAmount(){
		return this.oldAmount;
	}

	public void setOldAmount(BigDecimal oldAmount){
		this.oldAmount=oldAmount;
	}

	public BigDecimal getDepreciationAmount(){
		return this.depreciationAmount;
	}

	public void setDepreciationAmount(BigDecimal depreciationAmount){
		this.depreciationAmount=depreciationAmount;
	}

	public BigDecimal getNewAmount(){
		return this.newAmount;
	}

	public void setNewAmount(BigDecimal newAmount){
		this.newAmount=newAmount;
	}

	public BigDecimal getAmount(){
		return this.amount;
	}

	public void setAmount(BigDecimal amount){
		this.amount=amount;
	}

	public BigDecimal getDepreciationRatio(){
		return this.depreciationRatio;
	}

	public void setDepreciationRatio(BigDecimal depreciationRatio){
		this.depreciationRatio=depreciationRatio;
	}

	public String getItemRemark(){
		return this.itemRemark;
	}

	public void setItemRemark(String itemRemark){
		this.itemRemark=itemRemark;
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
