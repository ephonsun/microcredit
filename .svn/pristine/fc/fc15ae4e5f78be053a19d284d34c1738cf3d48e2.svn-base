package banger.domain.customer;

import java.io.Serializable;

import banger.framework.codetable.CodeTable;

/**
 * 客户日程查询
 */
public class CustScheduleQuery extends CustSchedule implements Serializable {
	
	private String customerLevel; //客户类型
	
	@CodeTable(name="cdDictColByName",key="customerLevel",params="CD_CUSTOMER_LEVEL")
	private String customerLevelCN;
	
	private String customerName;  //姓名
	
	private String managerName; //归属人姓名
	
	private String customerSex;//性别
	
	@CodeTable(name="cdDictColByName",key="customerSex",params="CD_SEX")
	private String customerSexCN;//性别
	
	private Integer customerAge;//年龄
	
	private String customerAgeStr;
	
	private String phoneNumber;	//联系电话
	
	private Integer teamGroupId; //团队id
	
	private Integer belongUserId;//客户归属人
	
	private String planTimeStr; 
	
	private String planTimeStr1;
	
	@CodeTable(name="cdDictColByName",key="planType",params="CD_SCHEDULE_PLAN_TYPE")
	private String planTypeCN;
	
	@CodeTable(name="cdDictColByName",key="planRate",params="CD_SCHEDULE_PLAN_RATE")
	private String planRateCN;
	
	private String startDate;//联系时间
	
	private String endDate;
	
	private Integer isOverDate;
	
	private Integer mySchedule;//我的
	
	private String stateCN;

	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	public Integer getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(Integer customerAge) {
		this.customerAge = customerAge;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getTeamGroupId() {
		return teamGroupId;
	}

	public void setTeamGroupId(Integer teamGroupId) {
		this.teamGroupId = teamGroupId;
	}

	public Integer getBelongUserId() {
		return belongUserId;
	}

	public void setBelongUserId(Integer belongUserId) {
		this.belongUserId = belongUserId;
	}

	public String getPlanTimeStr() {
		return planTimeStr;
	}

	public void setPlanTimeStr(String planTimeStr) {
		this.planTimeStr = planTimeStr;
	}

	public String getPlanTypeCN() {
		return planTypeCN;
	}

	public void setPlanTypeCN(String planTypeCN) {
		this.planTypeCN = planTypeCN;
	}

	public String getCustomerAgeStr() {
		return customerAgeStr;
	}

	public void setCustomerAgeStr(String customerAgeStr) {
		this.customerAgeStr = customerAgeStr;
	}

	public Integer getIsOverDate() {
		return isOverDate;
	}

	public void setIsOverDate(Integer isOverDate) {
		this.isOverDate = isOverDate;
	}

	public Integer getMySchedule() {
		return mySchedule;
	}

	public void setMySchedule(Integer mySchedule) {
		this.mySchedule = mySchedule;
	}

	public String getCustomerLevelCN() {
		return customerLevelCN;
	}

	public void setCustomerLevelCN(String customerLevelCN) {
		this.customerLevelCN = customerLevelCN;
	}

	public String getCustomerSexCN() {
		return customerSexCN;
	}

	public void setCustomerSexCN(String customerSexCN) {
		this.customerSexCN = customerSexCN;
	}

	public String getPlanRateCN() {
		return planRateCN;
	}

	public void setPlanRateCN(String planRateCN) {
		this.planRateCN = planRateCN;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPlanTimeStr1() {
		return planTimeStr1;
	}

	public void setPlanTimeStr1(String planTimeStr1) {
		this.planTimeStr1 = planTimeStr1;
	}

	public String getStateCN() {
		return stateCN;
	}

	public void setStateCN(String stateCN) {
		if(super.getState()==1){
			this.stateCN="未完成";
		}else{
			this.stateCN="已完成";
		}
	}
	
}
