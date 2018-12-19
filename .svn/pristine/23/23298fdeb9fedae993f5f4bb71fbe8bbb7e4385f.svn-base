package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色菜单
 */
public class PmsRoleMenu implements Serializable {

	// Fields

	private static final long serialVersionUID = 8373451487324508859L;
	private Integer prmRoleMenuId;
	private Integer prmRoleId;
	private String prmMenuId;
	private Date prmCreateDate;
	private Date prmUpdateDate;
	private Integer prmCreateUser;
	private Integer prmUpdateUser;

	// Constructors

	/** default constructor */
	public PmsRoleMenu() {
	}

	/** minimal constructor */
	public PmsRoleMenu(Integer prmRoleMenuId, Integer prmRoleId,
			String prmMenuId) {
		this.prmRoleMenuId = prmRoleMenuId;
		this.prmRoleId = prmRoleId;
		this.prmMenuId = prmMenuId;
	}

	/** full constructor */
	public PmsRoleMenu(Integer prmRoleMenuId, Integer prmRoleId,
			String prmMenuId, Date prmCreateDate, Date prmUpdateDate,
			Integer prmCreateUser, Integer prmUpdateUser) {
		this.prmRoleMenuId = prmRoleMenuId;
		this.prmRoleId = prmRoleId;
		this.prmMenuId = prmMenuId;
		this.prmCreateDate = prmCreateDate;
		this.prmUpdateDate = prmUpdateDate;
		this.prmCreateUser = prmCreateUser;
		this.prmUpdateUser = prmUpdateUser;
	}

	// Property accessors

	public Integer getPrmRoleMenuId() {
		return this.prmRoleMenuId;
	}

	public void setPrmRoleMenuId(Integer prmRoleMenuId) {
		this.prmRoleMenuId = prmRoleMenuId;
	}

	public Integer getPrmRoleId() {
		return this.prmRoleId;
	}

	public void setPrmRoleId(Integer prmRoleId) {
		this.prmRoleId = prmRoleId;
	}

	public String getPrmMenuId() {
		return this.prmMenuId;
	}

	public void setPrmMenuId(String prmMenuId) {
		this.prmMenuId = prmMenuId;
	}

	public Date getPrmCreateDate() {
		return this.prmCreateDate;
	}

	public void setPrmCreateDate(Date prmCreateDate) {
		this.prmCreateDate = prmCreateDate;
	}

	public Date getPrmUpdateDate() {
		return this.prmUpdateDate;
	}

	public void setPrmUpdateDate(Date prmUpdateDate) {
		this.prmUpdateDate = prmUpdateDate;
	}

	public Integer getPrmCreateUser() {
		return this.prmCreateUser;
	}

	public void setPrmCreateUser(Integer prmCreateUser) {
		this.prmCreateUser = prmCreateUser;
	}

	public Integer getPrmUpdateUser() {
		return this.prmUpdateUser;
	}

	public void setPrmUpdateUser(Integer prmUpdateUser) {
		this.prmUpdateUser = prmUpdateUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((prmCreateDate == null) ? 0 : prmCreateDate.hashCode());
		result = prime * result
				+ ((prmCreateUser == null) ? 0 : prmCreateUser.hashCode());
		result = prime * result
				+ ((prmMenuId == null) ? 0 : prmMenuId.hashCode());
		result = prime * result
				+ ((prmRoleId == null) ? 0 : prmRoleId.hashCode());
		result = prime * result
				+ ((prmRoleMenuId == null) ? 0 : prmRoleMenuId.hashCode());
		result = prime * result
				+ ((prmUpdateDate == null) ? 0 : prmUpdateDate.hashCode());
		result = prime * result
				+ ((prmUpdateUser == null) ? 0 : prmUpdateUser.hashCode());
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
		PmsRoleMenu other = (PmsRoleMenu) obj;
		if (prmCreateDate == null) {
			if (other.prmCreateDate != null)
				return false;
		} else if (!prmCreateDate.equals(other.prmCreateDate))
			return false;
		if (prmCreateUser == null) {
			if (other.prmCreateUser != null)
				return false;
		} else if (!prmCreateUser.equals(other.prmCreateUser))
			return false;
		if (prmMenuId == null) {
			if (other.prmMenuId != null)
				return false;
		} else if (!prmMenuId.equals(other.prmMenuId))
			return false;
		if (prmRoleId == null) {
			if (other.prmRoleId != null)
				return false;
		} else if (!prmRoleId.equals(other.prmRoleId))
			return false;
		if (prmRoleMenuId == null) {
			if (other.prmRoleMenuId != null)
				return false;
		} else if (!prmRoleMenuId.equals(other.prmRoleMenuId))
			return false;
		if (prmUpdateDate == null) {
			if (other.prmUpdateDate != null)
				return false;
		} else if (!prmUpdateDate.equals(other.prmUpdateDate))
			return false;
		if (prmUpdateUser == null) {
			if (other.prmUpdateUser != null)
				return false;
		} else if (!prmUpdateUser.equals(other.prmUpdateUser))
			return false;
		return true;
	}

}