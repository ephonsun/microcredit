package banger.domain.config;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 数据字段同步表
 */
public class AutoTableColumnSync implements Serializable {
	private static final long serialVersionUID = 6967348230198770849L;
	private String tableDisplay;					//表显示名称
	private String tableColumnDisplay;					//列显示名称
	private String sourceTable;					//源表名
	private String sourceTableColumn;					//原列名
	private String targetTable;					//目标表名
	private String targetTableColumn;					//目标列名
	private String actionName;					//同步动作

	public String getTableDisplay(){
		return this.tableDisplay;
	}

	public void setTableDisplay(String tableDisplay){
		this.tableDisplay=tableDisplay;
	}

	public String getTableColumnDisplay(){
		return this.tableColumnDisplay;
	}

	public void setTableColumnDisplay(String tableColumnDisplay){
		this.tableColumnDisplay=tableColumnDisplay;
	}

	public String getSourceTable(){
		return this.sourceTable;
	}

	public void setSourceTable(String sourceTable){
		this.sourceTable=sourceTable;
	}

	public String getSourceTableColumn(){
		return this.sourceTableColumn;
	}

	public void setSourceTableColumn(String sourceTableColumn){
		this.sourceTableColumn=sourceTableColumn;
	}

	public String getTargetTable(){
		return this.targetTable;
	}

	public void setTargetTable(String targetTable){
		this.targetTable=targetTable;
	}

	public String getTargetTableColumn(){
		return this.targetTableColumn;
	}

	public void setTargetTableColumn(String targetTableColumn){
		this.targetTableColumn=targetTableColumn;
	}

	public String getActionName(){
		return this.actionName;
	}

	public void setActionName(String actionName){
		this.actionName=actionName;
	}

}
