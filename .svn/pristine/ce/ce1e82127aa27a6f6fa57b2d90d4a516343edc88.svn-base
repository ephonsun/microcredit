package banger.domain.customer;

import java.io.Serializable;

import banger.framework.codetable.CodeTable;

/**
 * 客户黑名单表
 */
public class CustCustomerBlackQuery extends CustCustomerBlack implements Serializable {
	private String userName;
	
	@CodeTable(name="cdDictColByName",key="cardType",params="CD_IDENTIFY_TYPE")
	private String cardTypeCN;

	private String startDate;
	
	private String endDate;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardTypeCN() {
		return cardTypeCN;
	}

	public void setCardTypeCN(String cardTypeCN) {
		this.cardTypeCN = cardTypeCN;
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

	
}
