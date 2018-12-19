package banger.domain.layout;

public class FormsCell {
	private Integer cols;		//例数
	private Integer rows;		//行数
	private String title;		//显示名称
	private String control;		//控件
	private String field;		//字段
	private String html;		//自定义HTML
	private String dbtype;		//数据类型
	private String query;		//查询方式如：精确，糊模
	private String table;
	private String property;
	
	public FormsCell(){
		this.cols = 1;
		this.rows = 1;
		this.html = "";
		this.control = "";
		this.field = "";
		this.property = "";
	}
	
	public Integer getCols() {
		return cols;
	}
	public void setCols(Integer cols) {
		this.cols = cols;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getDbtype() {
		return dbtype;
	}
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
}
