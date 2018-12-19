package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 贷款行业指引等级说明表
 */
public class LoanIndustryGradeexp implements Serializable {
	private static final long serialVersionUID = 7703010943130654299L;
	private Integer id;					//主键
	private Integer loanId;					//贷款id
	private String itemName;					//项目名称
	private String itemColumn;					//项目字段
	private String itemGradecn;					//等级英文名称
	private String itemGradeen;					//等级中文名称
	private String itemGradeexp;					//等级与平均值比较说明
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间

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

	public String getItemName(){
		return this.itemName;
	}

	public void setItemName(String itemName){
		this.itemName=itemName;
	}

	public String getItemColumn(){
		return this.itemColumn;
	}

	public void setItemColumn(String itemColumn){
		this.itemColumn=itemColumn;
	}

	public String getItemGradecn(){
		return this.itemGradecn;
	}

	public void setItemGradecn(String itemGradecn){
		this.itemGradecn=itemGradecn;
	}

	public String getItemGradeen(){
		return this.itemGradeen;
	}

	public void setItemGradeen(String itemGradeen){
		this.itemGradeen=itemGradeen;
	}

	public String getItemGradeexp(){
		return this.itemGradeexp;
	}

	public void setItemGradeexp(String itemGradeexp){
		this.itemGradeexp=itemGradeexp;
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
