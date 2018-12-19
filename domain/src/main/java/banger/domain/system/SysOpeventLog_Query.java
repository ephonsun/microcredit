package banger.domain.system;

public class SysOpeventLog_Query extends SysOpeventLog{

	private static final long serialVersionUID = -4634802127034821234L;

	private String opeventUserName;
	private String opeventUserAccount;

	public String getOpeventUserName() {
		return opeventUserName;
	}

	public void setOpeventUserName(String opeventUserName) {
		this.opeventUserName = opeventUserName;
	}

	public String getOpeventUserAccount() {
		return opeventUserAccount;
	}

	public void setOpeventUserAccount(String opeventUserAccount) {
		this.opeventUserAccount = opeventUserAccount;
	}
}
