package banger.domain.permission;

public class PmsUserRoles_Query extends PmsUserRoles {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer roleType;
	private String userName;
	private String deptName;


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
}
