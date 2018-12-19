package banger.domain.permission;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能权限
 */
public class PmsFunc implements Serializable {

	// Fields

	private static final long serialVersionUID = 3991847608117358154L;
	private String funcId;
	private String funcName;
	private String funcParentId;
	private String funcModule;
	private Integer funcIsDel;
	private Integer funcDepth;
	private Date funcCreateDate;
	private Date funcUpdateDate;
	private Integer funcCreateUser;
	private Integer funcUpdateUser;

	// Constructors

	/** default constructor */
	public PmsFunc() {
	}

	/** minimal constructor */
	public PmsFunc(String funcId) {
		this.funcId = funcId;
	}

	/** full constructor */
	public PmsFunc(String funcId, String funcName, String funcParentId,
			String funcModule, Integer funcIsDel, Integer funcDepth, Date funcCreateDate,
			Date funcUpdateDate, Integer funcCreateUser,
			Integer funcUpdateUser) {
		this.funcId = funcId;
		this.funcName = funcName;
		this.funcParentId = funcParentId;
		this.funcModule = funcModule;
		this.funcIsDel = funcIsDel;
		this.funcDepth = funcDepth;
		this.funcCreateDate = funcCreateDate;
		this.funcUpdateDate = funcUpdateDate;
		this.funcCreateUser = funcCreateUser;
		this.funcUpdateUser = funcUpdateUser;
	}

	// Property accessors
	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncParentId() {
		return funcParentId;
	}

	public void setFuncParentId(String funcParentId) {
		this.funcParentId = funcParentId;
	}

	public String getFuncModule() {
		return funcModule;
	}

	public void setFuncModule(String funcModule) {
		this.funcModule = funcModule;
	}

	public Integer getFuncIsDel() {
		return funcIsDel;
	}

	public void setFuncIsDel(Integer funcIsDel) {
		this.funcIsDel = funcIsDel;
	}

	public Integer getFuncDepth() {
		return funcDepth;
	}

	public void setFuncDepth(Integer funcDepth) {
		this.funcDepth = funcDepth;
	}

	public Date getFuncCreateDate() {
		return funcCreateDate;
	}

	public void setFuncCreateDate(Date funcCreateDate) {
		this.funcCreateDate = funcCreateDate;
	}

	public Date getFuncUpdateDate() {
		return funcUpdateDate;
	}

	public void setFuncUpdateDate(Date funcUpdateDate) {
		this.funcUpdateDate = funcUpdateDate;
	}

	public Integer getFuncCreateUser() {
		return funcCreateUser;
	}

	public void setFuncCreateUser(Integer funcCreateUser) {
		this.funcCreateUser = funcCreateUser;
	}

	public Integer getFuncUpdateUser() {
		return funcUpdateUser;
	}

	public void setFuncUpdateUser(Integer funcUpdateUser) {
		this.funcUpdateUser = funcUpdateUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((funcCreateDate == null) ? 0 : funcCreateDate.hashCode());
		result = prime * result
				+ ((funcCreateUser == null) ? 0 : funcCreateUser.hashCode());
		result = prime * result
				+ ((funcDepth == null) ? 0 : funcDepth.hashCode());
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		result = prime * result
				+ ((funcIsDel == null) ? 0 : funcIsDel.hashCode());
		result = prime * result
				+ ((funcModule == null) ? 0 : funcModule.hashCode());
		result = prime * result
				+ ((funcName == null) ? 0 : funcName.hashCode());
		result = prime * result
				+ ((funcParentId == null) ? 0 : funcParentId.hashCode());
		result = prime * result
				+ ((funcUpdateDate == null) ? 0 : funcUpdateDate.hashCode());
		result = prime * result
				+ ((funcUpdateUser == null) ? 0 : funcUpdateUser.hashCode());
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
		PmsFunc other = (PmsFunc) obj;
		if (funcCreateDate == null) {
			if (other.funcCreateDate != null)
				return false;
		} else if (!funcCreateDate.equals(other.funcCreateDate))
			return false;
		if (funcCreateUser == null) {
			if (other.funcCreateUser != null)
				return false;
		} else if (!funcCreateUser.equals(other.funcCreateUser))
			return false;
		if (funcDepth == null) {
			if (other.funcDepth != null)
				return false;
		} else if (!funcDepth.equals(other.funcDepth))
			return false;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		if (funcIsDel == null) {
			if (other.funcIsDel != null)
				return false;
		} else if (!funcIsDel.equals(other.funcIsDel))
			return false;
		if (funcModule == null) {
			if (other.funcModule != null)
				return false;
		} else if (!funcModule.equals(other.funcModule))
			return false;
		if (funcName == null) {
			if (other.funcName != null)
				return false;
		} else if (!funcName.equals(other.funcName))
			return false;
		if (funcParentId == null) {
			if (other.funcParentId != null)
				return false;
		} else if (!funcParentId.equals(other.funcParentId))
			return false;
		if (funcUpdateDate == null) {
			if (other.funcUpdateDate != null)
				return false;
		} else if (!funcUpdateDate.equals(other.funcUpdateDate))
			return false;
		if (funcUpdateUser == null) {
			if (other.funcUpdateUser != null)
				return false;
		} else if (!funcUpdateUser.equals(other.funcUpdateUser))
			return false;
		return true;
	}
}