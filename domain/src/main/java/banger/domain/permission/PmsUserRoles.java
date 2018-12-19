package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色
 */
public class PmsUserRoles implements Serializable {
	private static final long serialVersionUID = -1206636453885638957L;
	private Integer purUserRolesId;
	private Integer purUserId;
	private Integer purRoleId;
	private Date purCreateDate;
	private Date purUpdateDate;
	private Integer purCreateUser;
	private Integer purUpdateUser;

	// Constructors

	/** default constructor */
	public PmsUserRoles() {
	}

	/** minimal constructor */
	public PmsUserRoles(Integer purUserRolesId, Integer purUserId,
			Integer purRoleId) {
		this.purUserRolesId = purUserRolesId;
		this.purUserId = purUserId;
		this.purRoleId = purRoleId;
	}

	/** full constructor */
	public PmsUserRoles(Integer purUserRolesId, Integer purUserId,
			Integer purRoleId, Date purCreateDate,
			Date purUpdateDate, Integer purCreateUser,
			Integer purUpdateUser) {
		this.purUserRolesId = purUserRolesId;
		this.purUserId = purUserId;
		this.purRoleId = purRoleId;
		this.purCreateDate = purCreateDate;
		this.purUpdateDate = purUpdateDate;
		this.purCreateUser = purCreateUser;
		this.purUpdateUser = purUpdateUser;
	}

	// Property accessors

	public Integer getPurUserRolesId() {
		return this.purUserRolesId;
	}

	public void setPurUserRolesId(Integer purUserRolesId) {
		this.purUserRolesId = purUserRolesId;
	}

	public Integer getPurUserId() {
		return this.purUserId;
	}

	public void setPurUserId(Integer purUserId) {
		this.purUserId = purUserId;
	}

	public Integer getPurRoleId() {
		return this.purRoleId;
	}

	public void setPurRoleId(Integer purRoleId) {
		this.purRoleId = purRoleId;
	}

	public Date getPurCreateDate() {
		return this.purCreateDate;
	}

	public void setPurCreateDate(Date purCreateDate) {
		this.purCreateDate = purCreateDate;
	}

	public Date getPurUpdateDate() {
		return this.purUpdateDate;
	}

	public void setPurUpdateDate(Date purUpdateDate) {
		this.purUpdateDate = purUpdateDate;
	}

	public Integer getPurCreateUser() {
		return this.purCreateUser;
	}

	public void setPurCreateUser(Integer purCreateUser) {
		this.purCreateUser = purCreateUser;
	}

	public Integer getPurUpdateUser() {
		return this.purUpdateUser;
	}

	public void setPurUpdateUser(Integer purUpdateUser) {
		this.purUpdateUser = purUpdateUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((purCreateDate == null) ? 0 : purCreateDate.hashCode());
		result = prime * result
				+ ((purCreateUser == null) ? 0 : purCreateUser.hashCode());
		result = prime * result
				+ ((purRoleId == null) ? 0 : purRoleId.hashCode());
		result = prime * result
				+ ((purUpdateDate == null) ? 0 : purUpdateDate.hashCode());
		result = prime * result
				+ ((purUpdateUser == null) ? 0 : purUpdateUser.hashCode());
		result = prime * result
				+ ((purUserId == null) ? 0 : purUserId.hashCode());
		result = prime * result
				+ ((purUserRolesId == null) ? 0 : purUserRolesId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PmsUserRoles other = (PmsUserRoles) obj;
		if (purCreateDate == null) {
			if (other.purCreateDate != null)
				return false;
		} else if (!purCreateDate.equals(other.purCreateDate))
			return false;
		if (purCreateUser == null) {
			if (other.purCreateUser != null)
				return false;
		} else if (!purCreateUser.equals(other.purCreateUser))
			return false;
		if (purRoleId == null) {
			if (other.purRoleId != null)
				return false;
		} else if (!purRoleId.equals(other.purRoleId))
			return false;
		if (purUpdateDate == null) {
			if (other.purUpdateDate != null)
				return false;
		} else if (!purUpdateDate.equals(other.purUpdateDate))
			return false;
		if (purUpdateUser == null) {
			if (other.purUpdateUser != null)
				return false;
		} else if (!purUpdateUser.equals(other.purUpdateUser))
			return false;
		if (purUserId == null) {
			if (other.purUserId != null)
				return false;
		} else if (!purUserId.equals(other.purUserId))
			return false;
		if (purUserRolesId == null) {
			if (other.purUserRolesId != null)
				return false;
		} else if (!purUserRolesId.equals(other.purUserRolesId))
			return false;
		return true;
	}

}