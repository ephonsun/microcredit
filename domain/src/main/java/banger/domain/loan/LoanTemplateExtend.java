package banger.domain.loan;

import java.io.Serializable;
import java.util.List;

/**
 * 贷款申请模板
 * @author zhusw
 *
 */
public class LoanTemplateExtend implements Serializable {
	private static final long serialVersionUID = -3811185113256807416L;
	private Integer tableId;					//主键ID
	private String tableDbName;					//数据库表名
	private String tableModuleName;					//表模版名
	private String tableDisplayName;					//表显示名称
	private Integer tableType;					//表类型
	private Integer tableIsActived;
	private Integer isActived;
	private Object data;
	private String tableHide; //模板是否隐藏
	List<LoanFieldExtend> fieldList;

	public Integer getTableId() {
		return tableId;
	}
	
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	
	public String getTableDbName() {
		return tableDbName;
	}
	
	public void setTableDbName(String tableDbName) {
		this.tableDbName = tableDbName;
	}
	
	public String getTableModuleName() {
		return tableModuleName;
	}
	
	public void setTableModuleName(String tableModuleName) {
		this.tableModuleName = tableModuleName;
	}
	
	public String getTableDisplayName() {
		return tableDisplayName;
	}
	
	public void setTableDisplayName(String tableDisplayName) {
		this.tableDisplayName = tableDisplayName;
	}
	
	public Integer getTableType() {
		return tableType;
	}
	
	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public Integer getTableIsActived() {
		return tableIsActived;
	}

	public void setTableIsActived(Integer tableIsActived) {
		this.tableIsActived = tableIsActived;
	}

	public Integer getIsActived() {
		return isActived;
	}

	public void setIsActived(Integer isActived) {
		this.isActived = isActived;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getTableHide() {
		return tableHide;
	}

	public void setTableHide(String tableHide) {
		this.tableHide = tableHide;
	}

	public List<LoanFieldExtend> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<LoanFieldExtend> fieldList) {
		this.fieldList = fieldList;
	}
}
