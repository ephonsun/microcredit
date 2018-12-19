package banger.domain.loan.contract;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class LoanContractRelateItem implements Serializable {
	private static final long serialVersionUID = 1130604204791055165L;
	private Integer id;					//
	private Integer loanTypeId;					//贷款类型id
	private Integer templateFileId;					//合同模版文件id
	private Integer createUser;					//
	private Date createDate;					//

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getLoanTypeId(){
		return this.loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId){
		this.loanTypeId=loanTypeId;
	}

	public Integer getTemplateFileId(){
		return this.templateFileId;
	}

	public void setTemplateFileId(Integer templateFileId){
		this.templateFileId=templateFileId;
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

}
