package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色功能
 */
public class PmsRoleFunc implements Serializable {

	// Fields

	private static final long serialVersionUID = 8402339374199823185L;
	private Integer prfRoleFuncId;
	private Integer prfRoleId;
	private String prfFuncId;
	private Date prfCreateDate;
	private Date prfUpdateDate;
	private Integer prfCreateUser;
	private Integer prfUpdateUser;

	// Constructors

	/** default constructor */
	public PmsRoleFunc() {
	}

	/** minimal constructor */
	public PmsRoleFunc(Integer prfRoleFuncId) {
		this.prfRoleFuncId = prfRoleFuncId;
	}

	/** full constructor */
	public PmsRoleFunc(Integer prfRoleFuncId, Integer prfRoleId,
			String prfFuncId, Date prfCreateDate, Date prfUpdateDate,
			Integer prfCreateUser, Integer prfUpdateUser) {
		this.prfRoleFuncId = prfRoleFuncId;
		this.prfRoleId = prfRoleId;
		this.prfFuncId = prfFuncId;
		this.prfCreateDate = prfCreateDate;
		this.prfUpdateDate = prfUpdateDate;
		this.prfCreateUser = prfCreateUser;
		this.prfUpdateUser = prfUpdateUser;
	}

	// Property accessors

	public Integer getPrfRoleFuncId() {
		return this.prfRoleFuncId;
	}

	public void setPrfRoleFuncId(Integer prfRoleFuncId) {
		this.prfRoleFuncId = prfRoleFuncId;
	}

	public Integer getPrfRoleId() {
		return this.prfRoleId;
	}

	public void setPrfRoleId(Integer prfRoleId) {
		this.prfRoleId = prfRoleId;
	}

	public String getPrfFuncId() {
		return this.prfFuncId;
	}

	public void setPrfFuncId(String prfFuncId) {
		this.prfFuncId = prfFuncId;
	}

	public Date getPrfCreateDate() {
		return this.prfCreateDate;
	}

	public void setPrfCreateDate(Date prfCreateDate) {
		this.prfCreateDate = prfCreateDate;
	}

	public Date getPrfUpdateDate() {
		return this.prfUpdateDate;
	}

	public void setPrfUpdateDate(Date prfUpdateDate) {
		this.prfUpdateDate = prfUpdateDate;
	}

	public Integer getPrfCreateUser() {
		return this.prfCreateUser;
	}

	public void setPrfCreateUser(Integer prfCreateUser) {
		this.prfCreateUser = prfCreateUser;
	}

	public Integer getPrfUpdateUser() {
		return this.prfUpdateUser;
	}

	public void setPrfUpdateUser(Integer prfUpdateUser) {
		this.prfUpdateUser = prfUpdateUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((prfCreateDate == null) ? 0 : prfCreateDate.hashCode());
		result = prime * result
				+ ((prfCreateUser == null) ? 0 : prfCreateUser.hashCode());
		result = prime * result
				+ ((prfFuncId == null) ? 0 : prfFuncId.hashCode());
		result = prime * result
				+ ((prfRoleFuncId == null) ? 0 : prfRoleFuncId.hashCode());
		result = prime * result
				+ ((prfRoleId == null) ? 0 : prfRoleId.hashCode());
		result = prime * result
				+ ((prfUpdateDate == null) ? 0 : prfUpdateDate.hashCode());
		result = prime * result
				+ ((prfUpdateUser == null) ? 0 : prfUpdateUser.hashCode());
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
		PmsRoleFunc other = (PmsRoleFunc) obj;
		if (prfCreateDate == null) {
			if (other.prfCreateDate != null)
				return false;
		} else if (!prfCreateDate.equals(other.prfCreateDate))
			return false;
		if (prfCreateUser == null) {
			if (other.prfCreateUser != null)
				return false;
		} else if (!prfCreateUser.equals(other.prfCreateUser))
			return false;
		if (prfFuncId == null) {
			if (other.prfFuncId != null)
				return false;
		} else if (!prfFuncId.equals(other.prfFuncId))
			return false;
		if (prfRoleFuncId == null) {
			if (other.prfRoleFuncId != null)
				return false;
		} else if (!prfRoleFuncId.equals(other.prfRoleFuncId))
			return false;
		if (prfRoleId == null) {
			if (other.prfRoleId != null)
				return false;
		} else if (!prfRoleId.equals(other.prfRoleId))
			return false;
		if (prfUpdateDate == null) {
			if (other.prfUpdateDate != null)
				return false;
		} else if (!prfUpdateDate.equals(other.prfUpdateDate))
			return false;
		if (prfUpdateUser == null) {
			if (other.prfUpdateUser != null)
				return false;
		} else if (!prfUpdateUser.equals(other.prfUpdateUser))
			return false;
		return true;
	}

}