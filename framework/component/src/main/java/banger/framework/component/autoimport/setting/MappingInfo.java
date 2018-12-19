package banger.framework.component.autoimport.setting;

import banger.framework.component.autoimport.IPropertyInfo;

/**
 * 导入文件列信息
 */
public class MappingInfo implements IPropertyInfo {

	private static final long serialVersionUID = -3624823639269660530L;

	/**
	 * 文本列索引
	 */
	protected int sourceColumnIndex;

	/**
	 * 原表列名
	 */
	protected String sourceColumnName;

	/**
	 * 原表列名
	 */
	protected String sourceTableName;

	/**
	 * 目标表列名
	 */
	protected String targetColumnName;


	/**
	 * 目标表列名
	 */
	protected String targetColumnDisplay;

	/**
	 * 目标表列名表名
	 */
	protected String targetColumnTable;

	/**
	 * 目标表列类型
	 */
	protected String targetColumnType;

	/**
	 * 目标数据字典
	 */
	protected String targetDictName;

	public MappingInfo(){
		this.sourceColumnIndex = -1;
		this.sourceColumnName = "";
		this.sourceTableName = "";
		this.targetColumnName = "";
		this.targetColumnDisplay = "";
		this.targetColumnTable = "";
		this.targetColumnType = "";
		this.targetDictName = "";
	}

	public int getSourceColumnIndex() {
		return sourceColumnIndex;
	}

	public void setSourceColumnIndex(int sourceColumnIndex) {
		this.sourceColumnIndex = sourceColumnIndex;
	}

	public String getSourceColumnName() {
		return sourceColumnName;
	}

	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public String getTargetColumnTable() {
		return targetColumnTable;
	}

	public void setTargetColumnTable(String targetColumnTable) {
		this.targetColumnTable = targetColumnTable;
	}

	public String getTargetColumnType() {
		return targetColumnType;
	}

	public void setTargetColumnType(String targetColumnType) {
		this.targetColumnType = targetColumnType;
	}

	public String getTargetDictName() {
		return targetDictName;
	}

	public void setTargetDictName(String targetDictName) {
		this.targetDictName = targetDictName;
	}

	public String getTargetColumnName() {
		return targetColumnName;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}

	public String getTargetColumnDisplay() {
		return targetColumnDisplay;
	}

	public void setTargetColumnDisplay(String targetColumnDisplay) {
		this.targetColumnDisplay = targetColumnDisplay;
	}

	public String getPropertyName() {
		return this.targetColumnName;
	}

	public String getPropertyDisplay() {
		return this.targetColumnDisplay;
	}

	@Override
	public Object[] getCodeTableParams() {
		return new Object[]{};
	}
}
