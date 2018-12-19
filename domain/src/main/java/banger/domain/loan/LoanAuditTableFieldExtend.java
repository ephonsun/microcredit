package banger.domain.loan;

import banger.domain.config.AutoTableColumn;

public class LoanAuditTableFieldExtend extends AutoTableColumn {

	private Integer auditFieldId;
	private static final long serialVersionUID = 1L;

	public Integer getAuditFieldId() {
		return auditFieldId;
	}

	public void setAuditFieldId(Integer auditFieldId) {
		this.auditFieldId = auditFieldId;
	}
}
