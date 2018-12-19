package banger.domain.product;

import banger.framework.util.IdCardUtil;
import banger.framework.util.SensitiveUtil;
import org.apache.commons.lang.StringUtils;

import banger.framework.codetable.CodeTable;

public class ProdIntentCustomer_Query extends ProdIntentCustomer {

	private static final long serialVersionUID = 1597643034880233226L;

	@CodeTable(name="cdDictColByName",key="picGender",params="CD_CUSTOMER_SEX")
	private String picGenderName;
	
	@CodeTable(name="cdDictColByName",key="picCertificateType",params="CD_IDENTIFY_TYPE")
	private String picCertificateTypeName;
	
	private String picCreateUserName;
	private String picPhoneX;
	private String picCertificateNumX;

	public String getPicGenderName() {
		return picGenderName;
	}

	public void setPicGenderName(String picGenderName) {
		this.picGenderName = picGenderName;
	}

	public String getPicCertificateTypeName() {
		return picCertificateTypeName;
	}

	public void setPicCertificateTypeName(String picCertificateTypeName) {
		this.picCertificateTypeName = picCertificateTypeName;
	}

	public String getPicCreateUserName() {
		return picCreateUserName;
	}

	public void setPicCreateUserName(String picCreateUserName) {
		this.picCreateUserName = picCreateUserName;
	}

	/**
	 * 脱敏后面4位
	 * @return
	 */
	public String getPicPhoneX() {
		picPhoneX = getPicPhone();
		if(StringUtils.isBlank(picPhoneX))
			return "";
		return IdCardUtil.getTelNumX(picPhoneX,1);
	}

	public void setPicPhoneX(String picPhoneX) {
		this.picPhoneX = picPhoneX;
	}

	/**
	 *  18位 脱敏 7-14 15 脱敏位 7-12
	 * @return
	 */
	public String getPicCertificateNumX() {
		picCertificateNumX = getPicCertificateNum();
		String identifyType = getPicCertificateType();
		return IdCardUtil.getIdentifyNumX(picCertificateNumX, identifyType, 1);
	}

	public void setPicCertificateNumX(String picCertificateNumX) {
		this.picCertificateNumX = picCertificateNumX;
	}

	public static void main(String[] args) {
		ProdIntentCustomer_Query query = new ProdIntentCustomer_Query();
		query.setPicPhoneX("15211321321321");
		System.out.println(query.getPicPhoneX());
		query.setPicCertificateNumX("330454199312484");
		System.out.println(query.getPicCertificateNumX());
	}
}
