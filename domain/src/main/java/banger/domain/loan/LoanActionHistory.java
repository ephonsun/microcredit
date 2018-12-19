package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 贷款操作历史表
 */
public class LoanActionHistory implements Serializable {
	private static final long serialVersionUID = 1043120635861613125L;
	private Integer id;					//主键ID
	private Integer loanId;					//贷款ID
	private String loanProcessType;					//贷款过程类型
	private String actionName;					//操作内容
	private String actionContent;					//操作内容
	private Date actionDate;					//操作时间
	private String actionUserAccount;					//操作人帐号
	private String actionUserName;					//操作人姓名

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

	public String getLoanProcessType(){
		return this.loanProcessType;
	}

	public void setLoanProcessType(String loanProcessType){
		this.loanProcessType=loanProcessType;
	}

	public String getActionName(){
		return this.actionName;
	}

	public void setActionName(String actionName){
		this.actionName=actionName;
	}

	public String getActionContent(){
		return this.actionContent;
	}

	public void setActionContent(String actionContent){
		this.actionContent=actionContent;
	}

	public Date getActionDate(){
		return this.actionDate;
	}

	public void setActionDate(Date actionDate){
		this.actionDate=actionDate;
	}

	public String getActionUserAccount(){
		return this.actionUserAccount;
	}

	public void setActionUserAccount(String actionUserAccount){
		this.actionUserAccount=actionUserAccount;
	}

	public String getActionUserName(){
		return this.actionUserName;
	}

	public void setActionUserName(String actionUserName){
		this.actionUserName=actionUserName;
	}

}
