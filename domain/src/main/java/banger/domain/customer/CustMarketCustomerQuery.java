package banger.domain.customer;

import banger.framework.codetable.CodeTable;
import banger.framework.util.IdCardUtil;
import banger.framework.util.SensitiveUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;


/**
 * 营销客户查询对象
 */
public class CustMarketCustomerQuery extends  CustMarketCustomer{
	
	private String loanMoneyStr;
	
	@CodeTable(name="cdDictColByName",key="customerSex",params="CD_SEX")
	private String sexCN;
	
	private String ageStr;
	
	@CodeTable(name="cdDictColByName",key="cardType",params="CD_IDENTIFY_TYPE")
	private String cardTypeCN;


	private Date signDates;
	private String signDateStr;

	private String createDateStr;
	
	private String startDate;
	
	private String endDate;
	
	private String marketCustomerIds;//勾选的待分配客户id
	
	private Integer teamGroupId;//团队id

	public String getLoanMoneyStr() {
		return loanMoneyStr;
	}

	public String getLoanMoneyFormat() {
		return loanMoneyStr;
	}

	public void setLoanMoneyStr(String loanMoneyStr) {
		this.loanMoneyStr = loanMoneyStr;
	}

	public String getSexCN() {
		return sexCN;
	}

	public void setSexCN(String sexCN) {
		this.sexCN = sexCN;
	}

	public String getAgeStr() {
		return ageStr;
	}

	public void setAgeStr(String ageStr) {
		this.ageStr = ageStr;
	}

	public String getCardTypeCN() {
		return cardTypeCN;
	}

	public void setCardTypeCN(String cardTypeCN) {
		this.cardTypeCN = cardTypeCN;
	}

	public String getCardTypeName(){
		return cardTypeCN;
	}

	public String getCardNumberFormat(){
		/* 预申请不脱敏
		String loanIdentifyNumX = getCardNumber();
		String identifyType = getCardType();
		return IdCardUtil.getIdentifyNumX(loanIdentifyNumX, identifyType);
		*/
		return getCardNumber();
	}

	public String getPhoneNumberFormat(){
		/*
		String loanTelNumX = getPhoneNumber();
		if(StringUtils.isBlank(loanTelNumX))
			return "";
		int len = loanTelNumX.length();
		String temp = loanTelNumX;
		if(len>6) {
			temp = SensitiveUtil.getTakeOffSensitiveLast(loanTelNumX, 4);
		}
		return temp;
		*/
		return getPhoneNumber();
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
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

	public String getMarketCustomerIds() {
		return marketCustomerIds;
	}

	public void setMarketCustomerIds(String marketCustomerIds) {
		this.marketCustomerIds = marketCustomerIds;
	}

	public Integer getTeamGroupId() {
		return teamGroupId;
	}

	public void setTeamGroupId(Integer teamGroupId) {
		this.teamGroupId = teamGroupId;
	}

	public Date getSignDates() {
		return signDates;
	}

	public void setSignDates(Date signDates) {
		this.signDates = signDates;
	}

	public String getSignDateStr() {
		return signDateStr;
	}

	public void setSignDateStr(String signDateStr) {
		this.signDateStr = signDateStr;
	}
}
