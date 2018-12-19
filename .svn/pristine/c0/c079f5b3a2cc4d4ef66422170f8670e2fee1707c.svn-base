package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构类
 */
public class PmsDept implements Serializable {

	private static final long serialVersionUID = -4793308578007819435L;
	// Fields

	private Integer deptId;
	private String deptName;
	private Integer deptParentId;
	private String deptCode;
	private String deptSearchCode;
	private String deptRemark;
	private Integer deptIsdel;
	private Integer deptSort;
	private Integer deptDepth;
	private Date deptCreateDate;
	private Date deptUpdateDate;
	private Integer deptCreateUser;
	private Integer deptUpdateUser;

	// Constructors

	/** default constructor */
	public PmsDept() {
	}

	/** minimal constructor */
	public PmsDept(Integer deptId, Integer deptParentId) {
		this.deptId = deptId;
		this.deptParentId = deptParentId;
	}

	/** full constructor */
	public PmsDept(Integer deptId, String deptName, Integer deptParentId,
			String deptCode, String deptSearchCode, String deptRemark,
			Integer deptIsdel, Integer deptSort, Integer deptDepth, Date deptCreateDate,
			Date deptUpdateDate, Integer deptCreateUser,
			Integer deptUpdateUser) {
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptParentId = deptParentId;
		this.deptCode = deptCode;
		this.deptSearchCode = deptSearchCode;
		this.deptRemark = deptRemark;
		this.deptIsdel = deptIsdel;
		this.deptSort = deptSort;
		this.deptDepth = deptDepth;
		this.deptCreateDate = deptCreateDate;
		this.deptUpdateDate = deptUpdateDate;
		this.deptCreateUser = deptCreateUser;
		this.deptUpdateUser = deptUpdateUser;
	}

	// Property accessors

	public Integer getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getDeptParentId() {
		return this.deptParentId;
	}

	public void setDeptParentId(Integer deptParentId) {
		this.deptParentId = deptParentId;
	}

	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptSearchCode() {
		return this.deptSearchCode;
	}

	public void setDeptSearchCode(String deptSearchCode) {
		this.deptSearchCode = deptSearchCode;
	}

	public String getDeptRemark() {
		return this.deptRemark;
	}

	public void setDeptRemark(String deptRemark) {
		this.deptRemark = deptRemark;
	}

	public Integer getDeptIsdel() {
		return this.deptIsdel;
	}

	public void setDeptIsdel(Integer deptIsdel) {
		this.deptIsdel = deptIsdel;
	}

	public Integer getDeptSort() {
		return this.deptSort;
	}

	public void setDeptSort(Integer deptSort) {
		this.deptSort = deptSort;
	}

	public Integer getDeptDepth() {
		return deptDepth;
	}

	public void setDeptDepth(Integer deptDepth) {
		this.deptDepth = deptDepth;
	}

	public Date getDeptCreateDate() {
		return this.deptCreateDate;
	}

	public void setDeptCreateDate(Date deptCreateDate) {
		this.deptCreateDate = deptCreateDate;
	}

	public Date getDeptUpdateDate() {
		return this.deptUpdateDate;
	}

	public void setDeptUpdateDate(Date deptUpdateDate) {
		this.deptUpdateDate = deptUpdateDate;
	}

	public Integer getDeptCreateUser() {
		return this.deptCreateUser;
	}

	public void setDeptCreateUser(Integer deptCreateUser) {
		this.deptCreateUser = deptCreateUser;
	}

	public Integer getDeptUpdateUser() {
		return this.deptUpdateUser;
	}

	public void setDeptUpdateUser(Integer deptUpdateUser) {
		this.deptUpdateUser = deptUpdateUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result
				+ ((deptCreateDate == null) ? 0 : deptCreateDate.hashCode());
		result = prime * result
				+ ((deptCreateUser == null) ? 0 : deptCreateUser.hashCode());
		result = prime * result
				+ ((deptDepth == null) ? 0 : deptDepth.hashCode());
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result
				+ ((deptIsdel == null) ? 0 : deptIsdel.hashCode());
		result = prime * result
				+ ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result
				+ ((deptParentId == null) ? 0 : deptParentId.hashCode());
		result = prime * result
				+ ((deptRemark == null) ? 0 : deptRemark.hashCode());
		result = prime * result
				+ ((deptSearchCode == null) ? 0 : deptSearchCode.hashCode());
		result = prime * result
				+ ((deptSort == null) ? 0 : deptSort.hashCode());
		result = prime * result
				+ ((deptUpdateDate == null) ? 0 : deptUpdateDate.hashCode());
		result = prime * result
				+ ((deptUpdateUser == null) ? 0 : deptUpdateUser.hashCode());
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
		PmsDept other = (PmsDept) obj;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (deptCreateDate == null) {
			if (other.deptCreateDate != null)
				return false;
		} else if (!deptCreateDate.equals(other.deptCreateDate))
			return false;
		if (deptCreateUser == null) {
			if (other.deptCreateUser != null)
				return false;
		} else if (!deptCreateUser.equals(other.deptCreateUser))
			return false;
		if (deptDepth == null) {
			if (other.deptDepth != null)
				return false;
		} else if (!deptDepth.equals(other.deptDepth))
			return false;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptIsdel == null) {
			if (other.deptIsdel != null)
				return false;
		} else if (!deptIsdel.equals(other.deptIsdel))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (deptParentId == null) {
			if (other.deptParentId != null)
				return false;
		} else if (!deptParentId.equals(other.deptParentId))
			return false;
		if (deptRemark == null) {
			if (other.deptRemark != null)
				return false;
		} else if (!deptRemark.equals(other.deptRemark))
			return false;
		if (deptSearchCode == null) {
			if (other.deptSearchCode != null)
				return false;
		} else if (!deptSearchCode.equals(other.deptSearchCode))
			return false;
		if (deptSort == null) {
			if (other.deptSort != null)
				return false;
		} else if (!deptSort.equals(other.deptSort))
			return false;
		if (deptUpdateDate == null) {
			if (other.deptUpdateDate != null)
				return false;
		} else if (!deptUpdateDate.equals(other.deptUpdateDate))
			return false;
		if (deptUpdateUser == null) {
			if (other.deptUpdateUser != null)
				return false;
		} else if (!deptUpdateUser.equals(other.deptUpdateUser))
			return false;
		return true;
	}

}