package banger.domain.permission;

import java.io.Serializable;

/**
 * PmsUser entity. @author MyEclipse Persistence Tools
 */

public class UserLogin implements Serializable {

	private static final long serialVersionUID = -1447597860090812707L;
	private String account;
	private String password;
	private String checkCode;
	private String needRandNum;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getNeedRandNum() {
		return needRandNum;
	}

	public void setNeedRandNum(String needRandNum) {
		this.needRandNum = needRandNum;
	}
}