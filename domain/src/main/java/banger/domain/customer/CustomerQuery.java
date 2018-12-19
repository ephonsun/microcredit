package banger.domain.customer;

import banger.framework.codetable.CodeTable;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;

/**
 * 
 */
public class CustomerQuery extends CustBasicInfo {

	@CodeTable(name = "cdDictColByName", key = "customerSex", params = "CD_SEX")
	protected String customerSexName;

	@CodeTable(name = "cdDictColByName", key = "customerLevel", params = "CD_CUSTOMER_LEVEL")
	protected String customerLevelName;

	private String belongUserName;

	public String getCustomerInfo(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getCustomerName());
		if(StringUtil.isNotEmpty(customerSexName))
			sb.append(" "+this.getCustomerSexName());
		if(this.getCustomerAge()!=null)
			sb.append(" "+this.getCustomerAge()+"岁");
		if(StringUtil.isNotEmpty(this.getPhoneNumber()))
			sb.append(" "+ IdCardUtil.getTelNumX(this.getPhoneNumber(),1));
		return sb.toString();
	}
	@CodeTable(name = "cdDictColByName", key = "identifyType", params = "CD_IDENTIFY_TYPE")
	private String identifyTypeName;

	public String getIdentifyTypeName() {
		return identifyTypeName;
	}

	public void setIdentifyTypeName(String identifyTypeName) {
		this.identifyTypeName = identifyTypeName;
	}

	private String cardInfo;

	public String getCardInfo() {
		StringBuffer cardInfo = new StringBuffer("");
		cardInfo.append(getIdentifyTypeName()).append(" ").append(IdCardUtil.getIdentifyNumX(getIdentifyNum(), getIdentifyType(), 1));
		return cardInfo.toString();
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getBelongUserName() {
		return belongUserName;
	}

	public void setBelongUserName(String belongUserName) {
		this.belongUserName = belongUserName;
	}

	public String getCustomerSexName() {
		return customerSexName;
	}

	public void setCustomerSexName(String customerSexName) {
		this.customerSexName = customerSexName;
	}

	public String getCustomerLevelName() {
		return customerLevelName;
	}

	public void setCustomerLevelName(String customerLevelName) {
		this.customerLevelName = customerLevelName;
	}
}
