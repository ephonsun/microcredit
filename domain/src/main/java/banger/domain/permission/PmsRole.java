package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 */
public class PmsRole implements Serializable {

	// Fields

	private static final long serialVersionUID = 5044647304750864760L;
	private Integer roleId;
	private Integer roleType;
	private String roleName;
	private Integer roleIsdel;
	private Integer roleSort;
	private String roleRemark;
	private Integer roleIsDefault;
	private Date roleCreateDate;
	private Date roleUpdateDate;
	private Integer roleCreateUser;
	private Integer roleUpdateUser;
    private String roleDeptIds;
    private Integer roleCanApproval;
    private Integer roleIsFixed;
	// Constructors

	/** default constructor */
	public PmsRole() {
	}

	/** minimal constructor */
	public PmsRole(Integer roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public PmsRole(Integer roleId, Integer roleType, String roleName,
			Integer roleIsdel, Integer roleSort, String roleRemark, Date roleCreateDate,
			Date roleUpdateDate, Integer roleCreateUser,
			Integer roleUpdateUser,Integer roleCanApproval) {
		this.roleId = roleId;
		this.roleType = roleType;
		this.roleName = roleName;
		this.roleIsdel = roleIsdel;
		this.roleSort = roleSort;
		this.roleRemark = roleRemark;
		this.roleCreateDate = roleCreateDate;
		this.roleUpdateDate = roleUpdateDate;
		this.roleCreateUser = roleCreateUser;
		this.roleUpdateUser = roleUpdateUser;
		this.roleCanApproval = roleCanApproval;
	}

	// Property accessors

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleType() {
		return this.roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleIsdel() {
		return this.roleIsdel;
	}

	public void setRoleIsdel(Integer roleIsdel) {
		this.roleIsdel = roleIsdel;
	}

	public Integer getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(Integer roleSort) {
		this.roleSort = roleSort;
	}

	public String getRoleRemark() {
		return this.roleRemark;
	}

	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

	public Integer getRoleIsDefault() {
		return roleIsDefault;
	}

	public void setRoleIsDefault(Integer roleIsDefault) {
		this.roleIsDefault = roleIsDefault;
	}

	public Date getRoleCreateDate() {
		return this.roleCreateDate;
	}

	public void setRoleCreateDate(Date roleCreateDate) {
		this.roleCreateDate = roleCreateDate;
	}

	public Date getRoleUpdateDate() {
		return this.roleUpdateDate;
	}

	public void setRoleUpdateDate(Date roleUpdateDate) {
		this.roleUpdateDate = roleUpdateDate;
	}

	public Integer getRoleCreateUser() {
		return this.roleCreateUser;
	}

	public void setRoleCreateUser(Integer roleCreateUser) {
		this.roleCreateUser = roleCreateUser;
	}

	public Integer getRoleUpdateUser() {
		return this.roleUpdateUser;
	}

	public void setRoleUpdateUser(Integer roleUpdateUser) {
		this.roleUpdateUser = roleUpdateUser;
	}

	public Integer getRoleIsFixed() {
		return roleIsFixed;
	}

	public void setRoleIsFixed(Integer roleIsFixed) {
		this.roleIsFixed = roleIsFixed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PmsRole pmsRole = (PmsRole) o;

		if (roleCreateDate != null ? !roleCreateDate.equals(pmsRole.roleCreateDate) : pmsRole.roleCreateDate != null)
			return false;
		if (roleCreateUser != null ? !roleCreateUser.equals(pmsRole.roleCreateUser) : pmsRole.roleCreateUser != null)
			return false;
		if (roleId != null ? !roleId.equals(pmsRole.roleId) : pmsRole.roleId != null) return false;
		if (roleIsDefault != null ? !roleIsDefault.equals(pmsRole.roleIsDefault) : pmsRole.roleIsDefault != null)
			return false;
		if (roleIsdel != null ? !roleIsdel.equals(pmsRole.roleIsdel) : pmsRole.roleIsdel != null) return false;
		if (roleName != null ? !roleName.equals(pmsRole.roleName) : pmsRole.roleName != null) return false;
		if (roleRemark != null ? !roleRemark.equals(pmsRole.roleRemark) : pmsRole.roleRemark != null) return false;
		if (roleSort != null ? !roleSort.equals(pmsRole.roleSort) : pmsRole.roleSort != null) return false;
		if (roleType != null ? !roleType.equals(pmsRole.roleType) : pmsRole.roleType != null) return false;
		if (roleUpdateDate != null ? !roleUpdateDate.equals(pmsRole.roleUpdateDate) : pmsRole.roleUpdateDate != null)
			return false;
		if (roleUpdateUser != null ? !roleUpdateUser.equals(pmsRole.roleUpdateUser) : pmsRole.roleUpdateUser != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = roleId != null ? roleId.hashCode() : 0;
		result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
		result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
		result = 31 * result + (roleIsdel != null ? roleIsdel.hashCode() : 0);
		result = 31 * result + (roleSort != null ? roleSort.hashCode() : 0);
		result = 31 * result + (roleRemark != null ? roleRemark.hashCode() : 0);
		result = 31 * result + (roleIsDefault != null ? roleIsDefault.hashCode() : 0);
		result = 31 * result + (roleCreateDate != null ? roleCreateDate.hashCode() : 0);
		result = 31 * result + (roleUpdateDate != null ? roleUpdateDate.hashCode() : 0);
		result = 31 * result + (roleCreateUser != null ? roleCreateUser.hashCode() : 0);
		result = 31 * result + (roleUpdateUser != null ? roleUpdateUser.hashCode() : 0);
		return result;
	}

    public String getRoleDeptIds() {
        return roleDeptIds;
    }

    public void setRoleDeptIds(String roleDeptIds) {
        this.roleDeptIds = roleDeptIds;
    }

	public Integer getRoleCanApproval() {
		return roleCanApproval;
	}

	public void setRoleCanApproval(Integer roleCanApproval) {
		this.roleCanApproval = roleCanApproval;
	}
}