package banger.framework.component.dataimport.context;

/**
 * 导入文件列信息
 */
public class ColumnInfo extends FieldInfo {
	private static final long serialVersionUID = -3624823639269660530L;
	/**
	 * 列索引
	 */
	protected int index;
	/**
	 * 列名
	 */
	protected String columnName;
	
	public ColumnInfo(){
		this(-1,"");
	}
	
	public ColumnInfo(int index,String columnName){
		this(index,columnName,null);
	}
	
	public ColumnInfo(int index,String columnName,String fieldName){
		this.index = index;
		this.columnName = columnName;
		this.fieldName = fieldName;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
