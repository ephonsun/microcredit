package banger.domain.permission;

import java.io.Serializable;

/**
 * 机构类
 */
public class SysTeamMember_Query extends SysTeamMember implements Serializable {

	private static final long serialVersionUID = -2838157691862927307L;

	private String teamName;

	private String userName;
	private Integer roleId;

	private String teamMemberName;


	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setTeamMemberName(String teamMemberName) {
		this.teamMemberName = teamMemberName;
	}

	public String getTeamMemberName(){
		return this.getTeamName() + " : " + this.getUserName();
	}
}