package banger.framework.web.layout;

import banger.framework.util.StringUtil;

public class Field {
	private int cols;			//列数
	private int rows;			//行数
	private String name;		//字段名称
	private String display;		//显示名称
	private String control;		//输入控件
	private String measure;		//单位
	private String codeTable;		//代码表
	private String codeTableParams;			//代码表参数
	private String query;			//查询类型
	private String valueType;		//值类型
	private boolean multi;			//是否多选
	private boolean hour;
	private boolean minute;
	private boolean second;
	private boolean nullable;			//可空
	private boolean readonly;			//只读
	private String valid;				//校验
	private String column;				//数量库列
	private String merge;				//合并单元格
	private String html;				//自定义Html内容
	private Object cell;				//单元格对像
	
	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getControl() {
		return control;
	}
	
	public boolean getNullable(){
		return nullable;
	}
	
	public void setNullable(boolean nullable){
		this.nullable = nullable;
	}

	public Object getCell() {
		return cell;
	}

	public void setCell(Object cell) {
		this.cell = cell;
	}

	public void setControl(String control) {
		this.control = control;
		this.cols = this.getColSpan(control);
		this.query = this.getQuery(control);
		this.valueType = this.getValueType(control);
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
	public String getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	public String getCodeTableParams() {
		return codeTableParams;
	}

	public void setCodeTableParams(String codeTableParams) {
		this.codeTableParams = codeTableParams;
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public boolean getMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	
	public boolean getHour() {
		return hour;
	}

	public void setHour(boolean hour) {
		this.hour = hour;
	}

	public boolean getMinute() {
		return minute;
	}

	public void setMinute(boolean minute) {
		this.minute = minute;
	}

	public boolean getSecond() {
		return second;
	}

	public void setSecond(boolean second) {
		this.second = second;
	}
	
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public boolean getReadonly(){
		return readonly;
	}
	
	public void setReadonly(boolean readonly){
		this.readonly = readonly;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getValids(){
		String valids = "";
		if(!this.nullable)valids="nonEmpty";
		if(!StringUtil.isNullOrEmpty(this.valid)){
			valids=(valids.length()>0)?valids+" "+this.valid:this.valid;
		}
		return valids;
	}
	
	public String getMerge() {
		return merge;
	}

	public void setMerge(String merge) {
		this.merge = merge;
	}
	
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Field(){
		this.cols = 1;
		this.rows = 1;
		this.display = "";
		this.measure = "";
		this.name = "";
		this.control = "";
		this.codeTable = "";
		this.codeTableParams = "";
		this.valid = "";
		this.multi =false;
		this.hour = true;
		this.minute = true;
		this.second = true;
		this.nullable = true;
		this.readonly = false;
		this.column = "";
		this.merge = "";
		this.html = "";
	}
	
	private int getColSpan(String control){
		if("DateSpan".equalsIgnoreCase(control)){
			return 2;
		}else if("TimeSpan".equalsIgnoreCase(control)){
			return 2;
		}else if("NumSpan".equalsIgnoreCase(control)){
			return 2;
		}else if("TextArea".equalsIgnoreCase(control)){
			return -1;
		}
		return 1;
	}
	
	private String getQuery(String control){
		if("TextBox".equalsIgnoreCase(control)){
			return "fuzzy";
		}else if("Combobox".equalsIgnoreCase(control)){
			return (this.multi)?"equals":"equal";
		}else if("DateSpan".equalsIgnoreCase(control)){
			return "datespan";
		}else if("TimeSpan".equalsIgnoreCase(control)){
			return "";
		}else if("NumSpan".equalsIgnoreCase(control)){
			return "numspan";
		}else if("TextArea".equalsIgnoreCase(control)){
			return "fuzzy";
		}
		return "";
	}
	
	private String getValueType(String control){
		if("TextBox".equalsIgnoreCase(control)){
			return "string";
		}else if("Combobox".equalsIgnoreCase(control)){
			return "string";
		}else if("DateSpan".equalsIgnoreCase(control)){
			return "date";
		}else if("TimeSpan".equalsIgnoreCase(control)){
			return "date";
		}else if("NumSpan".equalsIgnoreCase(control)){
			return "number";
		}else if("TextArea".equalsIgnoreCase(control)){
			return "string";
		}else {
			return "string";
		}
	}
	
}
