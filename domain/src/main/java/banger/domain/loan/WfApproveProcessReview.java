package banger.domain.loan;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 审批流环节审核人员表
 */
public class WfApproveProcessReview implements Serializable {
	private static final long serialVersionUID = 6330421753798672421L;
	private Integer id;					//主键ID
	private Integer processId;					//流程环节ID
	private String reviewMode;					//审核分配方式ALL_USER 发给所有用户SELECT_USER 发给指定用户RANDOM_USER 发给随机用户
	private String reviewDataPower;					//审核数据权限TEAM 团队ALL    全部
	private Integer revievRoleId;					//审核角色
	private Integer reviewCount;					//审核人数
	private Integer isLimitControl;					//是否限额
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

	public Integer getProcessId(){
		return this.processId;
	}

	public void setProcessId(Integer processId){
		this.processId=processId;
	}

	public String getReviewMode(){
		return this.reviewMode;
	}

	public void setReviewMode(String reviewMode){
		this.reviewMode=reviewMode;
	}

	public String getReviewDataPower(){
		return this.reviewDataPower;
	}

	public void setReviewDataPower(String reviewDataPower){
		this.reviewDataPower=reviewDataPower;
	}

	public Integer getRevievRoleId(){
		return this.revievRoleId;
	}

	public void setRevievRoleId(Integer revievRoleId){
		this.revievRoleId=revievRoleId;
	}

	public Integer getReviewCount(){
		return this.reviewCount;
	}

	public void setReviewCount(Integer reviewCount){
		this.reviewCount=reviewCount;
	}

	public Integer getIsLimitControl(){
		return this.isLimitControl;
	}

	public void setIsLimitControl(Integer isLimitControl){
		this.isLimitControl=isLimitControl;
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
