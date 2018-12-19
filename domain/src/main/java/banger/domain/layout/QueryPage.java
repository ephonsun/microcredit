package banger.domain.layout;

import java.util.ArrayList;
import java.util.List;

public class QueryPage extends BasePage {
	private String sqlId;
	private String tableName;			//主表
	private boolean sqlEnable;
	private String sqlString;
	private CountBar countBar;			//查询结果总数
	private Forms forms;				//查询表单控件
	private Grid grid;					//网格控件
	private List<Button> buttons;		//自定义按钮
	private List<CodeTable> codeTables;			//代码表
	private List<TreeTable> treeTables;			//树型表
	private List<QueryTable> queryTables;		//查询表
	
	public QueryPage(){
		this.countBar = new CountBar();
		this.forms = new Forms();
		this.grid = new Grid();
		this.buttons = new ArrayList<Button>();
		this.codeTables = new ArrayList<CodeTable>();
		this.treeTables = new ArrayList<TreeTable>();
		this.queryTables = new ArrayList<QueryTable>();
	}
	
	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public boolean getSqlEnable() {
		return sqlEnable;
	}

	public void setSqlEnable(boolean sqlEnable) {
		this.sqlEnable = sqlEnable;
	}

	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}
	
	public CountBar getCountBar() {
		return countBar;
	}
	
	public void setCountBar(CountBar countBar) {
		this.countBar = countBar;
	}

	public Forms getForms() {
		return forms;
	}

	public void setForms(Forms forms) {
		this.forms = forms;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public List<CodeTable> getCodeTables() {
		return codeTables;
	}

	public void setCodeTables(List<CodeTable> codeTables) {
		this.codeTables = codeTables;
	}

	public List<TreeTable> getTreeTables() {
		return treeTables;
	}

	public void setTreeTables(List<TreeTable> treeTables) {
		this.treeTables = treeTables;
	}

	public List<QueryTable> getQueryTables() {
		return queryTables;
	}

	public void setQueryTables(List<QueryTable> queryTables) {
		this.queryTables = queryTables;
	}

}
