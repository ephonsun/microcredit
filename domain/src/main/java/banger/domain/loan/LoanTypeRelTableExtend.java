package banger.domain.loan;

import banger.domain.config.AutoTableInfo;

public class LoanTypeRelTableExtend extends AutoTableInfo {

	private static final long serialVersionUID = 1L;

	private Integer id; // 主键ID
	private Integer sortno; // 排序号
	private Integer loanTypeId; // 贷款类型主键
	private String loanPrecType; // 贷款过程类型
	private Integer tableId; // 自定义表单主键
	private Integer isActivedthis;
	private Integer fieldIsCondition; // 是否为审批条件

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public Integer getLoanTypeId() {
		return loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId) {
		this.loanTypeId = loanTypeId;
	}

	public String getLoanPrecType() {
		return loanPrecType;
	}

	public void setLoanPrecType(String loanPrecType) {
		this.loanPrecType = loanPrecType;
	}

	@Override
	public Integer getTableId() {
		return tableId;
	}

	@Override
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public Integer getIsActivedthis() {
		return isActivedthis;
	}

	public void setIsActivedthis(Integer isActivedthis) {
		this.isActivedthis = isActivedthis;
	}

	public Integer getFieldIsCondition() {
		return fieldIsCondition;
	}

	public void setFieldIsCondition(Integer fieldIsCondition) {
		this.fieldIsCondition = fieldIsCondition;
	}
}
