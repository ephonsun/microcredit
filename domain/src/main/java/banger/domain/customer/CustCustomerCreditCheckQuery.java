package banger.domain.customer;

import java.io.Serializable;

/**
 * 客户征信调查表
 */
public class CustCustomerCreditCheckQuery extends CustCustomerCreditCheck implements Serializable {

	private static final long serialVersionUID = -2977720958574965247L;
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
