package banger.domain.permission;

import java.io.Serializable;

/**
 * 机构类
 */
public class SysTeamGroup_Query extends SysTeamGroup implements Serializable {

	private static final long serialVersionUID = 6316068908046585888L;

	private String leaderName;
	private String managerName;
	private String backerName;

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getBackerName() {
		return backerName;
	}

	public void setBackerName(String backerName) {
		this.backerName = backerName;
	}
}