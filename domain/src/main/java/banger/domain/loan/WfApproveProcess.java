package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 审批流环节定义表
 */
public class WfApproveProcess implements Serializable {
	private static final long serialVersionUID = 4956683115357871679L;
	private Integer processId;					//主键ID
	private Integer flowId;					//流程ID
	private Integer paramId;					//审核条件参数ID
	private String processName;					//进程名如：一审，二审
	private Integer orderNo;					//排序号
	private Integer isActived;					//是否启用0 不启用1 启用 （默认）
	private Integer isDel;					//是否删除0 不删除（默认）1 伪删除
	private Integer isFixed;					//是否内置流程内置流程，不可删除和禁用
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户

	public Integer getProcessId(){
		return this.processId;
	}

	public void setProcessId(Integer processId){
		this.processId=processId;
	}

	public Integer getFlowId(){
		return this.flowId;
	}

	public void setFlowId(Integer flowId){
		this.flowId=flowId;
	}

	public Integer getParamId(){
		return this.paramId;
	}

	public void setParamId(Integer paramId){
		this.paramId=paramId;
	}

	public String getProcessName(){
		return this.processName;
	}

	public void setProcessName(String processName){
		this.processName=processName;
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

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsFixed(){
		return this.isFixed;
	}

	public void setIsFixed(Integer isFixed){
		this.isFixed=isFixed;
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
