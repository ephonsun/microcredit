package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class CustSchedule implements Serializable {
	private static final long serialVersionUID = 7012989065323697121L;
	private Integer id;					//
	private Integer customerId;					//
	private String planType;					//联系方式
	private Date planTime;					//联系时间
	private String planRemark;					//日程备注
	private String planRate;					//联系进度
	private Integer scheduleType;			//日程类型
	private Integer state;					//完成情况
	private Integer createUser;					//
	private Date createDate;					//
	private Integer updateUser;					//
	private Date updateDate;					//
	private Integer delFlag;					//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId(){
		return this.customerId;
	}

	public void setCustomerId(Integer customerId){
		this.customerId=customerId;
	}


	public Date getPlanTime(){
		return this.planTime;
	}

	public void setPlanTime(Date planTime){
		this.planTime=planTime;
	}

	public String getPlanRemark(){
		return this.planRemark;
	}

	public void setPlanRemark(String planRemark){
		this.planRemark=planRemark;
	}

	public Integer getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(Integer scheduleType) {
		this.scheduleType = scheduleType;
	}

	public Integer getState(){
		return this.state;
	}

	public void setState(Integer state){
		this.state=state;
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

	public Integer getDelFlag(){
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag){
		this.delFlag=delFlag;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getPlanRate() {
		return planRate;
	}

	public void setPlanRate(String planRate) {
		this.planRate = planRate;
	}

}