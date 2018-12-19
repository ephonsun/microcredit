package banger.domain.permission;


import java.io.Serializable;

/**
 * PmsUser entity. @author MyEclipse Persistence Tools
 */

public class PmsBancsTelm implements Serializable {

	// Fields

	private static final long serialVersionUID = -1095598137613326208L;
	private String cwo8TellerNo; // 柜员编号
	private String cwo8BrchNo;// 所属机构编号
	private String cwo8TellerName; // 柜员名称
	private String cwo8UserType;// 柜员类型
	private String cwo8Capable;// 柜员级别
	private String cwo8Stat;//柜员状态  状态为09-记录删除(修改),99-记录删除(删除)的柜员不同步
	private String importResult;// 导入结果
    private String importResultDetail;// 导入结果描述

    public String getCwo8TellerNo() {
        return cwo8TellerNo;
    }

    public void setCwo8TellerNo(String cwo8TellerNo) {
        this.cwo8TellerNo = cwo8TellerNo;
    }

    public String getCwo8BrchNo() {
        return cwo8BrchNo;
    }

    public void setCwo8BrchNo(String cwo8BrchNo) {
        this.cwo8BrchNo = cwo8BrchNo;
    }

    public String getCwo8TellerName() {
        return cwo8TellerName;
    }

    public void setCwo8TellerName(String cwo8TellerName) {
        this.cwo8TellerName = cwo8TellerName;
    }

    public String getCwo8UserType() {
        return cwo8UserType;
    }

    public void setCwo8UserType(String cwo8UserType) {
        this.cwo8UserType = cwo8UserType;
    }

    public String getCwo8Capable() {
        return cwo8Capable;
    }

    public void setCwo8Capable(String cwo8Capable) {
        this.cwo8Capable = cwo8Capable;
    }

    public String getImportResult() {
        return importResult;
    }

    public void setImportResult(String importResult) {
        this.importResult = importResult;
    }

    public String getImportResultDetail() {
        return importResultDetail;
    }

    public void setImportResultDetail(String importResultDetail) {
        this.importResultDetail = importResultDetail;
    }

	public String getCwo8Stat() {
		return cwo8Stat;
	}

	public void setCwo8Stat(String cwo8Stat) {
		this.cwo8Stat = cwo8Stat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cwo8BrchNo == null) ? 0 : cwo8BrchNo.hashCode());
		result = prime * result
				+ ((cwo8Capable == null) ? 0 : cwo8Capable.hashCode());
		result = prime * result
				+ ((cwo8Stat == null) ? 0 : cwo8Stat.hashCode());
		result = prime * result
				+ ((cwo8TellerName == null) ? 0 : cwo8TellerName.hashCode());
		result = prime * result
				+ ((cwo8TellerNo == null) ? 0 : cwo8TellerNo.hashCode());
		result = prime * result
				+ ((cwo8UserType == null) ? 0 : cwo8UserType.hashCode());
		result = prime * result
				+ ((importResult == null) ? 0 : importResult.hashCode());
		result = prime
				* result
				+ ((importResultDetail == null) ? 0 : importResultDetail
						.hashCode());
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
		PmsBancsTelm other = (PmsBancsTelm) obj;
		if (cwo8BrchNo == null) {
			if (other.cwo8BrchNo != null)
				return false;
		} else if (!cwo8BrchNo.equals(other.cwo8BrchNo))
			return false;
		if (cwo8Capable == null) {
			if (other.cwo8Capable != null)
				return false;
		} else if (!cwo8Capable.equals(other.cwo8Capable))
			return false;
		if (cwo8Stat == null) {
			if (other.cwo8Stat != null)
				return false;
		} else if (!cwo8Stat.equals(other.cwo8Stat))
			return false;
		if (cwo8TellerName == null) {
			if (other.cwo8TellerName != null)
				return false;
		} else if (!cwo8TellerName.equals(other.cwo8TellerName))
			return false;
		if (cwo8TellerNo == null) {
			if (other.cwo8TellerNo != null)
				return false;
		} else if (!cwo8TellerNo.equals(other.cwo8TellerNo))
			return false;
		if (cwo8UserType == null) {
			if (other.cwo8UserType != null)
				return false;
		} else if (!cwo8UserType.equals(other.cwo8UserType))
			return false;
		if (importResult == null) {
			if (other.importResult != null)
				return false;
		} else if (!importResult.equals(other.importResult))
			return false;
		if (importResultDetail == null) {
			if (other.importResultDetail != null)
				return false;
		} else if (!importResultDetail.equals(other.importResultDetail))
			return false;
		return true;
	}
}