package banger.domain.loan;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 贷款流程类型
 */
public class LoanProcessType implements Serializable {
	private static final long serialVersionUID = 2376616253598611530L;
	private Integer id;					//主健ID
	private String precType;					//贷款流程类型
	private String precTypeName;					//贷款流程类型名称
	private Integer orderNo;					//排序字段
	private Integer isActived;					//

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getPrecType(){
		return this.precType;
	}

	public void setPrecType(String precType){
		this.precType=precType;
	}

	public String getPrecTypeName(){
		return this.precTypeName;
	}

	public void setPrecTypeName(String precTypeName){
		this.precTypeName=precTypeName;
	}

	public Integer getOrderNo(){
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo){
		this.orderNo=orderNo;
	}

	public Integer getIsActived(){
		return this.isActived;
	}

	public void setIsActived(Integer isActived){
		this.isActived=isActived;
	}

}
