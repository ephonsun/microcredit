package banger.domain.loan;

import java.math.BigDecimal;

public class LoanStatQuery extends LoanApplyInfo {

		String userName;//经理名
		String groupName;//团队名
		String time;//时间段
		Double loanTotalMoney;//贷款总金额
	    Double loanTotalMoneyW;
		Double loanAvg;//贷款平均金额
	    Double loanAvgW;
		Integer loanTotalNum;//贷款总数
	    Integer loanSuccessNum;//贷款成功数
		String loanPercent;//贷款通过率
		Integer loanHasClearance;//贷款已结清数
	    Integer approvalRefuse;//审批拒绝
	    Integer otherRefuse;//其他拒绝
		Integer allRefuse;
		String year;
		String month;
		BigDecimal loanAvgRatio;//累计平均利率
		BigDecimal loanAvgMoney;//放款平均金额
		Integer loanWeekNum;//周放款笔数
		BigDecimal loanWeekSum;//周放款总额

	public Integer getLoanWeekNum() {
		return loanWeekNum;
	}

	public void setLoanWeekNum(Integer loanWeekNum) {
		this.loanWeekNum = loanWeekNum;
	}

	public BigDecimal getLoanWeekSum() {
		return loanWeekSum;
	}

	public void setLoanWeekSum(BigDecimal loanWeekSum) {
		this.loanWeekSum = loanWeekSum==null?new BigDecimal(0):loanWeekSum;
	}

	public BigDecimal getLoanAvgMoney() {
		return loanAvgMoney;
	}

	public void setLoanAvgMoney(BigDecimal loanAvgMoney) {
		this.loanAvgMoney = loanAvgMoney==null?new BigDecimal(0):loanAvgMoney;
	}

	public BigDecimal getLoanAvgRatio() {
		return loanAvgRatio;
	}

	public void setLoanAvgRatio(BigDecimal loanAvgRatio) {
		this.loanAvgRatio = loanAvgRatio==null?new BigDecimal(0):loanAvgRatio;
	}

	public Integer getAllRefuse() {
		return allRefuse;
	}

	public void setAllRefuse(Integer allRefuse) {
		this.allRefuse = allRefuse;
	}

	public Double getLoanTotalMoneyW() {
		return loanTotalMoneyW;
	}

	public void setLoanTotalMoneyW(Double loanTotalMoneyW) {
		this.loanTotalMoneyW = loanTotalMoneyW;
	}

	public Double getLoanAvgW() {
		return loanAvgW;
	}

	public void setLoanAvgW(Double loanAvgW) {
		this.loanAvgW = loanAvgW;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getLoanTotalMoney() {
		return loanTotalMoney;
	}

	public void setLoanTotalMoney(Double loanTotalMoney) {
		this.loanTotalMoney = loanTotalMoney;
	}

	public Double getLoanAvg() {
		return loanAvg;
	}

	public void setLoanAvg(Double loanAvg) {
		this.loanAvg = loanAvg;
	}

	public Integer getLoanTotalNum() {
		return loanTotalNum;
	}

	public void setLoanTotalNum(Integer loanTotalNum) {
		this.loanTotalNum = loanTotalNum;
	}

	public Integer getLoanSuccessNum() {
		return loanSuccessNum;
	}

	public void setLoanSuccessNum(Integer loanSuccessNum) {
		this.loanSuccessNum = loanSuccessNum;
	}

	public String getLoanPercent() {
		return loanPercent;
	}

	public void setLoanPercent(String loanPercent) {
		this.loanPercent = loanPercent;
	}

	public Integer getLoanHasClearance() {
		return loanHasClearance;
	}

	public void setLoanHasClearance(Integer loanHasClearance) {
		this.loanHasClearance = loanHasClearance;
	}

	public Integer getApprovalRefuse() {
		return approvalRefuse;
	}

	public void setApprovalRefuse(Integer approvalRefuse) {
		this.approvalRefuse = approvalRefuse;
	}

	public Integer getOtherRefuse() {
		return otherRefuse;
	}

	public void setOtherRefuse(Integer otherRefuse) {
		this.otherRefuse = otherRefuse;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
