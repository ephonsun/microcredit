package banger.domain.loan;

import banger.domain.enumerate.LoanCollectionState;
import banger.domain.enumerate.LoanMonitorState;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.framework.codetable.CodeTable;
import banger.framework.util.IdCardUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class LoanApplyInfo_Web_Query extends LoanApplyInfo {

	private static final long serialVersionUID = -908303381891263206L;
	private String applyUserName;   //
	private String investigateUserName;   //
	private String belongUserName;
	private String teamName;
	@CodeTable(name = "cdDictColByName", key = "loanIdentifyType", params = "CD_IDENTIFY_TYPE")
	private String loanIdentifyTypeName;
	@CodeTable(name = "cdLoanType", key = "loanTypeId", params = "")
	private String loanTypeName;
	@CodeTable(name="cdDictColByName",key="loanSex",params="CD_SEX")
	private String customerSexCN;//性别

	private String loanMonitorStateName;
	@CodeTable(name = "cdDictColByName", key = "loanAfterState", params = "CD_LOAN_AFTERLOAN_STATUS")
	private String loanAfterStateName;

	private String loanProcessTypeName;					//贷款过程类型
	private String loanIdentifyNumX;
	private String loanTelNumX;
	private String loanApplyAmountFormat;
	private String loanResultAmountFormat;
	private String loanProposeAmountFormat;
	private String loanCreditAmountFormat;

	private String collectionState;


	private String customerInfo;
	private String cardInfo;

	private Integer auditState;
	private  Integer userId;
	private String contractSingUserName;//合同签订人

	public String getContractSingUserName() {
		return contractSingUserName;
	}

	public void setContractSingUserName(String contractSingUserName) {
		this.contractSingUserName = contractSingUserName;
	}

	//首次监控时间
	private Date firstMonitorDate;
	//首次监控状态 完成：MonitorComplete
	private  String firstMonitorState;

	public Date getFirstMonitorDate() {
		return firstMonitorDate;
	}

	public void setFirstMonitorDate(Date firstMonitorDate) {
		this.firstMonitorDate = firstMonitorDate;
	}

	public String getFirstMonitorState() {
		return firstMonitorState;
	}

	public void setFirstMonitorState(String firstMonitorState) {
		this.firstMonitorState = firstMonitorState;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getBelongUserName() {
		return belongUserName;
	}

	public void setBelongUserName(String belongUserName) {
		this.belongUserName = belongUserName;
	}


	public String getLoanApplyAmountFormat() {
		BigDecimal money = getLoanApplyAmount();
		if (money != null)
			return getFormatMoney(money.toString());
		else
			return "";
	}

	public String getLoanCreditAmountFormat() {
		BigDecimal money = getLoanCreditAmount();
		if (money != null)
			return getFormatMoney(money.toString());
		else
			return "";
	}

	public void setLoanCreditAmountFormat(String loanCreditAmountFormat) {
		this.loanCreditAmountFormat = loanCreditAmountFormat;
	}

	public String getLoanResultAmountFormat() {
		BigDecimal money = getLoanResultAmount();
		if (money != null)
			return getFormatMoney(money.toString());
		else
			return "";
	}

	public void setLoanResultAmountFormat(String loanResultAmountFormat) {
		this.loanResultAmountFormat = loanResultAmountFormat;
	}

	public String getLoanProposeAmountFormat() {
		BigDecimal money = getLoanProposeAmount();
		if (money != null)
			return getFormatMoney(money.toString());
		else
			return "";
	}

	public void setLoanProposeAmountFormat(String loanProposeAmountFormat) {
		this.loanProposeAmountFormat = loanProposeAmountFormat;
	}

	private String getFormatMoney(String money) {
		return IdCardUtil.getFormatMoney(money);
	}

	public void setLoanApplyAmountFormat(String loanApplyAmountFormat) {
		this.loanApplyAmountFormat = loanApplyAmountFormat;
	}

	public String getLoanProcessTypeName() {
		if (getLoanRefuseUser().intValue() != 0 )
			return LoanProcessTypeEnum.REFUSE.typeName;
		String processType = this.getLoanProcessType();
		return LoanProcessTypeEnum.getTypeNameByType(processType);
	}

	public void setLoanProcessTypeName(String loanProcessTypeName) {
		this.loanProcessTypeName = loanProcessTypeName;
	}

	public String getInvestigateUserName() {
		return investigateUserName;
	}

	public void setInvestigateUserName(String investigateUserName) {
		this.investigateUserName = investigateUserName;
	}

	public String getLoanMonitorStateName() {
		return LoanMonitorState.getOptionNameByState(getLoanMonitorState());
	}

	public void setLoanMonitorStateName(String loanMonitorStateName) {
		this.loanMonitorStateName = loanMonitorStateName;
	}

	public String getLoanAfterStateName() {
		return loanAfterStateName;
	}

	public void setLoanAfterStateName(String loanAfterStateName) {
		this.loanAfterStateName = loanAfterStateName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCustomerInfo() {
		StringBuffer customerInfo = new StringBuffer("");
		customerInfo.append(super.getLoanName());
		if (StringUtils.isNotBlank(getCustomerSexCN()))
			customerInfo.append(" ").append(getCustomerSexCN());
		if (super.getLoanAge() != null && super.getLoanAge().intValue() != 0 )
			customerInfo.append(" ").append(super.getLoanAge()).append("岁");
		customerInfo.append("<br>");
		customerInfo.append(this.getLoanTelNumForAllot()).append(" ");
		return customerInfo.toString();
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String getCardInfo() {
		StringBuffer cardInfo = new StringBuffer("");
		cardInfo.append(getLoanIdentifyTypeName()).append("<br>").append(this.getLoanIdentifyNumX());
		return cardInfo.toString();
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public void setLoanTelNumX(String loanTelNumX) {
		this.loanTelNumX = loanTelNumX;
	}


	public String getLoanIdentifyNumX() {
		loanIdentifyNumX = getLoanIdentifyNum();
		String identifyType = getLoanIdentifyType();
		return IdCardUtil.getIdentifyNumX(loanIdentifyNumX, identifyType, 1);
	}

	public void setLoanIdentifyNumX(String loanIdentifyNumX) {
		this.loanIdentifyNumX = loanIdentifyNumX;
	}

	public String getLoanIdentifyTypeName() {
		return loanIdentifyTypeName;
	}

	public void setLoanIdentifyTypeName(String loanIdentifyTypeName) {
		this.loanIdentifyTypeName = loanIdentifyTypeName;
	}

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public String getCustomerSexCN() {
		return customerSexCN;
	}

	public void setCustomerSexCN(String customerSexCN) {
		this.customerSexCN = customerSexCN;
	}

	private String getLoanTelNumForAllot() {
		loanTelNumX = getLoanTelNum();
		if (StringUtils.isNotBlank(loanTelNumX)){
			int length = loanTelNumX.length();

			return getLoanTelNumX();
		}
		return "";
	}
	public String getLoanTelNumX() {
		loanTelNumX = getLoanTelNum();
		return IdCardUtil.getTelNumX(loanTelNumX,1);
	}

	public String getCollectionState() {
		return LoanCollectionState.getNameByState(getLoanCollectionState());
	}

	public void setCollectionState(String collectionState) {
		this.collectionState = collectionState;
	}

}
