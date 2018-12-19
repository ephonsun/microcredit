package banger.domain.task;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 任务表
 */
public class TskTaskInfo implements Serializable {
	private static final long serialVersionUID = 6748576448220908447L;
	private Integer taskId;					//
	private String taskTitle;					//任务名称
	private Date startDate;					//开始时间
	private Date endDate;					//结束时间
	private Integer taskMold;					//任务模式,0 贷款任务,1 营销任务
	private Integer taskType;					//任务类型1:贷款金额2:贷款数量
	private Integer taskTarget;					//任务目标
	private String remark;					//任务简介
	private Integer assignUserId;					//分配者
	private Date finishDate;					//任务完成时间
	private Integer taskStatus;					//任务状态1:未完成2:完成3:已中止
	private Integer taskFinish;					//已完成数量
	private Integer taskAssign;					//已分配数量
	private Integer isDel;					//删除状态 0：未删除  1：删除
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private Integer teamGroupId;					//
	private Integer tskLevel;					//任务级别1 夸团队任务 2 团队任务 3个人任务

	public Integer getTaskMold() {
		return taskMold;
	}

	public void setTaskMold(Integer taskMold) {
		this.taskMold = taskMold;
	}

	public Integer getTaskId(){
		return this.taskId;
	}

	public void setTaskId(Integer taskId){
		this.taskId=taskId;
	}

	public String getTaskTitle(){
		return this.taskTitle;
	}

	public void setTaskTitle(String taskTitle){
		this.taskTitle=taskTitle;
	}

	public Date getStartDate(){
		return this.startDate;
	}

	public void setStartDate(Date startDate){
		this.startDate=startDate;
	}

	public Date getEndDate(){
		return this.endDate;
	}

	public void setEndDate(Date endDate){
		this.endDate=endDate;
	}

	public Integer getTaskType(){
		return this.taskType;
	}

	public void setTaskType(Integer taskType){
		this.taskType=taskType;
	}

	public Integer getTaskTarget(){
		return this.taskTarget;
	}

	public void setTaskTarget(Integer taskTarget){
		this.taskTarget=taskTarget;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public Integer getAssignUserId(){
		return this.assignUserId;
	}

	public void setAssignUserId(Integer assignUserId){
		this.assignUserId=assignUserId;
	}

	public Date getFinishDate(){
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate){
		this.finishDate=finishDate;
	}

	public Integer getTaskStatus(){
		return this.taskStatus;
	}

	public void setTaskStatus(Integer taskStatus){
		this.taskStatus=taskStatus;
	}

	public Integer getTaskFinish(){
		return this.taskFinish;
	}

	public void setTaskFinish(Integer taskFinish){
		this.taskFinish=taskFinish;
	}

	public Integer getTaskAssign(){
		return this.taskAssign;
	}

	public void setTaskAssign(Integer taskAssign){
		this.taskAssign=taskAssign;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
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

	public Integer getTeamGroupId(){
		return this.teamGroupId;
	}

	public void setTeamGroupId(Integer teamGroupId){
		this.teamGroupId=teamGroupId;
	}

	public Integer getTskLevel(){
		return this.tskLevel;
	}

	public void setTskLevel(Integer tskLevel){
		this.tskLevel=tskLevel;
	}


}
