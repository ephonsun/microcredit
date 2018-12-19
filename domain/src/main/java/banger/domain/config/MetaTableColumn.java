package banger.domain.config;

import banger.framework.sql.command.ValueType;

public class MetaTableColumn {
	private String columnName;				//列名
	private String columnType;
	private String tableName;
	private Integer length;
	private Integer scale;
	private ValueType type;
	private Boolean isPk;
	private String comments;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getScale() {
		return scale;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	public ValueType getType() {
		return type;
	}
	public void setType(ValueType type) {
		this.type = type;
	}
	public Boolean getIsPk() {
		return isPk;
	}
	public void setIsPk(Boolean isPk) {
		this.isPk = isPk;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
