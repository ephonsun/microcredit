package banger.domain.loan.contract;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class LoanContractTemplateType implements Serializable {
	private static final long serialVersionUID = 6708114594787616645L;
	private Integer id;					//
	private String typeName;					//合同模版分类名称
	private Integer typeLevel;					//合同模版分类级别
	private Integer sortNo;					//排序
	private String remark;					//备注
	private Integer isDel;					//
	private Integer createUser;					//
	private Date createDate;					//
	private Integer updateUser;					//
	private Date updateDate;					//

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setTypeName(String typeName){
		this.typeName=typeName;
	}

	public Integer getTypeLevel(){
		return this.typeLevel;
	}

	public void setTypeLevel(Integer typeLevel){
		this.typeLevel=typeLevel;
	}

	public Integer getSortNo(){
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo){
		this.sortNo=sortNo;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
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

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

}
