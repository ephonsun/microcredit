package banger.domain.html5;

import banger.framework.codetable.CodeTable;

import java.util.Date;

/**
 * 进件申请信息表
 */
public class IntoCustApplyInfoQuery extends  IntoCustApplyInfo{
	private  String  useType;
	private  String cardType;
	private String startDate;
	private String endDate;
	private String applyAmountStr;
	private String signSateStr;
	private String cardTypeName;

	private Date signDates;
	private String custAgeStr;

	@CodeTable(name="cdDictColByName",key="custSex",params="CD_SEX")
	private String sexCN;
	@CodeTable(name = "cdDictColByName", key = "loanUserOption", params = "CD_LOAN_USER_OPTION")
	private String loanUserOptionName;

	public String getCustAgeStr() {
		return custAgeStr;
	}

	public void setCustAgeStr(String custAgeStr) {
		this.custAgeStr = custAgeStr;
	}

	public String getCardTypeName() {
		return "居民身份证";
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getLoanUserOptionName() {
		return loanUserOptionName;
	}

	public void setLoanUserOptionName(String loanUserOptionName) {
		this.loanUserOptionName = loanUserOptionName;
	}

	public Date getSignDates() {
		return signDates;
	}

	public void setSignDates(Date signDates) {
		this.signDates = signDates;
	}





	public String getApplyAmountStr() {
		return applyAmountStr;
	}

	public String getSignSateStr() {
		return signSateStr;
	}

	public void setSignSateStr(String signSateStr) {
		this.signSateStr = signSateStr;
	}

	public void setApplyAmountStr(String applyAmountStr) {
		this.applyAmountStr = applyAmountStr;
	}

	public String getSexCN() {
		return sexCN;
	}

	public void setSexCN(String sexCN) {
		this.sexCN = sexCN;
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

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getCardType() {
		return "身份证";
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPhoneNumberFormat(){
		return getCustPhone();
	}
	public String getLoanMoneyFormat() {
		return applyAmountStr;
	}
	public String getCardNumberFormat(){
		return getIdCard();
	}

}
